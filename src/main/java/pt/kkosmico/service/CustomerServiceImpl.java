package pt.kkosmico.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.kkosmico.dto.CustomerDTO;
import pt.kkosmico.model.Customer;
import pt.kkosmico.model.User;
import pt.kkosmico.repository.CustomerRepository;
import pt.kkosmico.repository.UserRepository;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository, UserRepository userRepository) {
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
    }

    @Override
    public CustomerDTO getCustomerDtoById(UUID id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));
        return mapToDto(customer, "TODO");
    }

    @Override
    public CustomerDTO getCustomerDtoByEmail(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
        
        Customer customer = customerRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("No customer profile linked to this user account ID"));
        
        return mapToDto(customer, user.getEmail());
    }
    
    @Override
    @Transactional
    public CustomerDTO updateCustomer(UUID id, CustomerDTO dto) {
        Customer updated = mapFromDto(id, dto);
        return mapToDto(updated, "TODO");
    }
    
    @Override
    @Transactional
    public CustomerDTO updateCustomerByEmail(String email, CustomerDTO dto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
        
        
        Customer updated = mapFromDto(user.getId(), dto);
        return mapToDto(updated, user.getEmail());
    }
    
    private Customer mapFromDto(UUID id, CustomerDTO dto) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));
        
        customer.setFirstName(dto.getFirstName());
        customer.setLastName(dto.getLastName());
        customer.setPhoneNumber(dto.getPhoneNumber());
        customer.setPhonePrefix(dto.getPhonePrefix());
        customer.setGender(dto.getGender());
        customer.setAddress(dto.getAddress());
        customer.setCity(dto.getCity());
        customer.setState(dto.getState());
        customer.setZipCode(dto.getZipCode());
        customer.setCountry(dto.getCountry());
        
        return customerRepository.save(customer);
    }

    private CustomerDTO mapToDto(Customer customer, String email) {
        CustomerDTO dto = new CustomerDTO();
        dto.setEmail(email);
        dto.setFirstName(customer.getFirstName());
        dto.setLastName(customer.getLastName());
        dto.setPhoneNumber(customer.getPhoneNumber());
        dto.setPhonePrefix(customer.getPhonePrefix());
        dto.setGender(customer.getGender());
        dto.setAddress(customer.getAddress());
        dto.setCity(customer.getCity());
        dto.setState(customer.getState());
        dto.setZipCode(customer.getZipCode());
        dto.setCountry(customer.getCountry());
        return dto;
    }
}
