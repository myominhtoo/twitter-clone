package com.lio.api.util;

import static com.lio.api.model.constant.Index.*;

import java.util.Random;

public class Generator {

    private Generator(){}

    public static String generateId( String target ){
        String generatedId = "";
        boolean isNumberTurn = true;
        Random random = new Random();
        Integer ID_LENGTH = target.toLowerCase().equals("account")
                            ? ACCOUNT_ID_LENGTH : POST_ID_LENGTH;
        
        for( int i = 0 ; i < ID_LENGTH ; i++ ){ 
            generatedId += isNumberTurn 
                           ? String.valueOf(NUMBERS[random.nextInt(NUMBERS.length -1 )])
                           : String.valueOf(CHARACTERS[random.nextInt(CHARACTERS.length -1 )]);
            isNumberTurn = random.nextBoolean();
        }
        
        return generatedId;
    }

    public static Integer generateVerificationCode( int bound ){
        Random random = new Random();
        return random.nextInt(bound);
    }
}
