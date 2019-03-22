package com.imooc.house.common;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import org.apache.commons.lang3.CharSet;
import org.springframework.util.DigestUtils;

import java.nio.charset.Charset;

public class HashUtil {
//    private static final HashFunction FUNCTION = Hashing.md5();
    private static final String SALT = "HAOFANGWANG";
    public static String encryPasswd(String passwd){
//      HashCode hashCode = FUNCTION.hashString(passwd + SALT, Charset.forName("UTF-8"));
      String str = DigestUtils.md5DigestAsHex((passwd + SALT).getBytes());
      return str;
    }
}
