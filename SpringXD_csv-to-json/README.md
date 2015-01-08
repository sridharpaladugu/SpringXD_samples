The processor module which can parse the incoming payload of CSV to JSON.
Sample usage:

stream create --name testscv-to-json --definition "file --dir=/home/gpadmin/data/csv/ --outputType=text/plain --fixedDelay=10  | csv-to-json | file --dir=/home/gpadmin/data/json/ --suffix=json"
