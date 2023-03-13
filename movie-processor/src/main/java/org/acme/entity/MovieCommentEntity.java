package org.acme.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Table(name = "movie_comment")
@Entity
@RequiredArgsConstructor
@Data
public class MovieCommentEntity {
    @Id
    @GeneratedValue(generator = "seq_movie_comment")
    @SequenceGenerator(name = "seq_movie_comment", sequenceName = "seq_movie_comment", allocationSize = 1, initialValue = 1)
    private Long id;
    private Long movieId;
    private String comment;

}
