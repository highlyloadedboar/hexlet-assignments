package exercise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import exercise.model.Contact;
import exercise.repository.ContactRepository;
import exercise.dto.ContactDTO;
import exercise.dto.ContactCreateDTO;

@RestController
@RequestMapping("/contacts")
public class ContactsController {

    @Autowired
    private ContactRepository contactRepository;

    // BEGIN
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ContactDTO createContact(@RequestBody ContactCreateDTO contactCreateDTO) {
        Contact contact = toEntity(contactCreateDTO);
        contactRepository.save(contact);

        return toDto(contact);
    }

    private Contact toEntity(ContactCreateDTO contactDTO) {
        Contact contact = new Contact();
        contact.setPhone(contactDTO.getPhone());
        contact.setFirstName(contactDTO.getFirstName());
        contact.setLastName(contactDTO.getLastName());

        return contact;
    }

    private ContactDTO toDto(Contact contact) {
        ContactDTO dto = new ContactDTO();
        dto.setPhone(contact.getPhone());
        dto.setFirstName(contact.getFirstName());
        dto.setLastName(contact.getLastName());
        dto.setId(contact.getId());
        dto.setUpdatedAt(contact.getUpdatedAt());
        dto.setCreatedAt(contact.getCreatedAt());

        return dto;
    }
    // END
}
