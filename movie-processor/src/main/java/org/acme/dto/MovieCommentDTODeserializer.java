package org.acme.dto;

import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class MovieCommentDTODeserializer extends ObjectMapperDeserializer<MovieCommentDTO> {
    public MovieCommentDTODeserializer() {
        super(MovieCommentDTO.class);
    }
}