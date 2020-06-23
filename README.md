# grpc-test
gRPC is a modern open source high performance RPC framework that can run in any environment. It can efficiently connect services in and across data centers with pluggable support for load balancing, tracing, health checking and authentication. It is also applicable in last mile of distributed computing to connect devices, mobile applications and browsers to backend services.

More Info: https://grpc.io/


### Build and Run application without tomcat 

	git clone https://github.com/idthusitha/grpc-test.git
	cd {{WORKSPACE}}/grpc-test/grpc-client
	gradle clean build bootRun
	
	gradle clean build bootRun --debug-jvm
	Listening for transport dt_socket at address: 5005
	


### GRPC client install
	https://awesomeopensource.com/project/uw-labs/bloomrpc
	https://github.com/uw-labs/bloomrpc/releases/download/1.4.1/bloomrpc_1.4.1_amd64.deb


### GRPC Call using BloomRPC
Select the proto file

	{
		"firstName": "Hello",
		"lastName": "Hello"
	}
	
	http://localhost:9090


### GRPC envoy Proxy service
	cd {{WORKSPACE}}/grpc-test/grpc-envoy-proxy
	gradle clean build
	sudo docker image rm --force grpctest/grpc-envoy-proxy
	sudo docker build -t  grpctest/grpc-envoy-proxy .
	sudo docker-compose up	
	
	
	http://localhost:51051/grpcserver.RequestDemo/details?reqParam1=aaaaaaaaaaaa&reqParam2= qqqqqqqqqqqqqq
	
	https://blog.jdriven.com/2018/11/transcoding-grpc-to-http-json-using-envoy/



### REST request

	POST
	http://localhost:8080/hello