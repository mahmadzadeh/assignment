#!/usr/bin/env bash


#mvn package && mvn install

java -jar target/assignment-0.0.1-SNAPSHOT.jar&

sleep 10

echo -e '\n(1) Current topics in system\n'

curl http://localhost:8080/topics

echo -e '\n'

echo -e '\n(2) Create a user\n'

curl -d '{"name":"martin", "email":"email@email.com"}' -H "Content-Type: application/json" -X POST http://localhost:8080/users

echo -e '\n'

echo -e '\n(2) Create a second user\n'

curl -d '{"name":"joe", "email":"joe@email.com"}' -H "Content-Type: application/json" -X POST http://localhost:8080/users

echo -e '\n'

echo -e '\n(3) Create sports topic...\n'

curl -d '{"name":"sports"}' -H "Content-Type: application/json" -X POST http://localhost:8080/topics

echo -e '\n'

echo -e '\n(4) Create a family topic...\n'

curl -d '{"name":"family"}' -H "Content-Type: application/json" -X POST http://localhost:8080/topics

echo -e '\n'

echo -e '\n(5) Create comedy topic...\n'

curl -d '{"name":"comedy"}' -H "Content-Type: application/json" -X POST http://localhost:8080/topics

echo -e '\n'

echo -e '\n(6) Create movies topic...\n'

curl -d '{"name":"movies"}' -H "Content-Type: application/json" -X POST http://localhost:8080/topics

echo -e '\n'

echo -e '\n(7) Posting a message under sports topic as Martin user ...\n'

curl -d '{"message":"what is going on in sports", "userId":1 }' -H "Content-Type: application/json" -X POST http://localhost:8080/topics/sports/messages

echo -e '\n'

echo -e '\n(8) Posting a message under family topic as Martin user ...\n'

curl -d '{"message":"I like my family", "userId":1 }' -H "Content-Type: application/json" -X POST http://localhost:8080/topics/family/messages

echo -e '\n'

echo -e '\n(9) Posting a message under sports topic as second user ...\n'

curl -d '{"message":"Not much is happening in sports", "userId":2 }' -H "Content-Type: application/json" -X POST http://localhost:8080/topics/sports/messages

echo -e '\n'

echo -e '\n(10) Posting 10 message under sports topic as Martin user ...\n'

curl -d '{"message":"Not much is happening in sports", "userId":1 }' -H "Content-Type: application/json" -X POST http://localhost:8080/topics/sports/messages
curl -d '{"message":"Sports are good", "userId":1 }' -H "Content-Type: application/json" -X POST http://localhost:8080/topics/sports/messages
curl -d '{"message":"New thing", "userId":1 }' -H "Content-Type: application/json" -X POST http://localhost:8080/topics/sports/messages
curl -d '{"message":"Sports rule", "userId":1 }' -H "Content-Type: application/json" -X POST http://localhost:8080/topics/sports/messages
curl -d '{"message":"Sports rule again!!", "userId":1 }' -H "Content-Type: application/json" -X POST http://localhost:8080/topics/sports/messages
curl -d '{"message":"I am into sports", "userId":1 }' -H "Content-Type: application/json" -X POST http://localhost:8080/topics/sports/messages
curl -d '{"message":"I am into sports ", "userId":1 }' -H "Content-Type: application/json" -X POST http://localhost:8080/topics/sports/messages
curl -d '{"message":"Sports all the time ", "userId":1 }' -H "Content-Type: application/json" -X POST http://localhost:8080/topics/sports/messages
curl -d '{"message":"In Morning ", "userId":1 }' -H "Content-Type: application/json" -X POST http://localhost:8080/topics/sports/messages
curl -d '{"message":"In the evening ", "userId":1 }' -H "Content-Type: application/json" -X POST http://localhost:8080/topics/sports/messages
echo -e '\n'

echo -e '\n(11) all messages under sports topic'

curl http://localhost:8080/topics/sports/messages

echo -e '\n'

echo -e '\n(12) Subscribe Martin to sports topic'

curl -d '{"name":"sports" }' -H "Content-Type: application/json" -X POST http://localhost:8080/users/1/subscriptions

echo -e '\n'

echo -e '\n(13) Get Martins all subs'

curl  http://localhost:8080/users/1/subscriptions

echo -e '\n'










