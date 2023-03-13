package org.acme.mapper;

import org.acme.dto.DirectorDTO;
import org.acme.dto.MovieDTO;
import org.acme.entity.DirectorEntity;
import org.acme.entity.MovieEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface MovieMapper {
    MovieMapper INSTANCE = Mappers.getMapper(MovieMapper.class);

    @Mapping(source = "directorEntity", target = "director")
   // @Mapping(source = "movieEntity", target = "movie")
    MovieDTO movieEntityToMovieDTO(MovieEntity movieEntity);

    List<MovieDTO> movieEntityToMovieDTOs(List<MovieEntity> movieEntity);
    @Mapping(source = "director", target = "directorEntity")
    MovieEntity movieDTOToMovieEntity(MovieDTO movieDTO);

    DirectorEntity directoryDTOToDirectoryEntity(DirectorDTO directorDTO);
    DirectorDTO directorEntityToDirectoryDTO(DirectorEntity directorEntity);
}
