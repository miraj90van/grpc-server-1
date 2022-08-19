package com.code.miraz.services;

import com.code.miraz.grpc.MahasiswaRequest;
import com.code.miraz.grpc.MahasiswaResponse;
import com.code.miraz.grpc.MahasiswaServiceGrpc.MahasiswaServiceImplBase;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class MahasiswaServiceImpl extends MahasiswaServiceImplBase {

  @Override
  public void getMahasiswa(MahasiswaRequest request,
      StreamObserver<MahasiswaResponse> responseObserver) {

    String nim = request.getNim();
    String name = request.getName();

    System.out.println("Received Message: " + nim);
    System.out.println("Received Message: " + name);

    MahasiswaResponse mahasiswaResponse = MahasiswaResponse.newBuilder()
        .setNim("Received your nim: " + nim)
        .setName("Received your nim: " + name)
        .build();

    responseObserver.onNext(mahasiswaResponse);
    responseObserver.onCompleted();
  }


}
