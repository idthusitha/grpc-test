
1) The address of the admin interface. You can also get prometheus metrics here to see how the service performs!!

2) The address at which the HTTP API will be available

3)	by including an empty grpc: {} object, you tell the transcoder to only forward calls to the gRPC backend. Paths unknown to the gRPC service return 404. The name of the backend services to route requests to. Step (7) defines this name.

4)	The path to the .pb descriptor file we generated before

5)	The services to transcode

6)	Protobuf field names usually contain underscores. Setting this field to false will translate field names to camelcase.

7)	A cluster defines upstream services (services that envoy can proxy to in step )

8)	The address and port at which the backend services are reachable. I’ve used (127.0.0.1/localhost).