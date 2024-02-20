package com.ankush.readapp.interceptor;

import com.ankush.readapp.dto.UserDetails;
import com.ankush.readapp.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class UserDetailsInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        var headerValue = request.getHeader("userDetails");
        Utils.convertObject(headerValue, UserDetails.class);
        return true;
    }
}
