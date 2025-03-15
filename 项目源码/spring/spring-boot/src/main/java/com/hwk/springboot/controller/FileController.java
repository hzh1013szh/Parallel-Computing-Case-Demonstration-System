package com.hwk.springboot.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.StrUtil;
import com.hwk.springboot.common.Result;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/files")
public class FileController {
    // 文件上传存储路径
    private static final String filepath = System.getProperty("user.dir") + "/file/";
    //文件上传
    @RequestMapping("/upload")
    public Result upload(MultipartFile file){
        synchronized (FileController.class){
            String flag = System.currentTimeMillis()+"";
            String filename = file.getOriginalFilename();
            try{
                // 如果没有file文件夹，会在根目录下创建一个文件夹
                if(!FileUtil.isDirectory(filepath)){
                    FileUtil.mkdir(filepath);
                }
                // 文件存储形式 时间戳+文件名
                FileUtil.writeBytes(file.getBytes(),filepath + flag + "-" + filename);
                System.out.println("文件上传成功");
                Thread.sleep(1L);
            }catch (Exception e){
                System.err.println("文件上传失败");
            }
            return Result.success(flag);
        }
    }
    // 获取文件
    @RequestMapping("/{flag}")
    public void avatarpath(@PathVariable String flag, HttpServletResponse response){
        if(!FileUtil.isDirectory(filepath)){
            FileUtil.mkdir(filepath);
        }
        OutputStream os;
        List<String> fileNames = FileUtil.listFileNames(filepath);
        String avatar = fileNames.stream().filter(name -> name.contains(flag)).findAny().orElse("");
        try{
            if(StrUtil.isNotEmpty(avatar)){
                response.addHeader("Content-Disposition","attachment;filename=" + URLEncoder.encode(avatar,"UTF-8"));
                response.setContentType("application/octet-stream");
                byte[] bytes = FileUtil.readBytes(filepath + avatar);
                os = response.getOutputStream();
                os.write(bytes);
                os.flush();
                os.close();
            }
        }catch (Exception e){
            System.err.println("文件下载失败");
        }
    }
    @PostMapping("/wang/upload")
    public Map<String,Object> wangEditorUpload(MultipartFile file){
        String flag = System.currentTimeMillis()+"";
        String filename = file.getOriginalFilename();
        try{
            // 文件存储形式 时间戳+文件名
            FileUtil.writeBytes(file.getBytes(),filepath + flag + "-" + filename);
            System.out.println("文件上传成功");
            Thread.sleep(1L);
        }catch (Exception e){
            System.err.println("文件上传失败");
        }
        Map<String,Object> resMap = new HashMap<>();
        //wangEditor上传图片成功后，需要返回的参数
        resMap.put("errno",0);
        resMap.put("data", CollUtil.newArrayList(Dict.create().set("url","http://localhost:8080/api/files/" + flag)));
        return resMap;
    }
}
