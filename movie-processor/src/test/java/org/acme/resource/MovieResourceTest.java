package org.acme.resource;

import io.quarkus.gizmo.Gizmo;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.acme.dto.DirectorDTO;
import org.acme.dto.MovieDTO;
import org.acme.entity.MovieEntity;
import org.gradle.internal.impldep.javax.annotation.meta.When;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class MovieResourceTest {

    @Test
    void getAll() {
        given().when().get().then().statusCode(200);
    }
    @Test
    void getById_success() {
        MovieDTO movieDTO = given().when().get("/movies/1").then().statusCode(200).extract().as(MovieDTO.class);
        Assertions.assertNotNull(movieDTO);
        Assertions.assertNotNull(movieDTO.getDirector());
        Assertions.assertEquals(Long.valueOf(1), movieDTO.getId());

    }

    @Test
    void getById_fail() {
        given().when().get("/movies/11111").then().statusCode(404);
    }

    @Test
    void getByTitle_success() {
        MovieDTO movieDTO = given().when().get("/movies/title/Cherry").then().statusCode(200).extract().as(MovieDTO.class);
        Assertions.assertNotNull(movieDTO);
        Assertions.assertNotNull(movieDTO.getTitle());
    }

    @Test
    void getByTitle_fail() {
        given().when().get("/movies/title/AAAAA").then().statusCode(404);
    }

    @Test
    void getByCountry_success() {
        MovieDTO[] movieDTO = given().when().get("/movies/country/NL").then().statusCode(200).extract().as(MovieDTO[].class);
        Assertions.assertNotNull(movieDTO);
        Assertions.assertNotNull(movieDTO[0].getCountry());
        Assertions.assertNotNull(movieDTO[0].getTitle());
    }

    @Test
    void getByCountry_fail() {
        given().when().get("/movies/country/Country").then().statusCode(404);
    }

    @Test
    void delete_success() {
        //Adding new Movie for delete testing
        MovieDTO movieDTO = MovieDTO.builder()
                .title("Titanic").description("Romantic").country("USA")
                .director(DirectorDTO.builder().id(1L).build()).build();

        MovieDTO insertedMovie = given().contentType(ContentType.JSON).body(movieDTO).when().post("/movies").then().statusCode(201).extract().as(MovieDTO.class);
        given().when().delete("/movies/"+insertedMovie.getId()).then().statusCode(204);
    }

    @Test
    void delete_fail() {
        given().when().delete("/movies/"+11111).then().statusCode(404);
    }

    @Test
    void save_success() {
        //Add new Movie
        MovieDTO movieDTO = MovieDTO.builder()
                .title("Titanic").description("Romantic").country("USA")
                .director(DirectorDTO.builder().id(1L).build()).build();

        MovieDTO insertedMovie = given().contentType(ContentType.JSON).body(movieDTO)
                .when().post("/movies")
                .then().statusCode(201).extract().as(MovieDTO.class);

        Assertions.assertNotNull(insertedMovie);
        Assertions.assertNotNull(insertedMovie.getTitle());

    }
    @Test
    void update_conflict() {
        MovieDTO movie = given().when().get("movies/1").then().extract().as(MovieDTO.class);
        movie.setCountry("USA");
        movie.setTitle("Titanic");
        movie.setDescription("Romantic");

        given().contentType(ContentType.JSON).body(movie)
                .when().put("/movies/2")
                .then().statusCode(409);
    }

    @Test
    void update_notfound() {
        MovieDTO movie = new MovieDTO();
        given().contentType(ContentType.JSON).body(movie)
                .when().put("/movies/"+movie.getId())
                .then().statusCode(404);
    }
    @Test
    void update_success() {
        MovieDTO movie = given().when().get("movies/1").then().extract().as(MovieDTO.class);
        movie.setCountry("USA");
        movie.setTitle("Titanic");
        movie.setDescription("Romantic");

        MovieDTO updatedMovie = given().contentType(ContentType.JSON).body(movie)
                                .when().put("/movies/1")
                                .then().statusCode(200).extract().as(MovieDTO.class);
        Assertions.assertNotNull(updatedMovie);
        Assertions.assertEquals("Titanic",updatedMovie.getTitle());
        Assertions.assertEquals("USA",updatedMovie.getCountry());
        Assertions.assertEquals("Romantic",updatedMovie.getDescription());
    }
}