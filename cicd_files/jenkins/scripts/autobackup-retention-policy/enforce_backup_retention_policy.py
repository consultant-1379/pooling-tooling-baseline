import logging
import os
import requests
from requests.auth import HTTPBasicAuth
from bs4 import BeautifulSoup
from datetime import datetime

logging.basicConfig(level=logging.INFO)

RETENTION_POLICY_DAYS = 30

DAILY_DUMPS_URL = 'https://arm.seli.gic.ericsson.se/artifactory/proj-eric-oss-dev-test-generic-local/rpt-backups' \
                  '/mongo/dumps/ '


def enforce_retention_policy():
    logging.info(f'Retention policy set to {RETENTION_POLICY_DAYS} days')
    logging.info('Obtaining and parsing HTML data from artifactory')
    http_user = os.getenv('FUNCTIONAL_USER_USERNAME')
    http_password = os.getenv('FUNCTIONAL_USER_PASSWORD')
    response = requests.get(
        DAILY_DUMPS_URL,
        auth=HTTPBasicAuth(http_user, http_password))
    if response.status_code != 200:
        logging.error('Invalid response code from artifactory when retrieving data')
        exit(1)
    soup = BeautifulSoup(response.text, 'html.parser')

    daily_dump_directories = []
    for link in soup.find_all('a'):
        link_text = str(link.contents[0])
        if link_text == '../':
            continue
        daily_dump_directories.append(link_text.strip('/'))

    for daily_dump_directory in daily_dump_directories:
        date_object = datetime.strptime(daily_dump_directory, '%Y-%m-%d')
        age_of_directory = datetime.now() - date_object
        if age_of_directory.days > RETENTION_POLICY_DAYS:
            logging.info(
                f'Deleting dump directory {daily_dump_directory} as is older than {RETENTION_POLICY_DAYS} days')
            response = requests.delete(
                f'{DAILY_DUMPS_URL}{daily_dump_directory}/',
                auth=HTTPBasicAuth(http_user, http_password))
            if response.status_code != 204:
                logging.error(f'Error while deleting daily dump directory {daily_dump_directory}')
                exit(1)

    logging.info('Retention policy successfully enforced')


if __name__ == "__main__":
    enforce_retention_policy()
