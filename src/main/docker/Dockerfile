FROM anapsix/alpine-java:8_jdk_unlimited

ARG JAVA_OPTS
ARG PORT

ADD sgr.jar /

EXPOSE $PORT

CMD java $JAVA_OPTS -jar /sgr.jar --server.port=$PORT --storage.path=$STORAGE_PATH --spring.datasource.url=$DATASOURCE_URL --flyway.url=$DATASOURCE_URL
