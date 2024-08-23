package com.ankush.readapp.helper;

import com.ankush.readapp.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

public class UserDetailsHelper {

    public static User getCurrentUserDetails() {
        var securityContext = SecurityContextHolder.getContext();
        var authentication = securityContext.getAuthentication();
        if(Objects.nonNull(authentication) && authentication.getPrincipal() instanceof User) {
            return (User) authentication.getPrincipal();
        }

        return new User();
    }
}
