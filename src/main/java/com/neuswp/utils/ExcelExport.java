package com.neuswp.utils;

import com.alibaba.excel.EasyExcel;
import com.neuswp.constant.IntendKeyWords;
import com.neuswp.entity.EasBaseCourse;
import com.neuswp.entity.EasClass;
import com.neuswp.entity.EasStudent;
import com.neuswp.entity.EasTeacher;
import com.neuswp.entity.dto.Student;
import com.neuswp.services.EasBaseCourseService;
import com.neuswp.services.EasClassService;
import com.neuswp.services.EasStudentService;
import com.neuswp.services.EasTeacherService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/**
 * 功能调用路由：实现用户意图到功能的映射
 * 使用 easy-excel 导出 List 数据为 Excel 表格，并存储在阿里云 OSS 中
 * 注：此功能正常运行需要配置阿里云的 OSS 服务和 Access License！
 */
@Slf4j
@Component
public class ExcelExport {

    @Autowired
    private EasClassService easClassService;

    @Autowired
    private EasTeacherService easTeacherService;

    @Autowired
    private EasBaseCourseService easBaseCourseService;

    @Autowired
    private EasStudentService easStudentService;

    /**
     * 功能调用（识别意图后）
     * @param parsedInput 用户意图（需严格按枚举类列举）
     * @return 执行结果（由于只有导出类的意图，所以结果为 URL）
     */
    public String recognize(String parsedInput) {
        if (parsedInput.equals(IntendKeyWords.ERROR_UNKNOWN_INTEND)){
            log.info("[Utils] recognize: " + IntendKeyWords.ERROR_UNKNOWN_INTEND);
            return null;
        }

        final String prefix = "[Utils] recognize: ";
        String url = null;
        switch (parsedInput){
            case IntendKeyWords.EXPORT_BASE_COURSE_LIST:
                log.info(prefix + parsedInput);
                url = exportBaseCourse();
                break;
            case IntendKeyWords.EXPORT_CLASS_LIST:
                log.info(prefix + parsedInput);
                url = exportClass();
                break;
            case IntendKeyWords.EXPORT_TEACHER_LIST:
                log.info(prefix + parsedInput);
                url = exportTeacher();
                break;
            case IntendKeyWords.EXPORT_STUDENT_LIST:
                log.info(prefix + parsedInput);
                url = exportStudent();
                break;
        }
        return url;
    }

    /**
     * 一键导出课程数据
     */
    private String exportBaseCourse() {
        try {
            List<EasBaseCourse> dataList = easBaseCourseService.getAll(); // 获取所有课程数据

            String fileName = "base_courses_export_" + LocalDateTime.now().toString().replace(":", "-") + ".xlsx";
            String objectName = "exports/" + fileName;  // OSS 上的路径

            // 1. 使用 ByteArrayOutputStream 将 Excel 数据写入内存
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            EasyExcel.write(outputStream, EasBaseCourse.class).sheet("课程列表").doWrite(dataList);

            // 2. 初始化 AliOssUtil 并上传到 OSS
            AliOssUtil ossClient = new AliOssUtil();
            String downloadUrl = ossClient.upload(outputStream.toByteArray(), objectName);

            // 3. 记录日志
            log.info("[Utils] Excel uploaded to OSS successfully!");
            log.info("[Utils] Download URL: {}", downloadUrl);

            return downloadUrl;
        } catch (Exception e) {
            log.error("[Utils] Excel export or upload to OSS failed!", e);
            return null;
        }
    }

    /**
     * 一键导出班级数据
     */
    private String exportClass() {
        try {
            List<EasClass> dataList = easClassService.getAll(); // 获取所有班级数据

            String fileName = "classes_export_" + LocalDateTime.now().toString().replace(":", "-") + ".xlsx";
            String objectName = "exports/" + fileName;  // OSS 上的路径

            // 1. 使用 ByteArrayOutputStream 将 Excel 数据写入内存
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            EasyExcel.write(outputStream, EasClass.class).sheet("班级列表").doWrite(dataList);

            // 2. 初始化 AliOssUtil 并上传到 OSS
            AliOssUtil ossClient = new AliOssUtil();
            String downloadUrl = ossClient.upload(outputStream.toByteArray(), objectName);

            // 3. 记录日志
            log.info("[Utils] Excel uploaded to OSS successfully!");
            log.info("[Utils] Download URL: {}", downloadUrl);

            return downloadUrl;
        } catch (Exception e) {
            log.error("[Utils] Excel export or upload to OSS failed!", e);
            return null;
        }
    }

    /**
     * 一键导出教师数据
     */
    private String exportTeacher() {
        try {
            List<EasTeacher> dataList = easTeacherService.getAll(); // 获取所有教师数据

            String fileName = "teachers_export_" + LocalDateTime.now().toString().replace(":", "-") + ".xlsx";
            String objectName = "exports/" + fileName;  // OSS 上的路径

            // 1. 使用 ByteArrayOutputStream 将 Excel 数据写入内存
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            EasyExcel.write(outputStream, EasTeacher.class).sheet("教师列表").doWrite(dataList);

            // 2. 初始化 AliOssUtil 并上传到 OSS
            AliOssUtil ossClient = new AliOssUtil();
            String downloadUrl = ossClient.upload(outputStream.toByteArray(), objectName);

            // 3. 记录日志
            log.info("[Utils] Excel uploaded to OSS successfully!");
            log.info("[Utils] Download URL: {}", downloadUrl);

            return downloadUrl;
        } catch (Exception e) {
            log.error("[Utils] Excel export or upload to OSS failed!", e);
            return null;
        }
    }

    /**
     * 一键导出学生数据
     */
    private String exportStudent() {
        try {
            List<EasStudent> dataList = easStudentService.getList(new EasStudent()); // 获取所有学生数据
            List<Student> resultList = new ArrayList<>();   // 转为 Student 对象
            for (EasStudent easStudent : dataList) {
                Student student = new Student(
                        easStudent.getId(),
                        easStudent.getUsername(),
                        easStudent.getName(),
                        easStudent.getSex(),
                        easStudent.getBirthday(),
                        easStudent.getPhone(),
                        easStudent.getClass_id(),
                        easStudent.getMotto()
                );
                resultList.add(student);
            }

            String fileName = "students_export_" + LocalDateTime.now().toString().replace(":", "-") + ".xlsx";
            String objectName = "exports/" + fileName;  // OSS 上的路径

            // 1. 使用 ByteArrayOutputStream 将 Excel 数据写入内存
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            EasyExcel.write(outputStream, Student.class)
                    .sheet("学生列表")
                    .doWrite(resultList);

            // 2. 初始化 AliOssUtil 并上传到 OSS
            AliOssUtil ossClient = new AliOssUtil();
            String downloadUrl = ossClient.upload(outputStream.toByteArray(), objectName);

            log.info("[Utils] Excel uploaded to OSS successfully!");
            return downloadUrl;
        } catch (Exception e) {
            log.error("[Utils] Excel export or upload to OSS failed!", e);
            return null;
        }
    }
}
