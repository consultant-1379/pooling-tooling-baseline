FROM armdocker.rnd.ericsson.se/dockerhub-ericsson-remote/python:3.8-slim-buster

WORKDIR /usr/src/app

ADD enforce_backup_retention_policy.py .

COPY requirements.txt requirements.txt

RUN pip install -r requirements.txt

CMD ["python", "./enforce_backup_retention_policy.py"]
