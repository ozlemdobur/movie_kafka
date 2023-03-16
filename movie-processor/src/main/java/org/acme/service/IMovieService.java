package org.acme.service;

import io.smallrye.mutiny.Uni;
import org.acme.dto.MovieDTO;
import org.acme.entity.MovieEntity;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

public interface IMovieService {
    Uni<List<MovieDTO>> listAll(String title, String country);

    Uni<MovieDTO> findById(Long id);

    Uni<MovieDTO> findByTitle(String title);

    Uni<List<MovieDTO>> findByCountry(String country);

    Uni<Boolean> deleteById(Long id);

    //Uni<MovieDTO> save(MovieDTO movieDTO);
    Uni<MovieDTO> save(MovieDTO movieDTO);

    Uni<MovieDTO> update(Long id, MovieDTO movieDTO);
}