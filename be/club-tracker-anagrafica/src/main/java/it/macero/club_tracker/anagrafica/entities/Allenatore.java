package it.macero.club_tracker.anagrafica.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Allenatore implements Serializable {
    private List<RuoloAllenatore> categorie;
}

