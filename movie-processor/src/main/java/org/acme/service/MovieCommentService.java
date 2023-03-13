package org.acme.service;

import io.quarkus.hibernate.reactive.panache.common.runtime.ReactiveTransactional;
import io.smallrye.mutiny.Uni;
import org.acme.dto.MovieCommentDTO;
import org.acme.entity.MovieCommentEntity;
import org.acme.mapper.MovieCommentMapper;
import org.acme.repository.MovieCommentRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.control.ActivateRequestContext;
import javax.inject.Inject;

@ApplicationScoped
public class MovieCommentService implements IMovieCommentService {
    private MovieCommentRepository movieCommentRepository;
    private MovieCommentMapper movieMapper = MovieCommentMapper.INSTANCE;

    @Inject
    public MovieCommentService(MovieCommentRepository movieCommentRepository) {
        this.movieCommentRepository = movieCommentRepository;
    }

    @ReactiveTransactional
    @ActivateRequestContext
    @Override
    public Uni<MovieCommentDTO> save(MovieCommentDTO movieCommentDTO) {
        MovieCommentEntity movieCommentEntity = movieMapper.movieCommentDTOToMovieCommentEntity(movieCommentDTO);
        return this.movieCommentRepository.persist(movieCommentEntity)
                .map(p -> movieMapper.movieCommentEntityToMovieCommentDTO(p));
    }
}
