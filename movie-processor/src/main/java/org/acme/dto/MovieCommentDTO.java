package org.acme.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MovieCommentDTO {
    private Long id;
    private Long movieId;
    private String comment;

}
