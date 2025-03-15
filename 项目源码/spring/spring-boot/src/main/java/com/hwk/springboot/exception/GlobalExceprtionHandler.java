package com.hwk.springboot.exception;
import com.hwk.springboot.common.Result;
import jakarta.persistence.Id;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
// 全局异常
@ControllerAdvice(basePackages="com.hwk.springboot.controller")
public class GlobalExceprtionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceprtionHandler.class);

//    统一异常处理@ExceptionHandler，主要用于Exception
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(HttpServletRequest request,Exception e){
        log.error("异常信息：" + e);//后台显示的信息
        return Result.error("系统异常");//返回给前台的信息
    }
    // 自定义异常处理
    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public Result error(HttpServletRequest request,CustomException e){
        return Result.error(e.getMsg());
    }
}
