# springboot-rabbitmq
Demo for springboot app (producer and consumer) communicating with message broker RabbitMQ. All the components are running in different docker container

Steps to execute the application:
You have RabbitMq and Docker installed on your machine
On Docker's website you can find following command to run the image of RabbitMQ in a Docker container
command: docker run -d --hostname my-rabbit --name some-rabbit -p 15672:15672 RabbitMQ:3-management
open a browser and try "localhost:15672". you will see a login page
use userid: guest and password:guest

For RabbitMQ	
1) Create an exchange "currency_exchange" of type topic.
2) Create a queue "test_queue"
3) Under exchange click on "currency_exchange" and bind "total_queue" to "currency_exchange". use "forex_exchange" as routing key.

For Scheduler:
1) Download the scheduler and save it some location on your machine
2) Kindly update the token in demo.properties under scheduler --> src --> main --> resources. 
3) Assumption:Token is verified and can access the rest endpoint for all the mentioned currencies.
4) Navigate to the directory where pom.xml is placed
5) use " mvnw install dockerfile:build" to build a image of the scheduler that can be deployed in a docker container
6) use docker run -p 8080:8080 -t <docker_image_name> (in this scenario it is demo/scheduler:latest)

For Consumer:
1) Navigate to the directory where pom.xml is placed
2) Please ensure you have opencsv jar in class path as it is required to build the csv file.
2) use " mvnw install dockerfile:build" to build a image of the scheduler that can be deployed in a docker container
3) use docker run -p 8080:8080 -t <docker_image_name> (in this scenario it is demo/consumer:latest)




