#FROM tomcat:9.0-jdk8-openjdk
#ADD ./MWGApi/target/BHXAPI.war /usr/local/tomcat/webapps

#EXPOSE 8080

#CMD ["catalina.sh", "run"]

FROM tomcat:9.0-jdk8-openjdk
COPY ./MWGApi/target/BHXAPI.war /usr/local/tomcat/webapps
COPY ./prometheus-jmx-config.yaml /opt/jmx_exporter/prometheus-jmx-config.yaml
COPY ./jmx_prometheus_javaagent-0.15.0.jar /opt/jmx_exporter/jmx_prometheus_javaagent-0.15.0.jar
COPY ./setenv.sh /usr/local/tomcat/bin
WORKDIR /opt/jmx_exporter
RUN ls

RUN chmod  o+x /usr/local/tomcat/bin/setenv.sh

EXPOSE 8080
EXPOSE 8084

CMD ["catalina.sh", "run"]
