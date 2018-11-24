# assignment


## Specification

Design and implement a simple chat API server meeting the following requirements:
A user posts a message to a given chat topic.
If the topic does not exist, create it and add the message to it.
A user can see the last 10 messages of a topic that he will specify
A user can subscribe to a topic and receive in real-time new messages added to this topic
Every message posted by a user is scored using a provided external library com.ea.chat.score.jar (
this jar is provided by us and can be downloaded from https://s3-us-west-2.amazonaws.com/area52-misc/com.ea.chat.scorer.jar)
A user can see a ranking of top 10 users with the highest score 

## Technical requirements
The application must provide the required services over HTTP/TCP/Websockets
The response can be JSON, XML…
The application must be thread safe 
The application should be implemented in Java and/or Scala
Details are held in memory while the application is running and do not need to be persisted to a file or database
(Optional) A sample front end to demonstrate and test the application can be provided
Assessment




This was a fun project!!

I have included the folowing as part of the project.

-- A very rudamentary shell script called "run_me_to_seed.sh" that can be used to seed some data.
It expects a local server to be running at port 8080

-- As the script creates users, topics, messages etc and subscribes users to topics you might notice text files
with seemingly random names being generated. Due to shortage of time this is how I am delivering newly added
messages to users that have subscribed to a given topic. Sorry about the mess.

-- I'll include the built jar in the target directory so that you can simply run
java -jar target/assignment-0.0.1-SNAPSHOT.jar ( assumes java to be present of course )

-- Due to shortage of time I was only able to write some perfomance tests - see UserRepositoryIntegrationTest for instance

-- I also wrote a very simple performance test to test "Topic" domain object and its use of CopyOnWriteArrayList.
The test is TopicIntegrationTest.java.
Observation was that system degrades significantly after about 3K concurrent R/W. The R/W time after this point increases
in a graph that looks quadratic. So if the expectation is to see more than 3K concurrent R/W a different mechanism should
be used to ensure thread safty of the domain object.

-- The code has a decent test coverage see image included 'unit_test_coverge.png' 




## Overall Design.

### Dao layer - All repositories use a form of concurrent collection to avoid active syncing of access to shared
mutable collections
### Domain objects
### Service layer - standard service layer stuff
### Data transfer objects - Conversation with outside the application is done via DTOs.
### Notification subsystem - Simple observable/observer pattern implemented




