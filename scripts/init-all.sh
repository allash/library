#!/usr/bin/env bash

set -e

# cd to project root
cd "$(dirname "$0")/.."

cd docker
docker-compose down
docker-compose pull --ignore-pull-failures --parallel
docker-compose up -d --build