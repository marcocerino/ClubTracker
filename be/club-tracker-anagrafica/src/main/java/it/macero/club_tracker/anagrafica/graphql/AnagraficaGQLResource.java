package it.macero.club_tracker.anagrafica.graphql;

import io.smallrye.mutiny.Uni;
import it.macero.club_tracker.anagrafica.dto.Anagrafica;
import it.macero.club_tracker.anagrafica.service.AnagraficaService;
import jakarta.inject.Inject;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.Query;

import java.util.List;

@GraphQLApi
public class AnagraficaGQLResource {

    @Inject
    AnagraficaService service;

    @Query("allAnagrafica")
    @Description("Ottenere tutte le persone in banca dati")
    public Uni<List<Anagrafica>> getAllAnagrafica() {
        return service.getAll();
    }

    @Query("getAnagrafica")
    @Description("Ricerca una persona in banca dati")
    public Uni<Anagrafica> getAnagrafica(@Name("codFiscale") String codFiscale) {
        return service.getById(codFiscale);
    }

    @Query("insertAnagrafica")
    @Description("Aggiungere o aggiornare una persona in banca dati")
    public Uni<Anagrafica> insertAnagrafica(Anagrafica anagrafica) {
        return service.insert(anagrafica);
    }

    @Query("deleteAnagrafica")
    @Description("Rimuovere una persona in banca dati")
    public Uni<Boolean> deleteAnagrafica(@Name("codFiscale") String codFiscale) {
        return service.delete(codFiscale);
    }

}
