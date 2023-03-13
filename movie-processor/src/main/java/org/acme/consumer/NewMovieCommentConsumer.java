package org.acme.consumer;

import org.acme.dto.MovieCommentDTO;
import org.acme.service.IMovieCommentService;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
@ApplicationScoped
public class NewMovieCommentConsumer {
    IMovieCommentService movieCommentService;
    @Inject
    public NewMovieCommentConsumer(IMovieCommentService movieCommentService) {
        this.movieCommentService = movieCommentService;
    }
    @Incoming("new-comment")
    public void store(MovieCommentDTO movieCommentDTO) {
        System.out.println(movieCommentDTO.toString());
        this.movieCommentService.save(movieCommentDTO).subscribe()
                .with(item-> System.out.println("It is ok "+item),
                        failure-> System.out.println("Failed"+failure));
        System.out.println("end");

    }
}
