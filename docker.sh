#!/bin/bash

echo "Building ui docker image..." &&
cd ui &&
docker build . -t rb/crawler-ui &&
echo "Built ui docker image..." &&

echo "Building api docker image..." &&
cd ../api &&
docker build . -t rb/crawler-api &&
echo "Built api docker image..." &&


cd ../ &&
echo "Running docker-compose up..." &&
docker-compose up -d