FROM tomcat:9.0.34-jdk8

EXPOSE 8080

ARG artifact_name=my-webapp

COPY target/${artifact_name}.war /usr/local/tomcat/webapps/

CMD ["catalina.sh", "run"]