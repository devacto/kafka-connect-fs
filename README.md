# Kafka Connect FileSystem Connector
[![Build Status](https://travis-ci.org/mmolimar/kafka-connect-fs.svg?branch=master)](https://travis-ci.org/mmolimar/kafka-connect-fs)
[![Coverage Status](https://coveralls.io/repos/github/mmolimar/kafka-connect-fs/badge.svg?branch=master)](https://coveralls.io/github/mmolimar/kafka-connect-fs?branch=master)

## This Fork

This fork adds a new reader called FilenameReader. FilenameReader will only put the name of the file inside the Kafka
topic.

## Debugging the connector

`KAFKA_DEBUG=y DEBUG_SUSPEND_FLAG=y CLASSPATH="$(find /Users/victor/dev/work/sia/kafka-experiment/kafka-connect-fs/target/ -type f -name '*.jar'| grep '\-package' | tr '\n' ':')" /Users/victor/dev/work/sia/confluent-5.0.0/bin/connect-standalone worker.properties mmoliar-source-config.properties`

## Kafka Connect FileSystem Connector

**kafka-connect-fs** is a [Kafka Connector](http://kafka.apache.org/documentation.html#connect) 
for reading records from files in the file systems specified and load them into Kafka.

Documentation for this connector can be found [here](http://kafka-connect-fs.readthedocs.io/).

## Development

To build a development version you'll need a recent version of Kafka. You can build
kafka-connect-fs with Maven using the standard lifecycle phases.

## FAQ

Some frequently asked questions on Kafka Connect FileSystem Connector can be found here -
http://kafka-connect-fs.readthedocs.io/en/latest/faq.html

## Contribute

- Source Code: https://github.com/mmolimar/kafka-connect-fs
- Issue Tracker: https://github.com/mmolimar/kafka-connect-fs/issues

## License

Released under the Apache License, version 2.0.
