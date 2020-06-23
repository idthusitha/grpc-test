package com.grpc.grpcclient.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
//import org.springframework.retry.annotation.EnableRetry;

import java.util.List;

/**
 * @author thusitha
 *
 */
@Configuration
@PropertySource(value = { "classpath:application.properties" })
// @EnableRetry
public class AppConfig {

	@Value("${grpc.server.port}")
	private int grpcPort;

	public int getGrpcPort() {
		return grpcPort;
	}

	public void setGrpcPort(int grpcPort) {
		this.grpcPort = grpcPort;
	}

}
