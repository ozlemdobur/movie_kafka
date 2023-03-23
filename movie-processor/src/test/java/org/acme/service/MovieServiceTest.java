package org.acme.service;

import com.google.common.base.Verify;
import io.quarkus.test.TestReactiveTransaction;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.helpers.test.UniAssertSubscriber;
import org.acme.dto.MovieDTO;
import org.acme.entity.MovieEntity;
import org.acme.mapper.MovieMapper;
import org.acme.repository.MovieRepository;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;


import java.util.List;

import static org.eclipse.sisu.space.QualifiedTypeVisitor.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;


@QuarkusTest
class MovieServiceTest {
    @Inject
    IMovieService movieService;

    @InjectMock
    MovieRepository movieRepository;

    @InjectMock
    IMovieCommentService movieCommentService;

    MovieMapper mapper = MovieMapper.INSTANCE;

    @Test
    void listAll_titleIsNotNull_countryIsNull() {
        //GIVEN
        List<MovieEntity> movies = List.of(MovieEntity.builder().id(2L).title("Titanic").description("Romantic").country("USA").build());
       //WHEN
        Mockito.when(movieRepository.findByTitle("Titanic")).thenReturn(Uni.createFrom().item(movies));
        Uni<List<MovieDTO>> movieList = movieService.listAll("Titanic", null);
        //THEN
        UniAssertSubscriber<List<MovieDTO>> subscriber = movieList.subscribe().withSubscriber(UniAssertSubscriber.create());
        Assertions.assertEquals(subscriber.getItem().get(0).getTitle(),"Titanic");

    }

    @Test
    void listAll_titleIsNull_countryIsNotNull() {
        //GIVEN
        List<MovieEntity> movies = List.of(MovieEntity.builder().id(2L).title("Titanic").description("Romantic").country("USA").build());
        //WHEN
        Mockito.when(movieRepository.findByCountry("USA")).thenReturn(Uni.createFrom().item(movies));
        Uni<List<MovieDTO>> moviesList =movieService.listAll(null, "USA");
        //THEN
        UniAssertSubscriber<List<MovieDTO>> subscriber = moviesList.subscribe().withSubscriber(UniAssertSubscriber.create());
        Assertions.assertEquals("USA", subscriber.getItem().get(0).getCountry());
    }

    @Test
    void listAll_titleAndCountryIsNotNull() {
        //GIVEN
        List<MovieEntity> movies = List.of(MovieEntity.builder().id(2L).title("Titanic").description("Romantic").country("USA").build());
        //WHEN
        Mockito.when(movieRepository.findByTitleAndCountry("Titanic","USA")).thenReturn(Uni.createFrom().item(movies));
        Uni<List<MovieDTO>> moviesList =movieService.listAll("Titanic", "USA");
        //THEN
        UniAssertSubscriber<List<MovieDTO>> subscriber = moviesList.subscribe().withSubscriber(UniAssertSubscriber.create());
        Assertions.assertEquals("USA", subscriber.getItem().get(0).getCountry());
        Assertions.assertEquals("Titanic", subscriber.getItem().get(0).getTitle());
    }

    @Test
    void listAll() {
        //GIVEN
        List<MovieEntity> movies = List.of(MovieEntity.builder().id(2L).title("Titanic").description("Romantic").country("USA").build());
        //WHEN
        Mockito.when(movieRepository.listAll()).thenReturn(Uni.createFrom().item(movies));
        Uni<List<MovieDTO>> moviesList =movieService.listAll(null, null);
        //THEN
        UniAssertSubscriber<List<MovieDTO>> subscriber = moviesList.subscribe().withSubscriber(UniAssertSubscriber.create());
        Assertions.assertEquals("USA", subscriber.getItem().get(0).getCountry());
        Assertions.assertEquals("Titanic", subscriber.getItem().get(0).getTitle());
        Assertions.assertEquals("Romantic", subscriber.getItem().get(0).getDescription());
    }
  @Test
    void findById_NotFound() {
      // GIVEN
      final Long movieId = 111L;
      // WHEN
      Mockito.when(movieRepository.findById(movieId)).thenReturn(Uni.createFrom().nullItem());
      Uni<MovieDTO> movieDTOUni = movieService.findById(movieId);
      // THEN
      UniAssertSubscriber<Object> subscriber = movieDTOUni.subscribe().withSubscriber(UniAssertSubscriber.create());
      subscriber.assertFailedWith(WebApplicationException.class, "Not Found");
    }

/*    @Test
    void findByTitle() {
        //GIVEN
        MovieEntity movie = MovieEntity.builder().id(2L).title("Titanic").description("Romantic").country("USA").build();
        //WHEN
        Mockito.when(movieRepository.findByTitle("Titanic")).thenReturn(Uni.createFrom().item(movie));
        Uni<MovieDTO> movieDTOUni = movieService.findByTitle("Titanic");
        //THEN
        UniAssertSubscriber<MovieDTO> subscriber = movieDTOUni.subscribe().withSubscriber(UniAssertSubscriber.create());
        Assertions.assertEquals("Titanic",subscriber.getItem().getTitle());
    }*/

