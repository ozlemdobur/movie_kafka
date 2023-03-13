package org.acme.repository;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import org.acme.entity.DirectorEntity;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DirectorRepository implements PanacheRepository<DirectorEntity> {
}
