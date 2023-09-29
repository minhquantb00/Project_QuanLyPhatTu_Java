package QL_Chua.Validator;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Set;

public class Validate {
    public static <E> String validator(E object) {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<E>> violations = validator.validate(object);

        if (!violations.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder();
            for (ConstraintViolation<E> x : violations) {
                String fieldErr = x.getPropertyPath().toString() + ": ";
                errorMessage.append(fieldErr)
                        .append(x.getMessage())
                        .append("\n");
            }
            return errorMessage.toString();
        }
        return null;
    }

}
