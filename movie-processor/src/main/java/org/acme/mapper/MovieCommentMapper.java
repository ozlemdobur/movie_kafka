package org.acme.mapper;

import org.acme.dto.MovieCommentDTO;
import org.acme.entity.MovieCommentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MovieCommentMapper {
    MovieCommentMapper INSTANCE = Mappers.getMapper(MovieCommentMapper.class);



    MovieCommentEntity movieCommentDTOToMovieCommentEntity(MovieCommentDTO movieCommentDTO);

    MovieCommentDTO movieCommentEntityToMovieCommentDTO(MovieCommentEntity movieCommentEntity);
}
