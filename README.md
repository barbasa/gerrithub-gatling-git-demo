### How to run the tests

Git load test loading data from feed:
```
rm -fr /tmp/gatling-test-data;sbt 'gatling:testOnly *.CloneFromFeeder'
```

Git load test using DSL:
```
rm -fr /tmp/gatling-test-data;sbt 'gatling:testOnly *.CloneAndFetchWithDSL'
```

Functional test examople (WIP workflow):
```
sbt 'gatling:testOnly *.WIP'
```