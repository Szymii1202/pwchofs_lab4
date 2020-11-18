FROM java:8
LABEL maintainer="Dudziak Szymon"
COPY Main.java .
RUN ["chmod", "+x", "Main.java"]
RUN curl -L -o /mysql-connector-java-8.0.13.jar https://repo1.maven.org/maven2/mysql/mysql-connector-java/8.0.13/mysql-connector-java-8.0.13.jar
RUN javac Main.java
CMD java Main 
CMD ["java", "-classpath", "mysql-connector-java-8.0.13.jar:.","Main"]