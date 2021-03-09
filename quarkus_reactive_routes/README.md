docker run --name some-postgres -e POSTGRES_PASSWORD=password -e POSTGRES_DB=persondb -d -p5432:5432 postgres

gradle quarkusBuild --uber-jar

java -jar build/quarkus-demo-1.0.0-SNAPSHOT-runner.jar

gradle quarkusDev

curl -H "Content-Type: application/json; charset=UTF-8" --request POST --data @./payload/person3.json http://localhost:8080/person/

curl http://localhost:8080/person

curl http://localhost:8080/person/2

curl http://localhost:8080/person/firstname/Max
