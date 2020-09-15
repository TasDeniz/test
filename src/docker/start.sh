#!/usr/bin/env bash

set -e

sed -i "s/{postgres-host}/${POSTGRES_HOST}/"              /usr/local/tomcat/conf/Catalina/localhost/ROOT.xml
sed -i "s/{postgres-port}/${POSTGRES_PORT:-5432}/"        /usr/local/tomcat/conf/Catalina/localhost/ROOT.xml
sed -i "s/{postgres-db}/${POSTGRES_DB}/"                  /usr/local/tomcat/conf/Catalina/localhost/ROOT.xml
sed -i "s/{postgres-user}/${POSTGRES_USER}/"              /usr/local/tomcat/conf/Catalina/localhost/ROOT.xml
sed -i "s/{postgres-pass}/${POSTGRES_PASS}/"              /usr/local/tomcat/conf/Catalina/localhost/ROOT.xml

# Replace start.sh with catalina.sh
exec /usr/local/tomcat/bin/catalina.sh run