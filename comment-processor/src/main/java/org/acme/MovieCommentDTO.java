package org.acme;
public class MovieCommentDTO {
    private Long movieId;
    private String comment;

    public MovieCommentDTO(Long movieId, String comment) {
        this.movieId = movieId;
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "MovieCommentDTO{" +
                "movieId=" + movieId +
                ", comment='" + comment + '\'' +
                '}';
    }
}
