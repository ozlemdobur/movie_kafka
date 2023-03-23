package org.acme;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GenerateNewComment {
    @Incoming("new-movie")
    @Outgoing("new-comment-out")
    public MovieCommentDTO process(Long newMovieId) throws InterruptedException{
        System.out.println(newMovieId.toString());
        return new MovieCommentDTO(newMovieId, "Movie is created...");
    }

}
