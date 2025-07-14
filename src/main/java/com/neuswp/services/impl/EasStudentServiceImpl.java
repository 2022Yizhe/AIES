package com.neuswp.services.impl;


import com.alibaba.excel.EasyExcel;
import com.neuswp.entity.EasStudent;
import com.neuswp.entity.EasUser;
import com.neuswp.entity.dto.Student;
import com.neuswp.mappers.EasStudentMapper;
import com.neuswp.mappers.EasUserMapper;
import com.neuswp.services.EasStudentService;
import com.neuswp.utils.AliOssUtil;
import com.neuswp.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;


@Service
public class EasStudentServiceImpl implements EasStudentService {

    @Autowired
    private EasStudentMapper easStudentMapper;

    @Autowired
    private EasUserMapper easUserMapper;

    @Override
    public List<EasStudent> getList(EasStudent easStudent) throws Exception {
        return easStudentMapper.getList(easStudent);
    }

    @Override
    public List<EasStudent> findList(EasStudent easStudent) throws Exception {
        return easStudentMapper.findList(easStudent);
    }

    @Override
    public List<EasStudent> findListByUsername(String username) throws Exception {
        return easStudentMapper.findListByUsername(username);
    }

    @Override
    public EasStudent getStudentByUsername(String username) throws Exception {
        return easStudentMapper.getStudentByUsername(username);
    }

    @Override
    public void updateStudent(EasStudent easStudent) throws Exception {
        easStudentMapper.updateStudent(easStudent);
    }

    @Override
    public int getCountBytIdandcId(Integer tId, Integer baseCourseId,Integer classId) throws Exception {
        return easStudentMapper.getCountBytIdandcId(tId,baseCourseId,classId);
    }

    @Override
    public int getEndingCountBytIdandcId(Integer tId, Integer baseCourseId, Integer classId) {
        return easStudentMapper.getEndingCountBytIdandcId(tId,baseCourseId,classId);
    }

    @Override
    public List<EasStudent> getStudentScoreListByTid(Integer tId, Integer baseCourseId, Integer classId, PageUtil pageUtil) {
        return easStudentMapper.getStudentScoreListByTid(tId,baseCourseId,classId,pageUtil);
    }

    @Override
    public List<EasStudent> getStudentSelectCourseListByTid(Integer tId, Integer baseCourseId, Integer classId, PageUtil pageUtil) {
        return easStudentMapper.getStudentSelectCourseListByTid(tId,baseCourseId,classId,pageUtil);
    }

    @Override
    public int getTotal() {
        return easStudentMapper.getTotal();
    }

    @Override
    public int getTotalSex(String sex) {
        return easStudentMapper.getTotalSex(sex);
    }

    @Override
    public void addUsername(String username) throws Exception{
        easStudentMapper.addUsername(username);
    }

    @Override
    public void deleteStudent(String username) throws Exception{
        easStudentMapper.deleteStudent(username);
    }

    /**
     * 从 OSS 下载 Excel 并导入学生数据
     * @param fileUrl OSS 中的文件路径 (如：https:/\.../uploads/UUID_students_export_2024-09-15.xlsx)
     */
    @Override
    public void importStudentsFromOSS(String fileUrl) {
        AliOssUtil aliOssUtil = new AliOssUtil();

        // 下载指定文件
        byte[] fileBytes = aliOssUtil.download(fileUrl);
        if (fileBytes == null || fileBytes.length == 0) {
            throw new RuntimeException("Failed to download file or it's empty in OSS bucket!");
        }

        // 读取 Excel 数据
        List<Student> students = readStudentExcel(fileBytes, Student.class);

        // 插入数据库 (先插入用户表再插入学生表)
        for (Student student : students) {
            EasUser user = new EasUser();
            user.setUsername(student.getUsername());
            user.setPassword("123456");
            user.setSalt("123456");
            user.setLocked("0");
            easUserMapper.add(user);
        }
        easStudentMapper.insertBatch(students);

        System.out.println("[Service] Import " + students.size() + " student records from "+ fileUrl);

}

    /** 使用 EasyExcel 读取 Excel 数据
     * @return List<Student>
     */
    private List<Student> readStudentExcel(byte[] fileBytes, Class<Student> clazz) {
        List<Student> studentList = new ArrayList<>();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(fileBytes);
        try {
            studentList = EasyExcel.read(inputStream, clazz, null)
                    .sheet()
                    .doReadSync();
        } catch (Exception e) {
            System.out.println("[Service] Excel parsing failed!");
        }
        return studentList;
    }
}
