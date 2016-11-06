FROM tomcat:8-jre8

ADD ./settings.xml /usr/local/tomcat/conf/
ADD ./tomcat-users.xml /usr/local/tomcat/conf/
ADD ./server.xml /usr/local/tomcat/conf/
ADD ./softwarelabsit-bank-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/bank.war

ENV JAVA_OPTS="${JAVA_OPTS}-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8000"


CMD ["catalina.sh", "run"]
