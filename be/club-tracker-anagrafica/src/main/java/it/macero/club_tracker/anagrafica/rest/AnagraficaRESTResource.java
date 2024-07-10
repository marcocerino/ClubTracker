package it.macero.club_tracker.anagrafica.rest;

import io.smallrye.mutiny.Uni;
import it.macero.club_tracker.anagrafica.dto.Anagrafica;
import it.macero.club_tracker.anagrafica.service.AnagraficaService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.reactive.ResponseStatus;

import java.util.List;

@Path("/anagrafica")
@Tag(name = "Anagrafiche")
@ApplicationScoped
public class AnagraficaRESTResource {

    @Inject
    AnagraficaService service;

    @GET
    @ResponseStatus(200)
    public Uni<List<Anagrafica>> getAllAnagrafica() {
        return service.getAll();
    }

    @GET
    @ResponseStatus(200)
    @Path("/{codFiscale}")
    public Uni<Anagrafica> getAnagrafica(@PathParam("codFiscale") String codFiscale) {
        return service.getById(codFiscale);
    }

    @PUT
    @ResponseStatus(202)
    public Uni<Anagrafica> insertAnagrafica(Anagrafica anagrafica) {
        return service.insert(anagrafica);
    }

    @DELETE
    @Path("/{codFiscale}")
    public Uni<Response> deleteAnagrafica(@PathParam("codFiscale") String codFiscale) {
        return service.delete(codFiscale)
                .map(b -> Response.status(Boolean.TRUE.equals(b) ? Response.Status.NO_CONTENT : Response.Status.NOT_FOUND).build());
    }
}