    @Test
    void findByCountry() {
        //GIVEN
        List<MovieEntity> movies = List.of(MovieEntity.builder().id(2L).title("Titanic").description("Romantic").country("USA").build());
        //WHEN
        Mockito.when(movieRepository.findByCountry("USA")).thenReturn(Uni.createFrom().item(movies));
        Uni<List<MovieDTO>> moviesList =movieService.findByCountry("USA");
        //THEN
        UniAssertSubscriber<List<MovieDTO>> subscriber = moviesList.subscribe().withSubscriber(UniAssertSubscriber.create());
        Assertions.assertEquals("USA", subscriber.getItem().get(0).getCountry());
    }

    @Test
    @TestReactiveTransaction
    void deleteById() {
        //GIVEN
        Uni<Boolean> result= Uni.createFrom().item(Boolean.valueOf("true"));
        MovieDTO movie = MovieDTO.builder().id(2L).title("Titanic").description("Romantic").country("USA").build();
        movieService.save(movie);
        //WHEN
        Mockito.when(movieRepository.deleteById(movie.getId())).thenReturn(result);
        Uni<Boolean> deleted = movieService.deleteById(movie.getId());
        //THEN
        UniAssertSubscriber<Boolean> subscriber = deleted.subscribe().withSubscriber(UniAssertSubscriber.create());
        Assertions.assertTrue(subscriber.getItem());

    }
    //Kafka mesaj gonderme kismi eksik
    @Test
    @TestReactiveTransaction
    void save_update() {
        //INSERT
        //GIVEN
        MovieDTO movieDTO = MovieDTO.builder().id(2L).title("Titanic").description("Romantic").country("USA").build();
        MovieEntity insertMovieEntity = mapper.movieDTOToMovieEntity(movieDTO);
        //WHEN
        Mockito.when(movieRepository.persist(insertMovieEntity)).thenReturn(Uni.createFrom().item(insertMovieEntity));
        Uni<MovieDTO> insertedMovie = movieService.save(mapper.movieEntityToMovieDTO(insertMovieEntity));
        //THEN
        //verify(repository, times(1)).findByPassengers_User_Id(any());
       // verify(movieCommentService, times(1));
        UniAssertSubscriber<MovieDTO> subscriberInsert = insertedMovie.subscribe().withSubscriber(UniAssertSubscriber.create());
        Assertions.assertNotNull(subscriberInsert.getItem());
        Assertions.assertEquals("Titanic", subscriberInsert.getItem().getTitle());

        //UPDATE
        //GIVEN
        Long id= 2L;
        movieDTO.setTitle("Interseteller");
        movieDTO.setCountry("UK");
        MovieEntity updatedMovie = mapper.movieDTOToMovieEntity(movieDTO);
        //WHEN
        Mockito.when(movieRepository.findById(id)).thenReturn(Uni.createFrom().item(insertMovieEntity));
        Mockito.when(movieRepository.persist(updatedMovie)).thenReturn(Uni.createFrom().item(updatedMovie));
        Uni<MovieDTO> updatedMovieDTO = movieService.update(id, movieDTO);
        //THEN
        UniAssertSubscriber<MovieDTO> updateSubscriber = updatedMovieDTO.subscribe().withSubscriber(UniAssertSubscriber.create());
        Assertions.assertEquals("UK", updateSubscriber.getItem().getCountry());
        Assertions.assertEquals("Interseteller",updateSubscriber.getItem().getTitle());
    }

    @Test
    @TestReactiveTransaction
    void update_conflict() {
        //GIVEN
        Long id= 3L;
        MovieDTO movieDTO = MovieDTO.builder().id(2L).title("Titanic").description("Romantic").country("USA").build();
        //WHEN
        Uni<MovieDTO> updateMovie = movieService.update(id,movieDTO);
        //THEN
        UniAssertSubscriber<MovieDTO> subscriber = updateMovie.subscribe().withSubscriber(UniAssertSubscriber.create());
        subscriber.assertFailedWith(WebApplicationException.class, "Conflict");

    }

    @Test
    @TestReactiveTransaction
    void update_notFound() {
        //GIVEN
        Long id= null;
        MovieDTO movieDTO = MovieDTO.builder().id(2L).title("Titanic").description("Romantic").country("USA").build();
        //WHEN
        Uni<MovieDTO> updateMovie = movieService.update(id,movieDTO);
        //THEN
        UniAssertSubscriber<MovieDTO> subscriber = updateMovie.subscribe().withSubscriber(UniAssertSubscriber.create());
        subscriber.assertFailedWith(WebApplicationException.class, "Conflict");

    }
}