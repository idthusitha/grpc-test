package com.grpc.grpcclient.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.grpcserver.grpc.DemoRequest;
import com.grpcserver.grpc.DemoRequest.Builder;
import com.grpcserver.grpc.DemoResponse;
import com.grpcserver.grpc.HelloRequest;
import com.grpcserver.grpc.HelloResponse;
import com.grpcserver.grpc.HelloServiceGrpc;
import com.grpcserver.grpc.PingPongServiceGrpc;
import com.grpcserver.grpc.PingRequest;
import com.grpcserver.grpc.PongResponse;
import com.grpcserver.grpc.RequestDemoServiceGrpc;

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

	public String getRequestDetails(HttpServletRequest request) {
		ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090).usePlaintext().build();

		RequestDemoServiceGrpc.RequestDemoServiceBlockingStub stub = RequestDemoServiceGrpc.newBlockingStub(channel);

		Builder demoRequestBuilder = DemoRequest.newBuilder();
		demoRequestBuilder.setReqParam1(request.getParameter("reqParam1"));
		demoRequestBuilder.setReqParam2(request.getParameter("reqParam2"));

		DemoRequest demoRequest = demoRequestBuilder.build();
		DemoResponse demoResponse = stub.getRequestDetails(demoRequest);

		String response = demoResponse.getResParam1();

		channel.shutdown();

		return response;
	}
}