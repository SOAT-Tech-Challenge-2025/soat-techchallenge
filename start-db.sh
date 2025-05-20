#!/bin/sh

docker run -d -p 5432:5432 \
  --name soat-techchallenge-postgres \
  --restart always \
  -v soat-techchallenge-postgres-data:/var/lib/postgresql/data \
  soat-techchallenge-postgres:13

