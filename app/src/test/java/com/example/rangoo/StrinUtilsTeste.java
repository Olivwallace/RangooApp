package com.example.rangoo;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import com.example.rangoo.Utils.StringUtils;


public class StrinUtilsTeste {

    @Test
    public void testIsEmailValid(){
        boolean isEmail = StringUtils.isValidEmail(StringForTeste.VALID_EMAIL);
        assertEquals(true, isEmail);
    }

    @Test
    public void testIsInvalidEmail(){
        boolean isEmail = StringUtils.isValidEmail(StringForTeste.INVALID_EMAIL);
        assertEquals(false, isEmail);
    }

    @Test
    public void testIsPasswordValid(){
        boolean isPass = StringUtils.isValidPassword(StringForTeste.VALID_PASSWORD);
        assertEquals(true, isPass);
    }

    @Test
    public void testIsInvalidPassword(){
        boolean isPass = StringUtils.isValidPassword(StringForTeste.INVALID_PASSWORD);
        assertEquals(false, isPass);
    }

    @Test
    public void testValidDate(){
        boolean isData = StringUtils.isValidDate(StringForTeste.VALID_DATE);
        assertEquals(true, isData);
    }

    @Test
    public void testInvalidDate(){
        boolean isData = StringUtils.isValidDate(StringForTeste.INVALID_DATE);
        assertEquals(false, isData);
    }

    @Test
    public void testeValidPhone(){
        boolean isPhone = StringUtils.isValidPhone(StringForTeste.VALID_PHONE);
        assertEquals(true, isPhone);
    }

    @Test
    public void testeInvalidPhone(){
        boolean isPhone = StringUtils.isValidPhone(StringForTeste.INVALID_PHONE);
        assertEquals(false, isPhone);
    }

}
