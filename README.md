# Reactive Frameworks

This repository demonstrates ways of reactive programming and the benefit using the reactive java framework Quarkus and the streaming libraries RxJava, Mutiny, Project Reactor.

## Quarkus Demo

A simple setup of a Quarkus project to show how it could be used.
 - Jackson RestEasy
 - Hibernate ORM with Panache
 - Quarkus Arc

## Quarkus Reactive Routes

Shows how one could execute code directly on the underlying vert.x eventloop using reactive routes.

This quakrus instance contains both one reactive route with a delay that returns a String after some time and one RestEasy Route that let's the thread sleep for the same amount of time. You can see that the RestEasy client blocks on a executor thread.

## reactive 

This project contains some example of the java streaming APIs 
- RxJava
- Project Reactor
- Mutiny

## quarkus Reactive Processing

This project contains three services. 
One fakeDB that offers two endpoints. One blocking endpoint that sleeps for 20 seconds before returning a string. One non-blocking reactive route that returns a stream with a delay.

One SprinBoot client that sends a message to the blocking endpoint. We can see that the number of threads increases when waiting for the fakeDB service to response, becasue the IO is done on executor threads

The Quarkus Reactive Service on the other hand can handle the same number of incoming requests without any increase, because it uses reactive non-blocking libraries and the reactor pattern of the underlying vert.x


