package com.grpc.grpcclient.grpc;

import org.springframework.stereotype.Service;

import com.grpcserver.grpc.DemoRequest;
import com.grpcserver.grpc.DemoResponse;
import com.grpcserver.grpc.RequestDemoServiceGrpc.RequestDemoServiceImplBase;

import io.grpc.stub.StreamObserver;


@Service
public class RequestDemoServiceImpl extends RequestDemoServiceImplBase {

	@Override
	public void getRequestDetails(DemoRequest request, StreamObserver<DemoResponse> responseObserver) {

		StringBuilder str = new StringBuilder();
		
		str.append("Request Parameter: ");
		str.append(request.getReqParam1());
		str.append(request.getReqParam2());
		str.append(" ").toString();

		DemoResponse response = DemoResponse.newBuilder().setResParam1(str.toString()).build();

		responseObserver.onNext(response);
		responseObserver.onCompleted();
	}

}