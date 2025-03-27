FROM ibm-semeru-runtimes:open-21.0.2_13-jre-jammy
LABEL authors="ge.lee"
LABEL type=cloud
LABEL product=mungtique
VOLUME /tmp
ENV TZ=Asia/Seoul
ARG JAR_FILE=./build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

#docker build -t gaeun0811/eureka:1.0 .
#docker push gaeun0811/eureka:1.0
