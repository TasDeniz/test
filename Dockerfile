FROM tomcat:9.0.30-jdk11-openjdk

RUN ["rm", "-fr", "/usr/local/tomcat/webapps"]

ADD src/docker/context.xml                      /usr/local/tomcat/conf/Catalina/localhost/ROOT.xml
ADD src/docker/log4j2.xml                       /usr/local/tomcat/conf/

COPY ./target/Order.war /usr/local/tomcat/webapps/ROOT.war
ADD src/docker/start.sh                         /iws/
RUN chmod +x                                    /iws/start.sh
CMD ["/iws/start.sh"]