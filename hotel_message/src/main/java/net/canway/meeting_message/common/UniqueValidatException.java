package net.canway.meeting_message.common;

public class UniqueValidatException extends RuntimeException{
    public UniqueValidatException(){}
    public UniqueValidatException(String message){
        super(message);
    }

}
