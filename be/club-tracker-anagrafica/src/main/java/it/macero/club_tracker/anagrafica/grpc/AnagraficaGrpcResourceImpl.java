package it.macero.club_tracker.anagrafica.grpc;

import com.google.protobuf.BoolValue;
import com.google.protobuf.Empty;
import com.google.protobuf.StringValue;
import io.quarkus.grpc.GrpcService;
import io.smallrye.mutiny.Uni;
import it.macero.club_tracker.anagrafica.mapper.AnagraficaMapper;
import it.macero.club_tracker.anagrafica.service.AnagraficaService;
import jakarta.inject.Inject;

@GrpcService
public class AnagraficaGrpcResourceImpl implements AnagraficaGrpcResource {
    @Inject
    AnagraficaService service;
    @Inject
    AnagraficaMapper mapper;

    @Override
    public Uni<ListOfAnagrafica> getAll(Empty request) {
        return service.getAll().map(l -> ListOfAnagrafica.newBuilder().addAllListOfAnagrafica(
                l.stream().map(mapper::toAnagraficaProtobuf).toList()
        ).build());
    }

    @Override
    public Uni<AnagraficaProtobuf> getById(StringValue request) {
        String codFiscale = request.getValue();
        return service.getById(codFiscale).map(mapper::toAnagraficaProtobuf);
    }

    @Override
    public Uni<AnagraficaProtobuf> insert(AnagraficaProtobuf request) {
        return service.insert(mapper.toAnagrafica(request)).map(mapper::toAnagraficaProtobuf);
    }

    @Override
    public Uni<BoolValue> delete(StringValue request) {
        String codFiscale = request.getValue();
        return service.delete(codFiscale).map(b -> BoolValue.newBuilder().setValue(b).build());
    }
}