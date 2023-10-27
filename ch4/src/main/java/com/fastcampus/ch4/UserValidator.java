package com.fastcampus.ch4;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.fastcampus.ch4.domain.User;


	public class UserValidator implements Validator {
		
		@Override
		public boolean supports(Class<?> clazz) {
			

			return User.class.isAssignableFrom(clazz);
		}

		@Override
		public void validate(Object target, Errors errors) { 
			System.out.println("LocalValidator.validate() is called");

			User user = (User)target;
			
			String id = user.getId();
			
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "id",  "required");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pwd", "required");
			
			if(id==null || id.length() <  5 || id.length() > 12) {
				errors.rejectValue("id", "invalidLength", new String[]{"5","12"}, null);
			}
		}
	}
	
