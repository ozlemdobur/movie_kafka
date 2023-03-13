package org.acme.repository;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;
import org.acme.entity.MovieEntity;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class MovieRepository implements PanacheRepository<MovieEntity> {
    public Uni<List<MovieEntity>> findByCountry(String country) {
        return list("SELECT m FROM MovieEntity m WHERE m.country = ?1 ORDER BY id DESC", country);
    }
}
