package ee.sdacademy.thymleaf.contacts.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ee.sdacademy.thymleaf.contacts.domain.Contact;
import ee.sdacademy.thymleaf.contacts.services.ContactService;

@Controller
public class IndexPageController {



    @Autowired
    private ContactService contactService;

    @GetMapping("/")
    public String mainPage(Model model) {
        model.addAttribute("contacts", contactService.findAll());
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
        Contact o = new Contact();
        model.addAttribute("contact", o);
        return "createEditContact";
    }

    @GetMapping("/editContact/{id}")
    public String editContactPage(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("contact", contactService.get(id));
        return "editContact";
    }

    @PostMapping("/editContact/{id}")
    public String editContactPage(
            @PathVariable("id") Integer id,
            @Valid Contact contact,
            BindingResult bindingResult,
            Model model) {

        if (!id.equals(contact.getId())) {
            ObjectError objectError = new ObjectError("id", "Don't try to hack me");
            bindingResult.addError(objectError);
        }

        model.addAttribute("contact", contact);
        return "editContact";

    }

    @PostMapping("/createContact")
    public String createContact(@Valid @ModelAttribute Contact contact, BindingResult bindingResult, Model model) {
        model.addAttribute("newContact", true);
        if (bindingResult.hasErrors()) {
            return "createEditContact";
        }
        Contact createContact = contactService.save(contact);
        return "redirect:/view?id=" + createContact.getId();

    }


}
