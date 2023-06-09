package org.acme.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class DirectorDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String country;

}
