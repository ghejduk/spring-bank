FROM tomcat:8-jre8

ADD ./settings.xml /usr/local/tomcat/conf/
ADD ./tomcat-users.xml /usr/local/tomcat/conf/
ADD ./server.xml /usr/local/tomcat/conf/
ADD ./softwarelabsit-bank-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/bank.war

CMD ["catalina.sh", "run"]