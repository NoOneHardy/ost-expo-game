FROM mysql:8.0

ENV MYSQL_ROOT_PASSWORD=root
ENV MYSQL_DATABASE=ost_expo_game

COPY init.sql /docker-entrypoint-initdb.d/

EXPOSE 3306
