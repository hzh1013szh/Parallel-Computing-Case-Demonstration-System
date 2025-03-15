package com.hwk.springboot.common;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.hwk.springboot.entity.Admin;
import com.hwk.springboot.exception.CustomException;
import com.hwk.springboot.service.AdminService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

// Jwt拦截器，根据前端传过来的token，判断它的token是否合法
@Component
public class JwtInterceptor implements HandlerInterceptor {
    private static final Logger log = LoggerFactory.getLogger(JwtInterceptor.class);

    @Resource
    private AdminService adminService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        // 从http请求头中获取token
        String token = request.getHeader("token");
        if(StrUtil.isBlank(token)){
            // 如果没有，就去查询参数
            token = request.getParameter("token");
        }
        // 认证开始
        if(StrUtil.isBlank(token)){
            throw new CustomException("无token，请重新登录");
        }
        //获取token中的adminId
        String adminId = null;
        Admin admin = null;
        try{
            adminId = JWT.decode(token).getAudience().get(0);
            admin = adminService.getById(Integer.parseInt(adminId));
        }catch (Exception e){
            log.error("解析token失败，请重新登录，token={}", token, e);
            throw new CustomException("解析token失败，请重新登录");
        }
        if(admin == null){
            throw new CustomException("用户不存在，请重新登录");
        }
        try {
            // 用户密码加签认证
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(admin.getPassword())).build();
            jwtVerifier.verify(token);
        }catch (JWTVerificationException e){
            throw new CustomException("token验证失败，请重新登录");
        }
        log.info("token正确");
        return true;
    }
}
