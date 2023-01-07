package com.lio.api.model.constant;

import java.util.Random;

public class Index {
    
    public static final Integer ACCOUNT_ID_LENGTH = 10;
    public static final Integer POST_ID_LENGTH = 15;
    public static final String JWT_ISSUER = "lionel@twitter-clone";
    public static String JWT_SECRET = "dfjuerejixkkuipqkudjk91j3kkdffdf298008dfjdf@45dfmnjix";
    public static Integer []  NUMBERS = { 0,1,2,3,4,5,6,7,8,9 };
    public static char [] CHARACTERS = {
            'a','b','c','d','e',
            'f','g','h','i','j','k',
            'l','m','n','o','p','q',
            'r','s','t','u','v',
            'w','x','y','z'
    };

    public static Integer VERIFICATION_CODE_BOUND = 1000000;

    public static String BEARER = "Bearer ";

}
