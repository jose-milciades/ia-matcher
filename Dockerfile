FROM openjdk:9

RUN mkdir /opt/appi-match
RUN mkdir /opt/appi-match/config


COPY ner-0.0.1-SNAPSHOT.jar /opt/appi-match
COPY parametros_entidad.json /opt/appi-match/config

EXPOSE 9090
ENTRYPOINT ["java", "-jar", "/opt/appi-match/ner-0.0.1-SNAPSHOT.jar"]
