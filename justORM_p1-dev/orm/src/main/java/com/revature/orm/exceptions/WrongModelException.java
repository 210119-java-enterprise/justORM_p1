package com.revature.orm.exceptions;

/**
 * returns a wrong model exception
 */
public class WrongModelException extends RuntimeException{
    public WrongModelException(String message){super(message);};
}
