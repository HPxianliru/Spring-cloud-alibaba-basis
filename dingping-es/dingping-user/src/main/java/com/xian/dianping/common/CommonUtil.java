package com.xian.dianping.common;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

/**
 * Created by hzllb on 2019/7/13.
 */
public class CommonUtil {
    public static String processErrorString(BindingResult bindingResult){
        if(!bindingResult.hasErrors()){
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        for(FieldError fieldError:bindingResult.getFieldErrors()){
            stringBuilder.append(fieldError.getDefaultMessage()+",");
        }
        return stringBuilder.substring(0,stringBuilder.length()-1);
    }
}
