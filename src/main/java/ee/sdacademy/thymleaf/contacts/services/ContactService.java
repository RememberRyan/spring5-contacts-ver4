package ee.sdacademy.thymleaf.contacts.services;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ee.sdacademy.thymleaf.contacts.domain.Contact;
import ee.sdacademy.thymleaf.contacts.domain.Email;
import ee.sdacademy.thymleaf.contacts.domain.Phone;
import ee.sdacademy.thymleaf.contacts.model.ContactModel;
import ee.sdacademy.thymleaf.contacts.repository.ContactRepository;
import ee.sdacademy.thymleaf.contacts.repository.PhoneRepository;

@Service
public class ContactService {

    @Autowired
    private PhoneRepository phoneRepository;
    @Autowired
    private ContactRepository contactRepository;

    @Transactional
    public ContactModel save(ContactModel contact) {
        Contact save = contactRepository.saveAndFlush(mapToDomain(contact));
        return get(save.getId());
    }

    private Contact mapToDomain(final ContactModel model) {
        if (model.getId() == null) {
            return createNewDomain(model);
        }
        Contact contact = contactRepository.getOne(model.getId());
        contact.setName(model.getName());
        contact.setLastName(model.getLastName());
        contact.setStatus(model.getStatus());
        contact.setDescription(model.getDescription());
        mapModelExistingEmailsToDomain(contact, model.getEmails());

        return contact;
    }

    private Contact createNewDomain(final ContactModel model) {
        Contact contact = new Contact();
        contact.setName(model.getName());
        contact.setLastName(model.getLastName());
        contact.setStatus(model.getStatus());
        contact.setDescription(model.getDescription());
        contact.setEmails(mapModelEmailsToDomain(contact, model.getEmails()));
        return contact;
    }

    private List<Email> mapModelEmailsToDomain(Contact contact, final List<ContactModel.Email> emails) {
        return emails == null ? Collections.emptyList() : emails.stream().map(email -> mapNewModelEmailToDomain(contact, email)).collect(toList());
    }

    private Email mapNewModelEmailToDomain(Contact contact, final ContactModel.Email model) {
        Email email = new Email();
        return mapModelEmailToToDomainObject(contact, model, email);
    }

    private Email mapModelEmailToToDomainObject(final Contact contact, final ContactModel.Email model, final Email email) {
        email.setContact(contact);
        email.setEmail(model.getEmail());
        email.setType(model.getType());
        email.setDescription(model.getDescription());
        return email;
    }

    private void mapModelExistingEmailsToDomain(final Contact contact, List<ContactModel.Email> modelEmails) {
        if (modelEmails == null) {
            return;
        }


        Map<Integer, ContactModel.Email> modelEmailsMap = modelEmails.stream().filter(e -> e.getId() != null).
                collect(toMap(ContactModel.Email::getId, Function.identity()));

        Iterator<Email> emailIterator = contact.getEmails().iterator();

        while (emailIterator.hasNext()) {
            Email email = emailIterator.next();
            ContactModel.Email modelEmail = modelEmailsMap.get(email.getId());

            if (modelEmail == null || modelEmail.isDelete()) {
                emailIterator.remove();
                continue;
            }
            mapModelEmailToToDomainObject(contact, modelEmail, email);
        }
        modelEmails.stream().filter(email -> email.getId() == null).map(email -> mapNewModelEmailToDomain(contact, email)).forEach(email -> {
            contact.getEmails().add(email);
        });

    }

    @Transactional(readOnly = true)
    public ContactModel get(Integer id) {
        return contactRepository.findById(id).map(this::mapToModel).get();
    }

    private ContactModel mapToModel(final Contact contact) {
        ContactModel model = mapToShortModel(contact);
        model.setEmails(mapEmailsToModels(contact.getEmails()));
        return model;
    }

    private List<ContactModel.Email> mapEmailsToModels(final List<Email> emails) {
        return emails.stream().map(this::mapEmailToModel).collect(toList());
    }

    private ContactModel.Email mapEmailToModel(final Email email) {
        ContactModel.Email model = new ContactModel.Email();
        model.setId(email.getId());
        model.setEmail(email.getEmail());
        model.setDescription(email.getDescription());
        model.setType(email.getType());
        model.setDelete(false);
        return model;
    }

    public Page<ContactModel> find(final Integer page) {
        Page<Contact> queryPage = contactRepository.findAll(PageRequest.of(page - 1, 3));
        return queryPage.map(this::mapToShortModel);
    }

    private ContactModel mapToShortModel(final Contact contact) {
        ContactModel model = new ContactModel();
        model.setId(contact.getId());
        model.setName(contact.getName());
        model.setLastName(contact.getLastName());
        model.setStatus(contact.getStatus());
        model.setCreationTime(contact.getCreationTime());
        model.setDescription(contact.getDescription());
        return model;
    }

    public List<Phone> findPhoneNumbers(Integer contactId) {
        return phoneRepository.findByContactId(contactId);
    }

    @Transactional
    public void addNewEmail(final Integer contactId, final ContactModel.Email email) {
        Contact contact = contactRepository.getOne(contactId);
        contact.getEmails().add(mapNewModelEmailToDomain(contact, email));
    }
}
