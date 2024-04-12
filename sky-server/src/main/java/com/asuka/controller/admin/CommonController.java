package com.asuka.controller.admin;

import com.asuka.constant.MessageConstant;
import com.asuka.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

@RestController
@RequestMapping("/admin/common")
@Slf4j
public class CommonController {


    private String path = "D:/sky-parent/sky-server/src/main/resources/static/image";

    //private String p =

    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file){

        log.info("文件上传,{}",file);
        try {
            //获取文件名
            String originalFilename = file.getOriginalFilename();
            //获取后缀名
            String fileFormat = originalFilename.substring(originalFilename.lastIndexOf("."));

            String fileName = UUID.randomUUID().toString() + fileFormat;

            String p = path+"/"+fileName;
            file.transferTo(new File(p));

            return Result.success("http://127.0.0.1:8080/admin/common/image/"+fileName);
        } catch (IOException e) {
            log.info("文件上传失败,{}",e);
        }

        return Result.error(MessageConstant.UPLOAD_FAILED);
    }

    @GetMapping("/image/{image}")
    public byte[] getImage(@PathVariable String image){
        File file = new File(path+"/"+image);
        try {
            FileInputStream fos = new FileInputStream(file);
            byte[] b = fos.readAllBytes();
            return b;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }




}
