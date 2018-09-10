package com.weilt.commonentity.utils;

import org.springframework.util.DigestUtils;

/**
 * @author weilt
 * @com.weilt.commonentity.utils
 * @date 2018/9/10 == 0:01
 */
public class MD5Util {
    private static final String salt = "asldfjlk43097890afs998asdfd__dlklj%*&%";
    public String getmd5(String orign){
        String base = orign +"/"+salt;
        String charsetName = "utf-8";

        String md5 = null;

        md5 = DigestUtils.md5DigestAsHex(base.getBytes());

        return md5.toUpperCase();
    }
}
