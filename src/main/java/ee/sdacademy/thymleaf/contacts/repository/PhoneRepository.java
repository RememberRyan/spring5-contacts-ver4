package ee.sdacademy.thymleaf.contacts.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ee.sdacademy.thymleaf.contacts.domain.Phone;

public interface PhoneRepository extends CrudRepository<Phone, Integer> {

    List<Phone> findByContactId(Integer contactId);
}
