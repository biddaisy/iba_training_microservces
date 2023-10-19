# Registration web

docker build -t registration-web -f Dockerfile .

docker run --name registration-web -it --rm --env-file=registration-common/src/main/resources/.env -p 8080:9080 -p 8443:9443 registration-web