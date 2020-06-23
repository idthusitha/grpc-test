FROM envoyproxy/envoy:v1.14.1

COPY config/envoy-config.yml /tmpl/envoy.yaml.tmpl
COPY docker-entrypoint.sh /
COPY build/service_definition.pb /data/service_definition.pb

RUN chmod 500 /docker-entrypoint.sh

ENTRYPOINT ["/docker-entrypoint.sh"]
