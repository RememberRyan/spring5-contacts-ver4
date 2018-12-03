package ee.sdacademy.thymleaf.contacts.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import ee.sdacademy.thymleaf.contacts.domain.ContactStatus;
import ee.sdacademy.thymleaf.contacts.domain.DetailsType;
import ee.sdacademy.thymleaf.contacts.validators.LastNameCustomConstraint;
import lombok.Data;

@Data
public class ContactModel {

    private Integer id;
    @Length(min = 3, max = 50)
    private String name;
    @Length(min = 3, max = 50)
    private String lastName;
    private Date creationTime;
    private ContactStatus status = ContactStatus.ACTIVE;
    private String description;
    @Valid
    private List<Email> emails = new ArrayList<>();

    @Data
    public static class Phone {
        private Integer id;
        private int countryCode;
        private int phoneNumber;
        private DetailsType type;
        private String description;
        private boolean delete = false;
    }

    @Data
    public static class Email {
        private Integer id;
        @NotEmpty
        @javax.validation.constraints.Email
        private String email;
        private DetailsType type;
        private String description;
        private boolean delete = false;
    }

    @Data
    public static class Address {
        private Integer id;
        private String street;
        private String city;
        private String country;
        private String postalCode;
        private DetailsType type;
        private boolean delete = false;
    }

}
