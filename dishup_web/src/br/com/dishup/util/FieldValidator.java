package br.com.dishup.util;

import java.util.regex.Pattern;

public class FieldValidator {
	
	public static boolean isEmail(String email){
        Pattern pattern = Pattern.compile(".+@.+\\.[a-z]+");
        return pattern.matcher(email).matches();
	}
 
	public static boolean isEmpty(String var){
        boolean status = true;
		Pattern pattern = Pattern.compile("\\s+");
        if(pattern.matcher(var).matches() || var.isEmpty())
        	status = true;
        else
        	status = false;
        return status;
	}
	
	public static boolean isValidPassword(String var){
		Pattern pattern1 = Pattern.compile("\\w{6,18}");
		return (pattern1.matcher(var).matches());
	}
}
