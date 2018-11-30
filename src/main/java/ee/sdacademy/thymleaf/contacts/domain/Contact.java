package ee.sdacademy.thymleaf.contacts.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import ee.sdacademy.thymleaf.contacts.validators.LastNameCustomConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Length(min = 3, max = 50)
    private String name;
    @Length(min = 3, max = 50)
    @LastNameCustomConstraint(expectEndsWith = "ov")
    private String lastName;
    @CreationTimestamp
    private Date creationTime;
    @Enumerated(EnumType.STRING)
    private ContactStatus status = ContactStatus.ACTIVE;
    @Max(300)
    private String description;

}
