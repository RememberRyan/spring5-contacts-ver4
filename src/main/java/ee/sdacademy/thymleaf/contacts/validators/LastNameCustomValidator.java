package ee.sdacademy.thymleaf.contacts.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LastNameCustomValidator implements ConstraintValidator<LastNameCustomConstraint, String> {

    private LastNameCustomConstraint lastNameCustomConstraint;

    @Override
    public void initialize(final LastNameCustomConstraint lastNameCustomConstraint) {

        this.lastNameCustomConstraint = lastNameCustomConstraint;
    }

    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext ctx) {
        return value != null && value.endsWith(lastNameCustomConstraint.expectEndsWith());
    }
}
