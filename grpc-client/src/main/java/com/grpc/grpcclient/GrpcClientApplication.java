package com.grpc.grpcclient;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.grpc.grpcclient.util.GrpcUtil;

@SpringBootApplication
public class GrpcClientApplication {

	public static void main(String[] args) throws IOException {
		ApplicationContext context = SpringApplication.run(GrpcClientApplication.class, args);
		GrpcUtil.startGrpcServer(context);
	}

}
