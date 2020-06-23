package com.grpc.grpcclient.util;


import com.grpc.grpcclient.config.AppConfig;
import com.grpc.grpcclient.grpc.HelloServiceImpl;
import com.grpc.grpcclient.grpc.PingPongServiceImpl;
import com.grpc.grpcclient.grpc.RequestDemoServiceImpl;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.io.IOException;

public class GrpcUtil {

    private GrpcUtil() {
    }

    private static Logger logger = LoggerFactory.getLogger(GrpcUtil.class);

    public static void startGrpcServer(ApplicationContext context) throws IOException {

        AppConfig appConfig = context.getBean(AppConfig.class);

        Server server = ServerBuilder.forPort(appConfig.getGrpcPort())
                                     .addService(context.getBean(HelloServiceImpl.class))                                     
                                     .addService(context.getBean(PingPongServiceImpl.class))
                                     .addService(context.getBean(RequestDemoServiceImpl.class))
                                     .build()
                                     .start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> stopGrpcServer(server)));

        logger.info("Server started listening on : {} ", server.getPort());
    }

    public static void stopGrpcServer(Server server) {
        if (server != null) {
            logger.info("Shutting down gRPC server since JVM is shutting down");
            server.shutdown();
            logger.info("gRPC has server shut down properly");
        }
    }
}