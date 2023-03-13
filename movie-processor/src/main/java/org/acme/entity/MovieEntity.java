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
public class MovieEntity {

    @Id
    @GeneratedValue(generator = "seq_movie")
    @SequenceGenerator(name = "seq_movie", sequenceName = "seq_movie", allocationSize = 1, initialValue = 1)
    private Long id;
    @Column(unique = true, nullable = false)
    private String title;
    private String description;
    private String country;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "director_id", referencedColumnName = "id")
    private DirectorEntity directorEntity;

    public DirectorEntity getDirectorEntity() {
        return directorEntity;
    }

    public void setDirectorEntity(DirectorEntity directorEntity) {
        this.directorEntity = directorEntity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
