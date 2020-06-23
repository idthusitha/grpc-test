package com.grpc.grpcclient.grpc;

import org.springframework.stereotype.Service;

import com.grpcserver.grpc.HelloRequest;
import com.grpcserver.grpc.HelloResponse;
import com.grpcserver.grpc.HelloServiceGrpc.HelloServiceImplBase;

import io.grpc.stub.StreamObserver;

@Service
public class HelloServiceImpl extends HelloServiceImplBase {
	@Override
	public void hello(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {

		String greeting = new StringBuilder().append("Hello, ").append(request.getFirstName()).append(" ")
				.append(request.getLastName()).toString();

		HelloResponse response = HelloResponse.newBuilder().setGreeting(greeting).build();

		responseObserver.onNext(response);
		responseObserver.onCompleted();
	}

}