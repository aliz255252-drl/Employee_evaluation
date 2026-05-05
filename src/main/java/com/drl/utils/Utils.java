package com.drl.utils;

import java.text.ParseException;
import java.util.Collection;
import java.util.Map;
import java.util.Random;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.drl.entities.User;

@Component
public class Utils {
	private static Validator validator;

	public Utils(Validator validator) {
		super();
		Utils.validator = validator;
	}

	public static String validate(Object object) {
		Errors violations = validator.validateObject(object);
		if (violations.hasErrors()) {
			StringBuilder sb = new StringBuilder();
			violations.getAllErrors().forEach(e -> {
				sb.append(e.getDefaultMessage() + "\n");
			});
			return sb.toString();
		}
		return "";
	}

	/**
	 * This method returns true if the collection is null or is empty.
	 * 
	 * @param collection
	 * @return true | false
	 */
	public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

	/**
	 * This method returns true of the map is null or is empty.
	 * 
	 * @param map
	 * @return true | false
	 */
	public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

	/**
	 * This method returns true if the object is null.
	 * 
	 * @param object
	 * @return true | false
	 */
	public static boolean isEmpty(Object object) {
        return object == null;
    }

	/**
	 * This method returns true if the input array is null or its length is zero.
	 * 
	 * @param array
	 * @return true | false
	 */
	public static boolean isEmpty(Object[] array) {
        return array == null || array.length == 0;
    }

	/**
	 * This method returns true if the input string is null or its length is zero.
	 * 
	 * @param string
	 * @return true | false
	 */
	public static boolean isEmpty(String string) {
        return string == null || string.trim().length() == 0;
    }
	
	public static User getCurrentUser() {
		SecurityContext context =  SecurityContextHolder.getContext();
		if(!Utils.isEmpty(context) && !Utils.isEmpty(context.getAuthentication().getPrincipal()) && context.getAuthentication().getPrincipal() != "anonymousUser") {
			return (User) context.getAuthentication().getPrincipal();
		} else {
			return null;
		}
	}
	public static void main(String[] args) throws ParseException {
		emailTesting();
	}
	private static void emailTesting() {

		String toEmail = "waqar.ah.awan@gmail.com";
		String subject = "GRMS Test";
		String message = "Dear User, thankyou for registering in GRMS Portal.\n" +
				         "Your OTP is :  123456\n";

//		+ <a href="ftp://someftpserver.com/">	"
//		"<a ftp=\"url\">link text</a>;

		// ftp://honeycomberp.webhop.net

		System.out.println("Before Thread");
//		EmailUtils.sendEmail(toEmail, subject, message);

		System.out.println("After Thread");
	}
	public static Integer generateOTP() {
		Random random = new Random();
		return 100000 + random.nextInt(900000);
	}

}
