FROM armdocker.rnd.ericsson.se/dockerhub-ericsson-remote/python:3.8-slim-buster

WORKDIR /usr/src/app

ADD auto_backup.py .

COPY requirements.txt requirements.txt

RUN pip install -r requirements.txt

CMD ["python", "./auto_backup.py"]
