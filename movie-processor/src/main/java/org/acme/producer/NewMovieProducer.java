package org.acme.producer;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class NewMovieProducer implements INewMovieProducer {
    @Inject
    @Channel("new-movie-out")
    Emitter<Long> movieCommentProducer;

    @Override
    public void publish(Long movieId) {
        movieCommentProducer.send(movieId);
    }
}
