```shell
ip addr | grep eth0
```

Build the project into a jar:
```shell
gradle quarkusBuild --uber-jar
```

Start the service with jmx support
```shell
java  -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=9050 -Dcom.sun.management.jmxremote.local.only=false -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false -Djava.rmi.server.hostname=192.168.150.129 -jar build/quarkus-fake-db-1.0.0-SNAPSHOT-runner.jar
```
or via Docker

either use xwindow to start the visual vm
or find out the ip used by the system
```shell
ip addr | grep eth0
```


