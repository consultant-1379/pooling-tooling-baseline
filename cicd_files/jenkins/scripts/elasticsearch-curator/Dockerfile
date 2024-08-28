FROM armdocker.rnd.ericsson.se/dockerhub-ericsson-remote/python:3.8-slim-buster

WORKDIR /usr/src/app

COPY . /usr/src/app

ADD cicd_files/jenkins/scripts/elasticsearch-curator/action.yaml .

ADD cicd_files/jenkins/scripts/elasticsearch-curator/curator.yaml .

RUN pip install -U elasticsearch-curator==5.8.4

CMD ["curator", "--config", "./curator.yaml", "./action.yaml"]
