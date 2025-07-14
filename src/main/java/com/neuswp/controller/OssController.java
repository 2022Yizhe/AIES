package com.neuswp.controller;

import com.neuswp.utils.AliOssUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.UUID;


@RestController
@RequestMapping("/oss")
public class OssController {

    /**
     * 文件上传到 Ali OSS 的 '/uploads' 目录下
     * @param file  文件
     * @return 文件访问的 url
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) {
        try {
            String fileName = "uploads/" + UUID.randomUUID() + file.getOriginalFilename();
            String url = new AliOssUtil().upload(file.getBytes(), fileName);
            return ResponseEntity.ok().body(Map.of(
                "code", 200,
                "filePath", url
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                "code", 500,
                "msg", "上传失败"
            ));
        }
    }
}
