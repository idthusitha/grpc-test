#!/bin/sh
set -e

echo "Generating envoy.yaml config file..."
cp -rf /tmpl/envoy.yaml.tmpl /etc/envoy/envoy.yaml
sed -i "s/GRPC_HOST/$GRPC_HOST/g" /etc/envoy/envoy.yaml
sed -i "s/GRPC_PORT/$GRPC_PORT/g" /etc/envoy/envoy.yaml

echo "Starting Envoy..."
/usr/local/bin/envoy -c /etc/envoy/envoy.yaml