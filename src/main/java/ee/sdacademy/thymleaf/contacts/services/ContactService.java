package ee.sdacademy.thymleaf.contacts.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ee.sdacademy.thymleaf.contacts.domain.Contact;
import ee.sdacademy.thymleaf.contacts.domain.Phone;
import ee.sdacademy.thymleaf.contacts.repository.ContactRepository;
import ee.sdacademy.thymleaf.contacts.repository.PhoneRepository;

@Service
public class ContactService {

    @Autowired
    private PhoneRepository phoneRepository;
    @Autowired
    private ContactRepository contactRepository;

    @Transactional
    public Contact save(Contact contact) {
        return contactRepository.save(contact);
    }

    public Contact get(Integer id) {
        return contactRepository.findOne(id);
    }

    public List<Contact> findAll() {
        return contactRepository.findAll();
    }

    public List<Phone> findPhoneNumbers(Integer contactId) {
        return phoneRepository.findByContactId(contactId);
    }
}
