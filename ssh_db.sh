#! /bin/bash


docker stop postgresql
docker container rm postgresql
docker run -itd -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -p 5432:5432 -v postgres_data:/var/lib/postgresql/data --name postgresql postgres

sleep 2
exec_cmd="docker exec -it postgresql psql -h localhost -U postgres"
eval $exec_cmd