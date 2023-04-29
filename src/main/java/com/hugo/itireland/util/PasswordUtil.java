package com.hugo.itireland.util;

import org.springframework.util.DigestUtils;

public class PasswordUtil {
    public static String md5(String password) {
        return DigestUtils.md5DigestAsHex(password.getBytes());
    }
}
