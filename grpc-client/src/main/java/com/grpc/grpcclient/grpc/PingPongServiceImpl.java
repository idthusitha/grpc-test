package com.grpc.grpcclient.grpc;

import org.springframework.stereotype.Service;

import com.grpcserver.grpc.HelloRequest;
import com.grpcserver.grpc.HelloResponse;
import com.grpcserver.grpc.HelloServiceGrpc.HelloServiceImplBase;
import com.grpcserver.grpc.PingPongServiceGrpc.PingPongServiceImplBase;
import com.grpcserver.grpc.PingRequest;
import com.grpcserver.grpc.PongResponse;

import io.grpc.stub.StreamObserver;

@Service
public class PingPongServiceImpl extends PingPongServiceImplBase {

	@Override
	public void ping(PingRequest request, StreamObserver<PongResponse> responseObserver) {

		String greeting = new StringBuilder().append("Hello, ").append(request.getPing()).append(" ").toString();

		PongResponse response = PongResponse.newBuilder().setPong(greeting).build();

		responseObserver.onNext(response);
		responseObserver.onCompleted();
	}

}