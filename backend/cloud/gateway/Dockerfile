FROM ibm-semeru-runtimes:open-21.0.2_13-jre-jammy
LABEL authors="ge.lee"
LABEL type=cloud
LABEL product=mungtique
VOLUME /tmp
ENV TZ=Asia/Seoul
ARG JAR_FILE=./build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

#docker build -t mungtique/gateway:0.0.1 .