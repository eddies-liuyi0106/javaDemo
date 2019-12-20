package net.canway.meeting_message;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AuthMail {
    public static boolean isEmail(String email){
        if (null==email || "".equals(email)){
            return false;
        }
        String regEx1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern p = Pattern.compile(regEx1);
        Matcher m = p.matcher(email);
        if(m.matches()){
            return true;
        }else{
            return false;
        }
    }
    public static void main(String[] args) {
        String msg = "1dasd23424@11.com";
        System.out.println(isEmail(msg));
    }

}
