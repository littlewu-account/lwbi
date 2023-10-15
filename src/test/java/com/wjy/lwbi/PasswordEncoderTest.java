package com.wjy.lwbi;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

/**
 * @author 吴峻阳
 * @version 1.0
 */
@SpringBootTest
public class PasswordEncoderTest {

    @Resource
    private PasswordEncoder passwordEncoder;

    @Test
    public void testPasswordEncoder() {
        String encode = passwordEncoder.encode("12345678");
        System.out.println(encode);
        boolean matches = passwordEncoder.matches("12345678", encode);
        System.out.println(matches);
    }

}
