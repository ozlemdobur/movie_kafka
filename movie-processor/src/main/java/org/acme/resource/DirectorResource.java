package org.acme.resource;

import io.smallrye.mutiny.Uni;
import org.acme.dto.DirectorDTO;
import org.acme.service.IDirectorService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.net.URI;

@Path("/director")
public class DirectorResource {

    private IDirectorService directorService;
    @Inject
    public DirectorResource(IDirectorService directorService) {
        this.directorService = directorService;
    }

    @POST
    public Uni<Response> save(DirectorDTO directorDTO){
        return directorService.save(directorDTO)
                .map(directorDTO1 -> Response.created(URI.create("/director/"+directorDTO1.getId()))
                        .entity(directorDTO1).build());
    }

    @GET
    @Path("{id}")
    public Uni<Response> findById(@PathParam("id") Long id){
        return directorService.findById(id)
                .map(directorDTO1 ->Response.ok(directorDTO1).build());
    }
}
