package com.applet.common.auth;

import cn.hutool.core.util.StrUtil;
import com.applet.common.util.JwtOperator;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @author novLi
 * @date 2020年01月14日 16:47
 */
@Aspect
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthAspect {

    private final JwtOperator jwtOperator;

    /**
     * 校验用户是否登录
     **/
    @Around("@annotation(com.applet.common.auth.CheckLogin)")
    public Object CheckLogin(ProceedingJoinPoint point) {
        try {
            //1. 从header中取出token
            HttpServletRequest request = getRequest();

            String token = request.getHeader("X-Token");

            Boolean isValid = jwtOperator.validateToken(token);
            if (!isValid) {
                throw new SecurityException("Token 不合法");
            }
            // 3. 如果校验成功，那么就将用户的信息设置到request的attribute里面
            Claims claims = jwtOperator.getClaimsFromToken(token);
            request.setAttribute("id", claims.get("id"));
            request.setAttribute("wxNickname", claims.get("wxNickname"));
            request.setAttribute("role", claims.get("role"));

            return point.proceed();
        } catch (Throwable throwable) {
            throw new SecurityException("Token 不合法");
        }
    }

    private HttpServletRequest getRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        //2. 校验token是否合法,如果不合法,直接抛异常，如果合法就放行
        ServletRequestAttributes attributes = (ServletRequestAttributes) requestAttributes;
        return attributes.getRequest();
    }

    /**
     * 校验用户是否拥有访问权限
     **/
    @Around("@annotation(com.applet.common.auth.CheckAuthorization)")
    public Object CheckAuthorization(ProceedingJoinPoint point) {
        try {
            // 1. 验证token是否合法；
            this.CheckLogin(point);
            //校验是否匹配
            HttpServletRequest request = getRequest();

            String role = (String) request.getAttribute("role");

            MethodSignature signature = (MethodSignature) point.getSignature();
            Method method = signature.getMethod();
            CheckAuthorization annotation = method.getAnnotation(CheckAuthorization.class);

            String value = annotation.value();

            if (!StrUtil.equals(role, value)) {
                throw new SecurityException("用户无权访问");
            }
            return point.proceed();
        } catch (Throwable throwable) {
            throw new SecurityException("用户无权访问");
        }
    }
}
