package ee.sdacademy.thymleaf.contacts.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import ee.sdacademy.thymleaf.contacts.domain.Contact;

public interface ContactRepository extends JpaRepository<Contact, Integer> {

    List<Contact> findAll();

}
