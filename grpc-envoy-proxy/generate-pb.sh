#!/usr/bin/env bash

echo '++++++++++++++++++++++++++++++++++START generate-pb.sh+++++++++++++++++++++++++++++'

# generate the aeropay_service_definition.pb file that we can pass to envoy so that knows the grpc service
# we want to expose

# create proto directory
##mkdir -p proto


#copy the proto files to current location
##git archive --remote=ssh://git@github.com/idthusitha/grpc-test.git --prefix=proto/ HEAD:grpc-server/src/main/proto |  tar xvf -
##git archive --remote=ssh://git@bitbucket.org/accelaero/aero-pay-mono-repo.git --prefix=proto/ HEAD:api/aero-pay-api-definition/src/main/proto |  tar xvf -

# Switch to proto directory
cd proto
#generate pb
protoc -I. -I../build/extracted-include-protos/main --include_imports \
                --include_source_info \
                --descriptor_set_out=../build/service_definition.pb \
                *.proto

#protoc -I. -Ibuild/extracted-include-protos/main --include_imports \
#               --include_source_info \
#               --descriptor_set_out=reservation_service_definition.pb \
#               src/main/proto/reservation_service.proto


#clean the temp proto files
cd ..
##rm -rf proto


echo '++++++++++++++++++++++++++++++++++END generate-pb.sh+++++++++++++++++++++++++++++'