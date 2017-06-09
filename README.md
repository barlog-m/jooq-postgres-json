Example for using [jOOQ](https://www.jooq.org/doc/3.9/manual/code-generation/custom-data-type-bindings/) and [PostgreSQL JSONB](https://www.postgresql.org/docs/9.6/static/datatype-json.html)

```
java -jar build/libs/app.jar --spring.datasource.url=jdbc:postgresql://vm.local:5432/postgres
```

### Run without build jar (with reloadable resources)
```
gradle bootRun
```

### Run
```
gradle run
```

### Create executable jar with dependencies
```
gradle build
```

### Checkstyle and FindBugs
```
gradle check
```
