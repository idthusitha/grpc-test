package com.grpc.grpcclient.impl;

import org.springframework.stereotype.Service;

import com.grpcserver.grpc.HelloRequest;
import com.grpcserver.grpc.HelloResponse;
import com.grpcserver.grpc.HelloServiceGrpc;
import com.grpcserver.grpc.PingPongServiceGrpc;
import com.grpcserver.grpc.PingRequest;
import com.grpcserver.grpc.PongResponse;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

@Service
public class GRPCClientService {
	public String ping() {
		ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090).usePlaintext().build();

		PingPongServiceGrpc.PingPongServiceBlockingStub stub = PingPongServiceGrpc.newBlockingStub(channel);

		PongResponse helloResponse = stub.ping(PingRequest.newBuilder().setPing("").build());

		channel.shutdown();

		return helloResponse.getPong();
	}

	public String helloo() {
		ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090).usePlaintext().build();

		HelloServiceGrpc.HelloServiceBlockingStub stub = HelloServiceGrpc.newBlockingStub(channel);

		HelloResponse helloResponse = stub.hello(HelloRequest.newBuilder().setFirstName("Baeldung").setLastName("gRPC").build());

		String response = helloResponse.getGreeting();

		channel.shutdown();
		
		return response;
	}
}