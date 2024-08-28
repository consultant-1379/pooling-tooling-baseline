import os
import tarfile
from pathlib import Path

import bson
import pymongo


def make_tarfile(output_filename: str, source_dir: str) -> None:
    with tarfile.open(output_filename, 'w:gz') as tar:
        tar.add(source_dir, arcname=os.path.basename(source_dir))


def dump(output_filepath: str) -> None:
    client = pymongo.MongoClient(host='rpt.ews.gic.ericsson.se', port=31517)
    database = client['resourcePoolingToolDB']
    collections = database.list_collection_names()
    for collection in collections:
        with open(os.path.join(output_filepath, f'{collection}.bson'), 'wb+') as output_file:
            for document in database[collection].find():
                output_file.write(bson.BSON.encode(document))


def main():
    Path('/usr/src/app/out/resourcePoolingToolDB').mkdir(exist_ok=True)
    dump('/usr/src/app/out/resourcePoolingToolDB')
    make_tarfile('/usr/src/app/out/dump-current.tar.gz', '/usr/src/app/out/resourcePoolingToolDB')


if __name__ == '__main__':
    main()
