package daniel.kiszel.demo.util;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ObjectFieldValidator {

    /***
     * This method will check the property of an object
     * @param object
     * @return responseEntity with bad request & errors
     */
    public ResponseEntity checkFields(Object object){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Object>> violations = validator.validate(object);

        Map<String, String> errors = new HashMap<>();
        violations.forEach((error) ->{

            String fieldName = error.getPropertyPath().toString();
            String message = error.getMessage();
            errors.put(fieldName, message);
        });
        if(!errors.isEmpty()){
            return new ResponseEntity(errors, HttpStatus.BAD_REQUEST);
        }
        return null;
    }
}
