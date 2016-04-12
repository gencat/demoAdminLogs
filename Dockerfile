FROM cscanigo/tomcat7-java7
MAINTAINER oficina-tecnica.canigo.ctti@gencat.cat

#COPY target/demoAdminLogs /var/lib/tomcat7/webapps/ROOT
COPY target/demoAdminLogs /opt/tomcat/webapps/ROOT