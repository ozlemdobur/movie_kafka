package org.acme.service;

import io.quarkus.hibernate.reactive.panache.common.runtime.ReactiveTransactional;
import io.smallrye.mutiny.Uni;
import lombok.Builder;
import org.acme.dto.MovieCommentDTO;
import org.acme.dto.MovieDTO;
import org.acme.entity.DirectorEntity;
import org.acme.entity.MovieEntity;
import org.acme.mapper.MovieMapper;
import org.acme.producer.INewMovieProducer;
import org.acme.repository.MovieRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.ws.rs.WebApplicationException;
import java.util.List;
;

@ApplicationScoped
public class MovieService implements IMovieService {
    private MovieRepository movieRepository;
    private INewMovieProducer movieCommentProducer;
    private MovieMapper movieMapper = MovieMapper.INSTANCE;
    private MovieCommentDTO newComment;

    @Inject
    public MovieService(MovieRepository movieRepository, INewMovieProducer movieCommentProducer) {
        this.movieRepository = movieRepository;
        this.movieCommentProducer = movieCommentProducer;
    }

    public Uni<List<MovieDTO>> listAll() {
        return movieRepository.listAll().map(p -> movieMapper.movieEntityToMovieDTOs(p));
    }

    public Uni<MovieDTO> findById(Long id) {
        return movieRepository.findById(id)
                .onItem().ifNull().failWith(new WebApplicationException("Not Found", 404))
                .onItem().transform(movieEntity -> movieMapper.movieEntityToMovieDTO(movieEntity));
    }

    public Uni<MovieDTO> findByTitle(String title) {
        return movieRepository.find("title", title).singleResult()
                .onItem().transform(movieEntity -> movieMapper.movieEntityToMovieDTO(movieEntity))
                //.onFailure(ConstraintViolationException.class).transform(throwable -> new org.hibernate.reactive.exception.ConstraintViolationException());
                .onFailure(NoResultException.class).transform(throwable -> new WebApplicationException("Not Found", 404));
    }

    public Uni<List<MovieDTO>> findByCountry(String country) {

        return movieRepository.findByCountry(country)
                .onItem().transform(movieEntity -> movieMapper.movieEntityToMovieDTOs(movieEntity));
    }

    @ReactiveTransactional
    public Uni<Boolean> deleteById(Long id) {
        return movieRepository.deleteById(id);
    }

    //VALIDATING IN SERVICE
    @ReactiveTransactional
    public Uni<MovieDTO> save(MovieDTO movieDTO) {
        MovieEntity movieEntity = movieMapper.movieDTOToMovieEntity(movieDTO);

        Uni<MovieEntity> newMovieEntity = movieRepository.persist(movieEntity);

        return newMovieEntity.onItem().invoke(movieEntity1 -> movieCommentProducer.publish(movieEntity1.getId()))
                .map(movieEntity1 -> movieMapper.movieEntityToMovieDTO(movieEntity1));
    }

    @ReactiveTransactional
    public Uni<MovieDTO> update(Long id, MovieDTO movieDTO) throws ConstraintViolationException {

        if (movieDTO == null || movieDTO.getId() == null) {
            throw new WebApplicationException("Not Found", 404);
        }
        if (id != movieDTO.getId()) {
            throw new WebApplicationException("Conflict", 409);
        }

        return movieRepository.findById(id)
                .onItem()
                .transform(movieEntity1 -> movieMapper.movieDTOToMovieEntity(movieDTO))
                .invoke(movieEntity -> movieRepository.persist(movieEntity))
                .map(movieEntity -> movieMapper.movieEntityToMovieDTO(movieEntity));

    }
}
