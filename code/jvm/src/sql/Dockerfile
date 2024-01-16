FROM postgres:13.1-alpine

WORKDIR /app
COPY ./init/* /docker-entrypoint-initdb.d/

USER postgres
EXPOSE 5432