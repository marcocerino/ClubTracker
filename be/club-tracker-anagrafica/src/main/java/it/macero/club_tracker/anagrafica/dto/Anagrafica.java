package it.macero.club_tracker.anagrafica.dto;

import it.macero.club_tracker.anagrafica.entities.CATEGORIA;
import it.macero.club_tracker.anagrafica.entities.RuoloAllenatore;

import java.time.LocalDate;
import java.util.List;

public record Anagrafica(String codFiscale, String nome, String cognome, LocalDate dataNascita, String email,
                         String telefono, List<RuoloAllenatore> categorieAllenatore, List<CATEGORIA> categorieAtleta) {
}
