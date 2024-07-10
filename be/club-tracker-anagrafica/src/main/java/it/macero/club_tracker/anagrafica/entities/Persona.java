package it.macero.club_tracker.anagrafica.entities;

import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoEntityBase;
import lombok.Getter;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonId;

import java.time.LocalDate;

@Getter
@Setter
public class Persona extends ReactivePanacheMongoEntityBase {
    @BsonId
    private String codFiscale;
    private String nome;
    private String cognome;
    private LocalDate dataNascita;
    private String email;
    private String telefono;
    private Atleta atleta;
    private Allenatore allenatore;
}
