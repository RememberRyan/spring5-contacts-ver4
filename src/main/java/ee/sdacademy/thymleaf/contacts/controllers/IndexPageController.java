package ee.sdacademy.thymleaf.contacts.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ee.sdacademy.thymleaf.contacts.domain.ContactStatus;
import ee.sdacademy.thymleaf.contacts.domain.DetailsType;
import ee.sdacademy.thymleaf.contacts.model.ContactModel;
import ee.sdacademy.thymleaf.contacts.services.ContactService;
import ee.sdacademy.thymleaf.contacts.validators.ContactValidator;

@Controller
public class IndexPageController {
    @Autowired
    private ContactService contactService;
    @Autowired
    private ContactValidator contactValidator;


    @GetMapping("/")
    public String mainPage(@RequestParam(value = "page", defaultValue = "1") Integer page, Model model) {
        model.addAttribute("contacts", contactService.find(page));
        return "index";
    }

    @GetMapping("/view")
    public String viewContact(@RequestParam("id") Integer id, Model model) {
        model.addAttribute("contact", contactService.get(id));
        model.addAttribute("phones", contactService.findPhoneNumbers(id));
        return "view";
    }

    @GetMapping("/createContact")
    public String createContactPage(Model model) {
        model.addAttribute("newContact", true);
        ContactModel o = new ContactModel();
        o.getEmails().add(new ContactModel.Email());
        model.addAttribute("contact", o);
        return "createContact";
    }

    @GetMapping("/editContact/{id}")
    public String editContactPage(@PathVariable("id") Integer id, @ModelAttribute("newEmail") ContactModel.Email email, Model model, HttpSession session) {
        model.addAttribute("contact", contactService.get(id));
        model.addAttribute("newEmail", email);
        if (session.getAttribute("binding") != null) {
            model.addAttribute("org.springframework.validation.BindingResult.newEmail", session.getAttribute("binding"));
            session.removeAttribute("binding");
        }
        return "editContact";
    }

    @PostMapping("/editContact/{id}")
    public String editContactPage(
            @PathVariable("id") Integer id,
            @Valid ContactModel contact,
            BindingResult bindingResult,
            Model model) {

        if (!id.equals(contact.getId())) {
            ObjectError objectError = new ObjectError("id", "Don't try to hack me");
            bindingResult.addError(objectError);
        }
        if (!bindingResult.hasErrors()) {
            ContactModel contactModel = contactService.save(contact);
            model.addAttribute("contact", contactModel);
        } else {
            model.addAttribute("contact", contact);
        }

        model.addAttribute("newEmail", new ContactModel.Email());
        return "editContact";

    }

    @PostMapping("/createContact")
    public String createContact(@Valid @ModelAttribute("contact") ContactModel contact, BindingResult result, Model model) {
        model.addAttribute("newContact", true);
        if (result.hasErrors()) {
            return "createContact";
        }
        ContactModel createContact = contactService.save(contact);
        return "redirect:/view?id=" + createContact.getId();

    }

    @ModelAttribute("statuses")
    public ContactStatus[] statuses() {
        return ContactStatus.values();
    }

    @ModelAttribute("detailsTypes")
    public DetailsType[] detailsTypes() {
        return DetailsType.values();
    }

    @PostMapping("/editContact/{contactId}/addEmail")
    public String addEmail(@PathVariable("contactId") Integer contactId, @Valid ContactModel.Email email,
                           BindingResult result,
                           RedirectAttributes redirectAttributes, HttpSession httpSession) {

        if (result.hasErrors()) {
            redirectAttributes.addAttribute("newEmail", email);
            httpSession.setAttribute("binding", result);
        } else {
            contactService.addNewEmail(contactId, email);
        }
        return "redirect:/editContact/" + contactId;
    }
}
