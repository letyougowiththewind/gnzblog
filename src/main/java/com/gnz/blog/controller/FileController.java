package com.gnz.blog.controller;

import cn.hutool.core.util.*;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.util.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.*;
import com.gnz.blog.model.comm.*;
import com.gnz.blog.model.vo.*;
import com.gnz.blog.provider.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

@Api("与文件操作相关的api接口")
@RestController
public class FileController {
//    @Autowired
//    private MinioFileProvider minioFileProvider;
    @Autowired
    private QiniuFileProvider qiniuFileProvider;
    @Value("${qiniu.server}")
    private String uploadUrl;
    @Value("${qiniu.url}")
    private String imageUrl;

//    @ApiOperation("上传图片接口")
//    @PostMapping("/file/image")
//    public Results uploadImage(
//            @ApiParam("要上传的文件")
//            @RequestParam("image") MultipartFile multipartFile) {
//        String originalFilename = multipartFile.getOriginalFilename();
//        String[] arr = originalFilename.split("\\.");
//        String fileName = IdUtil.simpleUUID();
//        if (ArrayUtil.isNotEmpty(arr)) {
//            fileName += "." + arr[arr.length - 1];
//        }
//        try {
//            InputStream inputStream = multipartFile.getInputStream();
//            minioFileProvider.uploadFile(inputStream, fileName, multipartFile.getContentType());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return Results.ok("图片上传成功", "/file/image/" + fileName);
//    }
//
//    @ApiOperation("获取图片")
//    @GetMapping("/file/image/{fileName}.{type}")
//    public Results getImage(@PathVariable String fileName, @PathVariable String type, HttpServletResponse res) {
//        String fullName = fileName + "." + type;
//        try (ServletOutputStream out = res.getOutputStream(); InputStream in = minioFileProvider.downloadFile(fullName)) {
//            StreamUtils.copy(in, out);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return Results.ok("获取图片成功", null);
//    }

    @ApiOperation("获取七牛云上传凭证")
    @GetMapping("/file/qiniu/token")
    public Results<QiniuUploadInfoVO> getToken() {
        String token = qiniuFileProvider.getUploadToken();
        QiniuUploadInfoVO info = QiniuUploadInfoVO.builder()
                .token(token)
                .uploadUrl(uploadUrl)
                .imageUrl(imageUrl)
                .build();
        return Results.ok("token获取成功", info);
    }
}
