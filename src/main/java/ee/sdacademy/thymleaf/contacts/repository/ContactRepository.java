package ee.sdacademy.thymleaf.contacts.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ee.sdacademy.thymleaf.contacts.domain.Contact;

public interface ContactRepository extends CrudRepository<Contact, Integer> {

    List<Contact> findAll();
}
