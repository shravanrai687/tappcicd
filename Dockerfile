FROM openjdk:8
EXPOSE 8081
ADD target/TweetApp-0.0.1-SNAPSHOT.jar TweetApp.jar
ENTRYPOINT ["java","-jar","/TweetApp.jar"]