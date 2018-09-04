package com.haiercash.pluslink.capital.common.utils;

import com.haiercash.pluslink.capital.common.exception.PlCapitalException;
import com.haiercash.pluslink.capital.enums.ReturnCode;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotNull;
import java.lang.annotation.Annotation;
import java.util.Iterator;
import java.util.Set;

/**
 * 注解验证
 *
 * @author keliang.jiang
 * @date 2017/4/21.
 */
public class ValidateAnnotationUtils {

    /**
     * 校验注解是否通过
     *
     * @param t
     * @param <T>
     */
    public static <T> void validate(T t) {
        if (null == t) {
            PlCapitalException ex = new PlCapitalException(ReturnCode.msg_request_is_null,
                    "请求参数不能为空");
            throw ex;
        }

        ValidatorFactory vFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = vFactory.getValidator();
        Set set = validator.validate(t);                //  验证返回结果
        if (set.size() > 0) {
            for (Iterator<ConstraintViolation> itr = set.iterator(); itr.hasNext(); ) {
                ConstraintViolation constraintVio = itr.next();
                Annotation annotation = constraintVio.getConstraintDescriptor().getAnnotation();    //  报错注释
                String errorMsg = constraintVio.getMessage();                       //  报错信息
                if ((annotation instanceof NotNull)
                        || (annotation instanceof NotEmpty)
                        || (annotation instanceof NotBlank)) {
                    PlCapitalException ex = new PlCapitalException(ReturnCode.msg_request_param_is_null, errorMsg);
                    throw ex;
                } else {
                    PlCapitalException ex = new PlCapitalException(ReturnCode.msg_request_param_not_valid, errorMsg);
                    throw ex;
                }
            }
        }
    }

}
