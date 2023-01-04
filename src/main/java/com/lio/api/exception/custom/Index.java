package com.lio.api.exception.custom;

public class Index {

    /*
      DuplicateAccountException
     */
    public static class DuplicateAccountException extends Exception{
        public DuplicateAccountException( String message ){
            super(message);
        }
    }

}
