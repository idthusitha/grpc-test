# grpc-test
gRPC is a modern open source high performance RPC framework that can run in any environment. It can efficiently connect services in and across data centers with pluggable support for load balancing, tracing, health checking and authentication. It is also applicable in last mile of distributed computing to connect devices, mobile applications and browsers to backend services.

More Info: https://grpc.io/

### Clone repository from github
	git clone https://github.com/idthusitha/grpc-test.git

### Build grpc-server jar	
	cd {{WORKSPACE}}/grpc-test/grpc-server
	gradle clean build
	cp {{WORKSPACE}}/grpc-test/grpc-server/build/libs/grpc-server-1.0.0.jar  {{WORKSPACE}}/grpc-test/grpc-client/lib


### Build grpc-envoy-proxy Service
	cd {{WORKSPACE}}/grpc-test/grpc-envoy-proxy
	gradle clean build
	sudo docker image rm --force grpctest/grpc-envoy-proxy
	sudo docker build -t  grpctest/grpc-envoy-proxy .
	sudo docker-compose up	
	
	
	POST Request
	http://localhost:51051/grpcserver.RequestDemo/details?reqParam1=aaaaaaaaaaaa&reqParam2= qqqqqqqqqqqqqq
	
More Info:- https://blog.jdriven.com/2018/11/transcoding-grpc-to-http-json-using-envoy/


### Build and Run application sprint boot, bootRun
	
	cd {{WORKSPACE}}/grpc-test/grpc-client
	gradle clean build bootRun
	
	gradle clean build bootRun --debug-jvm
	Listening for transport dt_socket at address: 5005
	
	sudo su
	gradle clean build buildDocker
	sudo docker-compose up
	sudo docker-compose rm	
	
	sudo docker-compose -f docker-compose-debug.yml up
	Debug port is 5005


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


### REST request

	POST
	http://localhost:8080/hello