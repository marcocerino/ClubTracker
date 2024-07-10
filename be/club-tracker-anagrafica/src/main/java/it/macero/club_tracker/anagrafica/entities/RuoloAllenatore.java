package it.macero.club_tracker.anagrafica.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RuoloAllenatore {
    private CATEGORIA categoria;
    private String ruolo;
}
