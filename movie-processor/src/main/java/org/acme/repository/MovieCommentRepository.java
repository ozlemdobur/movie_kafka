package org.acme.repository;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import org.acme.entity.MovieCommentEntity;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MovieCommentRepository implements PanacheRepository<MovieCommentEntity> {
}
