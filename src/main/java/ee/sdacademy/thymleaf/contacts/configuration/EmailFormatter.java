package ee.sdacademy.thymleaf.contacts.configuration;

import java.io.IOException;
import java.text.ParseException;
import java.util.Locale;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ee.sdacademy.thymleaf.contacts.model.ContactModel;

public class EmailFormatter implements Formatter<ContactModel.Email> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public ContactModel.Email parse(final String text, final Locale locale) throws ParseException {
        if (StringUtils.isEmpty(text)) {
            return new ContactModel.Email();
        }
        try {
            return objectMapper.readValue(text, ContactModel.Email.class);
        } catch (IOException e) {
            return new ContactModel.Email();
        }
    }

    @Override
    public String print(final ContactModel.Email object, final Locale locale) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            return "";
        }
    }
}
