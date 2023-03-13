package org.acme.service;

import io.smallrye.mutiny.Uni;
import org.acme.dto.MovieCommentDTO;
public interface IMovieCommentService {
  Uni<MovieCommentDTO> save(MovieCommentDTO movieComment);
}
