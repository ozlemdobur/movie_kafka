package org.acme.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.List;
@Entity
@Table(name = "director")
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class DirectorEntity {
    @Id
    @GeneratedValue(generator = "seq_director")
    @SequenceGenerator(name = "seq_director", sequenceName = "seq_director", allocationSize = 1, initialValue = 1)
    private Long id;

    private String firstName;
    private String lastName;
    private String country;
    @OneToMany(mappedBy = "directorEntity")
    List<MovieEntity> movies;
}
