package com.example.demo.service.message;

import org.springframework.stereotype.Service;

@Service
public class Message {
    public final static String LOGIN_SUCCESS = "Login success";
    public final static String LOGIN_FAILURE = "Login failure";
    public final static int HTTP_OK = 200;
    public final static String ROLE_ADMIN = "ADMIN";
    public final static String ROLE_USER = "USER";
    public final static String ROLE = "ROLE";
    public final static String USER_ID = "USER_ID";
    public final static String PERMISSION_DENIED = "PERMISSION_DENIED";
    public final static String SUCCESS = "SUCCESS";
    public final static String SUCCESS_CONFIG = "SUCCESS_CONFIGURATION";
    public final static String FAIL_CONFIG = "FAIL_CONFIGURATION";
    public final static String REGISTER_SUCCESS = "REGISTER_SUCCESS";
    public final static String REGISTER_FAILURE = "REGISTER_FAILURE";
    public final static String ALERT_TEMPERATURE = " is higher than config temperature: ";
    public final static String SUBMIT_FAILURE = "SUBMIT_FAILURE";
    public final static String MISS_INFORMATION = "PLEASE INPUT FILL FULL INFORMATION";
}
