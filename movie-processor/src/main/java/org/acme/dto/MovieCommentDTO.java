package org.acme.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public class MovieCommentDTO {
    private Long id;
    private Long movieId;
    private String comment;

}
