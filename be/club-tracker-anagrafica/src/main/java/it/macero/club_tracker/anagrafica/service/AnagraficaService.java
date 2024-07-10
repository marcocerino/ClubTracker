package it.macero.club_tracker.anagrafica.service;

import io.smallrye.mutiny.Uni;
import it.macero.club_tracker.anagrafica.dto.Anagrafica;
import it.macero.club_tracker.anagrafica.entities.Persona;
import it.macero.club_tracker.anagrafica.mapper.AnagraficaMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class AnagraficaService {
    @Inject
    AnagraficaMapper mapper;

    public Uni<List<Anagrafica>> getAll() {
        return Persona.findAll().list()
                .map(l -> l.stream().parallel()
                        .map(p -> mapper.toAnagrafica((Persona) p))
                        .toList());
    }

    public Uni<Anagrafica> getById(String codFiscale) {
        return Persona.findById(codFiscale)
                .map(p -> mapper.toAnagrafica((Persona) p));
    }

    public Uni<Anagrafica> insert(Anagrafica a) {
        Persona persona = mapper.toPersona(a);
        return persona.persistOrUpdate().map(p -> mapper.toAnagrafica((Persona) p));
    }

    public Uni<Boolean> delete(String codFiscale) {
        return Persona.deleteById(codFiscale);
    }
}
