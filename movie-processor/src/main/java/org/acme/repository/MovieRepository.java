package org.acme.repository;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;
import org.acme.entity.MovieEntity;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class MovieRepository implements PanacheRepository<MovieEntity> {
    public Uni<List<MovieEntity>> findByCountry(String country) {
        return list("country = ?1 ORDER BY id DESC", country);
    }
    public Uni<List<MovieEntity>> findByTitle(String title) {
        return list("title= ?1 ORDER BY id DESC", title);
    }
    public Uni<List<MovieEntity>> findByTitleAndCountry(String country, String title) {
        return list("country = ?1 and title= ?2 ORDER BY id DESC", country,title);
    }


}
