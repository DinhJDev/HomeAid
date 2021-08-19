package com.homeaid.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.homeaid.models.Member;
@Component
public class MemberValidator implements Validator {
    
    //    1
    @Override
    public boolean supports(Class<?> clazz) {
        return Member.class.equals(clazz);
    }
    
    // 2
    @Override
    public void validate(Object object, Errors errors) {
        Member user = (Member) object;
        
        if (!user.getPasswordConfirmation().equals(user.getPassword())) {
            // 3
            errors.rejectValue("passwordConfirmation", "Match");
        }         
    }
}