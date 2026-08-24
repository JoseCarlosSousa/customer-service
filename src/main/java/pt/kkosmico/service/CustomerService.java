package pt.kkosmico.service;

import java.util.UUID;
import pt.kkosmico.dto.CustomerDTO;

public interface CustomerService {
    
    CustomerDTO getCustomerDtoById(UUID id);
    
    CustomerDTO updateCustomer(UUID id, CustomerDTO dto);

    CustomerDTO getCustomerDtoByEmail(String email);

    CustomerDTO updateCustomerByEmail(String email, CustomerDTO dto);
}
