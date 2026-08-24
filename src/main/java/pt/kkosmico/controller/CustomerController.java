package pt.kkosmico.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.kkosmico.dto.CustomerDTO;
import pt.kkosmico.service.CustomerService;
import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getById(@PathVariable UUID id) { // 🌟 Changed to UUID
        CustomerDTO customerDTO = customerService.getCustomerDtoById(id);
        return ResponseEntity.ok(customerDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> update(@PathVariable UUID id, @RequestBody CustomerDTO dto) { // 🌟 Changed to UUID
        CustomerDTO updatedDTO = customerService.updateCustomer(id, dto);
        return ResponseEntity.ok(updatedDTO);
    }

    /**
     * 🌟 EndPoint to fetch the currently logged-in customer's profile using the JWT email.
     */
    @GetMapping("/me")
    public ResponseEntity<CustomerDTO> getMyProfile(Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(401).build();
        }
        // Extracts the authenticated email from the security context
        String email = principal.getName();
        return ResponseEntity.ok(customerService.getCustomerDtoByEmail(email));
    }

    /**
     * 🌟 EndPoint to update the currently logged-in customer's profile using the JWT email.
     */
    @PutMapping("/me")
    public ResponseEntity<CustomerDTO> updateMyProfile(Principal principal, @RequestBody CustomerDTO dto) {
        if (principal == null) {
            return ResponseEntity.status(401).build();
        }
        String email = principal.getName();
        return ResponseEntity.ok(customerService.updateCustomerByEmail(email, dto));
    }
}
