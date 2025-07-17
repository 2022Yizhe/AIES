package com.neuswp.services.impl;

import com.alibaba.excel.EasyExcel;
import com.neuswp.entity.EasTeacher;
import com.neuswp.entity.EasUser;
import com.neuswp.entity.dto.Student;
import com.neuswp.entity.dto.Teacher;
import com.neuswp.mappers.EasTeacherMapper;
import com.neuswp.mappers.EasUserMapper;
import com.neuswp.services.EasTeacherService;
import com.neuswp.utils.AliOssUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;


@Service
public class EasTeacherServiceImpl implements EasTeacherService {

    @Autowired
    private EasTeacherMapper easTeacherMapper;

    @Autowired
    private EasUserMapper easUserMapper;

    @Override
    public List<EasTeacher> findTeacherList(EasTeacher easTeacher) throws Exception {
        return easTeacherMapper.findTeacherList(easTeacher);
    }

    @Override
    public List<EasTeacher> findListByUsername(String username) {
        return easTeacherMapper.findListByUsername(username);
    }

    @Override
    public EasTeacher getTeacherByUsername(String username) {
        return easTeacherMapper.getTeacherByUsername(username);
    }

    @Override
    public void updateTeacher(EasTeacher easTeacher) {
        easTeacherMapper.updateTeacher(easTeacher);
    }

    @Override
    public List<EasTeacher> getAll() {
        return easTeacherMapper.getAll();
    }

    @Override
    public EasTeacher findTeacherByUsername(String username) {
        return easTeacherMapper.findTeacherByUsername(username);
    }

    @Override
    public int getTotal() {
        return easTeacherMapper.getTotal();
    }

    @Override
    public void addUsername(String username) {
        easTeacherMapper.addUsername(username);
    }

    @Override
    public void deleteTeacher(String username) {
        easTeacherMapper.deleteTeacher(username);
    }


    /**
     * 从 OSS 下载 Excel 并导入教师数据
     * @param fileUrl OSS 中的文件路径 (如：https:/\.../uploads/UUID_teachers_export_2024-09-15.xlsx)
     */
    @Override
    public void importTeachersFromOSS(String fileUrl) {
        AliOssUtil aliOssUtil = new AliOssUtil();

        // 下载指定文件
        byte[] fileBytes = aliOssUtil.download(fileUrl);
        if (fileBytes == null || fileBytes.length == 0) {
            throw new RuntimeException("Failed to download file or it's empty in OSS bucket!");
        }

        // 读取 Excel 数据
        List<Teacher> teachers = readTeacherExcel(fileBytes, Teacher.class);

        // 插入数据库 (先插入用户表，再插入权限表，最后插入教师表)
        for (Teacher teacher : teachers) {
            EasUser user = new EasUser();
            user.setUsername(teacher.getUsername());
            user.setPassword("a66abb5684c45962d887564f08346e8d");
            user.setSalt("admin");
            user.setLocked("0");
            easUserMapper.add(user);
            easUserMapper.addUserRole(user.getId(), new Integer[]{3});  // 教师 - 3
        }
        easTeacherMapper.insertBatch(teachers);

        System.out.println("[Service] Import " + teachers.size() + " teacher records from "+ fileUrl);
    }

    /**
     * 查询教师是否存在
     * @param username 教师用户名
     */
    @Override
    public boolean hasTeacher(String username) {
        EasTeacher teacher = easTeacherMapper.getTeacherByUsername(username);
        return teacher != null;
    }

    /**
     * 使用 EasyExcel 读取 Excel 数据
     * @return List<Teacher>
     */
    private List<Teacher> readTeacherExcel(byte[] fileBytes, Class<Teacher> clazz) {
        List<Teacher> teacherList = new ArrayList<>();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(fileBytes);
        try {
            teacherList = EasyExcel.read(inputStream, clazz, null)
                    .sheet()
                    .doReadSync();
        } catch (Exception e) {
            System.out.println("[Service] Excel parsing failed!");
        }
        return teacherList;
    }
}
