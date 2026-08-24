package pt.kkosmico.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {

    private UUID id;
    private String email;
    private String firstName;
    private String lastName;
    private String phonePrefix;
    private String phoneNumber;
    private String gender;
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private String country;
}
