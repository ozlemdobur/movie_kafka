package org.acme.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.transform.Source;

@Data
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public class MovieDTO {
    private Long id;
    @NotNull(message = "Title may not be null")
    @NotBlank(message = "Title may not be blank")
    private String title;
    @NotNull(message = "Title may not be null")
    @NotBlank(message = "Title may not be blank")
    private String description;
    private String country;
    @NotNull
    private DirectorDTO director;

}
