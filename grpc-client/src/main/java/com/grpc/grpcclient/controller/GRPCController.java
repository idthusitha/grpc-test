package com.grpc.grpcclient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grpc.grpcclient.impl.GRPCClientService;

@RestController
public class GRPCController {
	GRPCClientService grpcClientService;

	@Autowired
	public GRPCController(GRPCClientService grpcClientService) {
		this.grpcClientService = grpcClientService;
	}

	@GetMapping("/ping")
	public String ping() {
		return grpcClientService.ping();
	}

	@RequestMapping("/hello")
	public String hello() {
		return grpcClientService.helloo();
	}
}
