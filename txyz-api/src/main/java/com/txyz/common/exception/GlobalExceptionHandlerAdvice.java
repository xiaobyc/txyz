package com.txyz.common.exception;

import com.txyz.common.constant.ResponseCode;
import com.txyz.common.result.ValidationResult;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandlerAdvice {
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public ValidationResult handler(ConstraintViolationException ex){
        if(ex.getConstraintViolations()!=null&&ex.getConstraintViolations().size()>0){
            ValidationResult  validationResult = new ValidationResult();
            StringBuilder stringBuilder = new StringBuilder();
            for(ConstraintViolation violation :ex.getConstraintViolations()){
                stringBuilder.append(violation.getMessage());
            }
            validationResult.setRetCode(ResponseCode.PARAM_ERROR);
            validationResult.setRetDesc(stringBuilder.toString());
            return validationResult;
        }
        throw  ex;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ValidationResult resolveMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        ValidationResult errorWebResult = new ValidationResult();
        List<ObjectError> objectErrors = ex.getBindingResult().getAllErrors();
        if(!CollectionUtils.isEmpty(objectErrors)) {
            StringBuilder msgBuilder = new StringBuilder();
            for (ObjectError objectError : objectErrors) {
                msgBuilder.append(objectError.getDefaultMessage()).append(",");
            }
            String errorMessage = msgBuilder.toString();
//            if (errorMessage.length() > 1) {
//                errorMessage = errorMessage.substring(0, errorMessage.length() - 1);
//            }
            errorWebResult.setRetDesc(errorMessage);
            errorWebResult.setRetCode(ResponseCode.PARAM_ERROR);
            return errorWebResult;
        }
        errorWebResult.setRetDesc(ex.getMessage());
        errorWebResult.setRetCode(ResponseCode.PARAM_ERROR);
        return errorWebResult;
    }

    @ExceptionHandler(RException.class)
    @ResponseBody
    public ValidationResult handle(RException ex){
        ValidationResult errorWebResult = new ValidationResult();
        errorWebResult.setRetDesc(ex.getMessage());
        errorWebResult.setRetCode(ex.getRetCode());
        return errorWebResult;
    }

}
