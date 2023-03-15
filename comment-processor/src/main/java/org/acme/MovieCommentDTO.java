package org.acme;

@RegisterForReflection
public class MovieCommentDTO {
    private Long movieId;
    private String comment;

    public MovieCommentDTO() {
    }

    public MovieCommentDTO(Long movieId, String comment) {
        this.movieId = movieId;
        this.comment = comment;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
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
