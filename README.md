# grpc-test


### Build project

	cd {{WORKSPACE}}/grpc-test/grpc-client
	gradle clean build bootRun


### GRPC Call using BloomRPC

	{
		"firstName": "Hello",
		"lastName": "Hello"
	}
	
	http://localhost:9090




### REST request

	POST
	http://localhost:8080/hello