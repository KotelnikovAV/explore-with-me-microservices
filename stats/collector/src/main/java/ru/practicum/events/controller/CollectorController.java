package ru.practicum.events.controller;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import ru.practicum.events.producer.EventsProducer;
import ru.practicum.ewm.stats.proto.UserActionControllerGrpc;
import ru.practicum.ewm.stats.proto.UserActionProto;

@GrpcService
@Slf4j
@RequiredArgsConstructor
public class CollectorController extends UserActionControllerGrpc.UserActionControllerImplBase {
    private final EventsProducer eventsProducer;

    @Override
    public void collectUserAction(UserActionProto userActionProto, StreamObserver<Empty> responseObserver) {
        log.info("Collect user action");

        eventsProducer.collectUserAction(userActionProto);

        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }
}
