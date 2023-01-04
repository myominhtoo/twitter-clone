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

    /*
     for all invalid requests
     */
    public static class InvalidRequestExeption extends Exception{
        public InvalidRequestExeption( String message ){
            super(message);
        }
    }

}
