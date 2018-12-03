package ee.sdacademy.thymleaf.contacts.validators;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.executable.ExecutableValidator;
import javax.validation.metadata.BeanDescriptor;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import ee.sdacademy.thymleaf.contacts.model.ContactModel;


@Component
public class ContactValidator implements Validator {

    @Override
    public boolean supports(final Class<?> clazz) {
        return ContactModel.class.equals(clazz);
    }

    @Override
    public void validate(final Object target, final Errors errors) {
        ContactModel input = (ContactModel) target;

    }
}
