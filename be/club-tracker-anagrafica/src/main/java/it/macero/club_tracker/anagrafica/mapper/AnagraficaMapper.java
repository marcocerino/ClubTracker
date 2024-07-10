package it.macero.club_tracker.anagrafica.mapper;

import it.macero.club_tracker.anagrafica.dto.Anagrafica;
import it.macero.club_tracker.anagrafica.entities.Allenatore;
import it.macero.club_tracker.anagrafica.entities.Atleta;
import it.macero.club_tracker.anagrafica.entities.Persona;
import it.macero.club_tracker.anagrafica.grpc.AnagraficaProtobuf;
import it.macero.club_tracker.anagrafica.grpc.CATEGORIA;
import it.macero.club_tracker.anagrafica.grpc.Data;
import it.macero.club_tracker.anagrafica.grpc.RuoloAllenatore;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.time.LocalDate;
import java.util.List;

@Mapper(componentModel = "cdi",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AnagraficaMapper {
    @Mapping(target = "categorieAtleta", source = "atleta.categorie")
    @Mapping(target = "categorieAllenatore", source = "allenatore.categorie")
    Anagrafica toAnagrafica(Persona p);

    @Mapping(target = "atleta", source = ".", qualifiedByName = "toAtleta")
    @Mapping(target = "allenatore", source = ".", qualifiedByName = "toAllenatore")
    Persona toPersona(Anagrafica a);

    @Named("toAtleta")
    default Atleta toAtleta(Anagrafica a) {
        if (a.categorieAtleta() == null)
            return null;
        return new Atleta(a.categorieAtleta());
    }

    @Named("toAllenatore")
    default Allenatore toAllenatore(Anagrafica a) {
        if (a.categorieAllenatore() == null)
            return null;
        return new Allenatore(a.categorieAllenatore());
    }

    //TODO: capire se si possono importare le classi java in proto o viceversa
    default AnagraficaProtobuf toAnagraficaProtobuf(Anagrafica anagrafica) {
        if (anagrafica == null) return null;
        AnagraficaProtobuf.Builder builder = AnagraficaProtobuf.newBuilder();
        builder.setCodFiscale(anagrafica.codFiscale())
                .setNome(anagrafica.nome())
                .setCognome(anagrafica.cognome())
                .setDataNascita(this.toDataProtobuf(anagrafica.dataNascita()))
                .setEmail(anagrafica.email())
                .setTelefono(anagrafica.telefono())
                .addAllRuoliAllenatore(this.toListOfRuoloAllenatore(anagrafica))
                .addAllCategorieAtleta(this.toListOfCategorieAtleta(anagrafica));
        return builder.build();
    }

    default Data toDataProtobuf(LocalDate localDate) {
        if (localDate == null) return null;
        return Data.newBuilder()
                .setAnno(localDate.getYear())
                .setMese(localDate.getMonthValue())
                .setGiorno(localDate.getDayOfMonth())
                .build();
    }

    default List<RuoloAllenatore> toListOfRuoloAllenatore(Anagrafica anagrafica) {
        if (anagrafica == null || anagrafica.categorieAllenatore() == null) return null;
        return anagrafica.categorieAllenatore().stream().map(this::toRuoloAllenatore).toList();
    }

    default RuoloAllenatore toRuoloAllenatore(it.macero.club_tracker.anagrafica.entities.RuoloAllenatore ruolo) {
        if (ruolo == null) return null;
        return RuoloAllenatore.newBuilder()
                .setCategoria(CATEGORIA.valueOf(ruolo.getCategoria().name()))
                .setRuolo(ruolo.getRuolo())
                .build();
    }

    default List<CATEGORIA> toListOfCategorieAtleta(Anagrafica anagrafica) {
        if (anagrafica == null || anagrafica.categorieAtleta() == null) return null;
        return anagrafica.categorieAtleta().stream().map(
                c -> CATEGORIA.valueOf(c.name())
        ).toList();
    }

    default Anagrafica toAnagrafica(AnagraficaProtobuf request) {
        if (request == null) return null;
        return new Anagrafica(
                request.getCodFiscale(),
                request.getNome(),
                request.getCognome(),
                fromDataProtobuf(request.getDataNascita()),
                request.getEmail(),
                request.getTelefono(),
                fromListOfRuoloAllenatore(request.getRuoliAllenatoreList()),
                fromListOfCategorieAtleta(request.getCategorieAtletaList())
        );
    }

    default LocalDate fromDataProtobuf(Data data) {
        if (data == null) return null;
        return LocalDate.of(data.getAnno(), data.getMese(), data.getGiorno());
    }

    default List<it.macero.club_tracker.anagrafica.entities.RuoloAllenatore> fromListOfRuoloAllenatore(List<RuoloAllenatore> ruoli) {
        if (ruoli == null) return null;
        return ruoli.stream().map(this::fromRuoloAllenatore).toList();
    }

    default it.macero.club_tracker.anagrafica.entities.RuoloAllenatore fromRuoloAllenatore(RuoloAllenatore ruolo) {
        if (ruolo == null) return null;
        return new it.macero.club_tracker.anagrafica.entities.RuoloAllenatore(
                it.macero.club_tracker.anagrafica.entities.CATEGORIA.valueOf(ruolo.getCategoria().name()),
                ruolo.getRuolo()
        );
    }

    default List<it.macero.club_tracker.anagrafica.entities.CATEGORIA> fromListOfCategorieAtleta(List<CATEGORIA> categorie) {
        if (categorie == null) return null;
        return categorie.stream().map(
                c -> it.macero.club_tracker.anagrafica.entities.CATEGORIA.valueOf(c.name())
        ).toList();
    }
}