package org.acme.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Table(name = "movie")
@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Data
@Builder
public class MovieEntity {

    @Id
    @GeneratedValue(generator = "seq_movie")
    @SequenceGenerator(name = "seq_movie", sequenceName = "seq_movie", allocationSize = 1, initialValue = 1)
    private Long id;
    //@Column(unique = true, nullable = false)
    private String title;
    private String description;
    private String country;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "director_id", referencedColumnName = "id")
    private DirectorEntity directorEntity;

}
