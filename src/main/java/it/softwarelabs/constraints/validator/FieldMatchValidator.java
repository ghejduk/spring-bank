package it.softwarelabs.constraints.validator;

import it.softwarelabs.constraints.FieldMatch;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {

    private String field;
    private String matchWith;
    private String message;
    private boolean inverse;

    public void initialize(FieldMatch fieldMatch) {
        field = fieldMatch.field();
        matchWith = fieldMatch.matchWith();
        message = fieldMatch.message();
        inverse = fieldMatch.inverse();
    }

    public boolean isValid(Object object, ConstraintValidatorContext context) {
        try {
            Field field = object.getClass().getDeclaredField(this.field);
            field.setAccessible(true);
            Object fieldValue = field.get(object);

            Field matchWith = object.getClass().getDeclaredField(this.matchWith);
            matchWith.setAccessible(true);
            Object matchWitchValue = matchWith.get(object);

            boolean result = fieldValue.equals(matchWitchValue);

            if (inverse) {
                result = !result;
            }

            if (!result) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(message)
                        .addPropertyNode(this.field)
                        .addConstraintViolation();
            }

            return result;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Could not access properties", e);
        }
    }
}
