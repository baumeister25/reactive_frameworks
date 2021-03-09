Start the service using docker-compose
```
docker-compose up
```

Run queries against the RestEasy component and against the eventloop endpoint, which are both implmented blocking. See the difference ;-)

```
blockingRequests.sh
```

```
blockingVertxRequests.sh
```


## Alternatives in reactive environment
The service implements two alternatives when executing blocking code in reactive environments.
Using the Vert.X eventbus or executing the blocking code on different executors explicitly.

Both ways are shown here as well.

