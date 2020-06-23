package com.grpc.grpcclient.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author thusitha
 *
 */
@Configuration
@PropertySource(value = { "classpath:application.properties" })
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
