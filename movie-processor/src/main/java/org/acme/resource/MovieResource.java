package org.acme.resource;

import io.smallrye.mutiny.Uni;
import org.acme.dto.MovieDTO;
import org.acme.service.IMovieService;

import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.net.URI;

import static javax.ws.rs.core.Response.Status.*;

@Path("/movies")
@Produces("application/json")
@Consumes("application/json")
public class MovieResource {

    private IMovieService movieService;

    @Inject
    public MovieResource(IMovieService movieService) {
        this.movieService = movieService;
    }

    @GET
    public Uni<Response> getAll(@QueryParam("title") String title, @QueryParam("country") String country) {
        return movieService.listAll(title, country).map(movie -> Response.ok(movie).build());
    }

    @GET
    @Path("{id}")
    public Uni<Response> getById(@PathParam("id") Long id) {
        return movieService.findById(id)
                //.onItem().ifNull().failWith(new WebApplicationException(NOT_FOUND))
                .map(movieDTO -> Response.ok(movieDTO).build());
    }

    @GET
    @Path("title/{title}")
    public Uni<Response> getByTitle(@Size(min = 4) @NotBlank @PathParam("title") String title) {

        return movieService.findByTitle(title)
                .map(movieDTO -> Response.ok(movieDTO).build());
    }

    @GET
    @Path("country/{country}")
    public Uni<Response> getByCountry(@PathParam("country") String country) {
        return movieService.findByCountry(country)
                .map(movieDTOs -> Response.ok(movieDTOs).build());

    }

    @DELETE
    @Path("{id}")
    public Uni<Response> delete(Long id) {
        return movieService.deleteById(id)
                .map(deleted -> deleted
                        ? Response.ok().status(NO_CONTENT).build()
                        : Response.ok().status(NOT_FOUND).build())
                .onFailure(ConstraintViolationException.class).recoverWithItem(e->{
                    return Response.status(BAD_REQUEST).entity(e.getMessage()).build();
                });

    }
//VALIDATING OF THE REST ENDPOINT
    @POST
    public Uni<Response> save(@Valid MovieDTO movieDTO) {
        return movieService.save(movieDTO)
                .map(movieDTO1 -> Response.created(URI.create("/movies/" + movieDTO1.getId())).entity(movieDTO1).build());
    }


/*    @POST
    public Uni<Response> save(MovieDTO movieDTO) throws ConstraintViolationException {
        return movieService.save(movieDTO)
                .map(movieDTO1 -> Response.created(URI.create("/movies/" + movieDTO1.getId())).entity(movieDTO1).build())
                .onFailure(ConstraintViolationException.class).recoverWithItem(e->{
                    return Response.status(BAD_REQUEST).entity(e.getMessage()).build();
                });
    }*/


    @PUT
    @Path("{id}")
    public Uni<Response> update(Long id,@Valid MovieDTO movieDTO) {

        Uni<Response> response = movieService.update(id, movieDTO)
                .map(movieDTO1 -> Response.ok(movieDTO1).build());
        return response;
    }

}
