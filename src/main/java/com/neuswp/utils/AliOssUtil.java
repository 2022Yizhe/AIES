package com.neuswp.utils;

import com.alibaba.excel.EasyExcel;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.OSSObject;
import com.neuswp.entity.dto.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.output.ByteArrayOutputStream;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Slf4j
public class AliOssUtil {

    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;

    // 请在服务主机环境配置 OSS_ACCESS_KEY_ID 和 OSS_ACCESS_KEY_SECRET
    public AliOssUtil() {
        this.endpoint = "oss-cn-beijing.aliyuncs.com";
        this.accessKeyId = System.getenv("OSS_ACCESS_KEY_ID");
        this.accessKeySecret = System.getenv("OSS_ACCESS_KEY_SECRET");
        this.bucketName = "web-yizhe-aies";
    }

    /**
     * 文件上传
     * @param bytes 文件字节流（数组）
     * @param objectName 文件名
     * @return 文件访问的 url
     */
    public String upload(byte[] bytes, String objectName) {

        // 创建 OSSClient 实例
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {
            // 创建 PutObject 请求
            ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(bytes));
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }

        // 文件访问路径规则 'https://BucketName.Endpoint/ObjectName'
        StringBuilder stringBuilder = new StringBuilder("https://");
        stringBuilder
                .append(bucketName)
                .append(".")
                .append(endpoint)
                .append("/")
                .append(objectName);

        System.out.println("[Utils] File upload to: " + stringBuilder.toString());

        return stringBuilder.toString();
    }

    /**
     * 从 OSS 下载文件
     * @param fileUrl 文件 url
     * @return 文件字节流（数组）
     */
    public byte[] download(String fileUrl) {
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        try {
            // 从 URL 提取 objectName
            String objectName = parseObjectNameFromUrl(fileUrl);
            if (objectName == null || objectName.isEmpty()) {
                System.out.println("[Utils] Invalid OSS file URL: " + fileUrl);
                return null;
            }

            // 获取 OSS 文件对象
            OSSObject ossObject = ossClient.getObject(bucketName, objectName);
            if (ossObject == null) {
                System.out.println("[Utils] File not found in OSS: " + objectName);
                return null;
            }

            // 获取 OSS 文件字节流
            InputStream inputStream = ossObject.getObjectContent();
            byte[] bytes = readStream(inputStream);
            return bytes;
        } catch (Exception e) {
            System.out.println("[Utils] OSS download failed! Object name: " + fileUrl + e);
            return null;
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }

    /**
     * 流转 byte[]
     */
    private byte[] readStream(InputStream inputStream) throws Exception {
        byte[] buffer = new byte[1024];
        int len;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) > -1) {
            output.write(buffer, 0, len);
        }
        return output.toByteArray();
    }

    /**
     * 从 OSS 文件 URL 中提取 objectName
     * 示例输入：https:/\.../uploads/UUID_students_export_2024-09-15.xlsx
     * 输出：exports/students_export_2024-09-15.xlsx
     */
    private String parseObjectNameFromUrl(String fileUrl) {
        // 去掉协议部分
        String[] parts = fileUrl.split("//");
        if (parts.length < 2) return null;

        // 去掉域名部分
        String pathPart = parts[1].substring(parts[1].indexOf("/") + 1);
        return pathPart;
    }
}
