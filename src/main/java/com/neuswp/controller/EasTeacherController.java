package com.neuswp.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.neuswp.entity.EasTeacher;
import com.neuswp.entity.EasUser;
import com.neuswp.services.EasStudentService;
import com.neuswp.services.EasTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/easTeacher")
public class EasTeacherController {

    @Autowired
    private EasTeacherService easTeacherService;

    @Autowired
    private EasStudentService easStudentService;

    @RequestMapping("/index")
    public String index() throws Exception{
        return "system/teacher/index";
    }

    @RequestMapping("/list")
    @ResponseBody
    public Map<String, Object> list(@RequestParam(defaultValue = "1") Integer page,
                                    @RequestParam(defaultValue = "10") Integer limit,
                                    EasTeacher easTeacher) throws Exception{
        Map<String, Object> map = new HashMap<>();

//        分页不能用待修改。。。

        Page<EasTeacher> pager = PageHelper.startPage(page,limit);
//        List<EasTeacher> list = easTeacherService.getList(easTeacher);
        List<EasTeacher> list = easTeacherService.findTeacherList(easTeacher);
//        System.out.println("获取信息总条数为:" + list.size());
//        for (EasTeacher e:list
//             ) {
//            System.out.println(e.getUser().getUsername());
//            System.out.println(e.getName());
//        }
        map.put("count",pager.getTotal());
        map.put("data",list);
        map.put("code",0);
        map.put("msg","");

        return map;
    }

    @RequestMapping("/search")
    @ResponseBody
    public List<EasTeacher> search() throws Exception{
        return easTeacherService.getAll();
    }

    /**
     * 导入教师信息 (从 EXCEL)
     * @param filePath Ali OSS url
     */
    @RequestMapping(value = "/import", method = RequestMethod.POST)
    public ResponseEntity<?> importTeachers(@RequestParam("filePath") String filePath) {
        try {
            // 下载文件并解析 Excel，然后插入数据库
            easTeacherService.importTeachersFromOSS(filePath);
            return ResponseEntity.ok().body(Map.of(
                    "code", 200,
                    "msg", "success"
            ));
        } catch (RuntimeException e) {
            // 运行时异常，如文件格式错误、数据库键值重复
            return ResponseEntity.status(500).body(Map.of(
                    "code", 500,
                    "msg", "error: " + e.getMessage()
            ));
        }
    }

    /**
     * 可靠性一般的权限控制
     */
    @RequestMapping(value = "/import", method = RequestMethod.GET)
    public ResponseEntity<?> checkPermission(HttpSession session) {
        /// 检查当前用户是否具有导入权限

        // 1. 获取当前用户
        EasUser currentUser = (EasUser) session.getAttribute("login_user");
        System.out.println("[Controller] Current User: " + currentUser);
        String username = currentUser.getUsername();

        // 2. 检查权限
        if (!easStudentService.hasStudent(username) && !easTeacherService.hasTeacher(username)) {
            return ResponseEntity.ok().body(Map.of(
                    "code", 200,
                    "msg", "success"
            ));
        } else {
            return ResponseEntity.status(200).body(Map.of(
                    "code", 203,
                    "msg", "NO_PERMISSION"
            ));
        }
    }
}
