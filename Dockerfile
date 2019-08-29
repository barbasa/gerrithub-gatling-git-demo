FROM denvazh/gatling:latest

ADD https://gerrit-ci.gerritforge.com/job/gatling-git-sbt-master/lastSuccessfulBuild/artifact/target/scala-2.12/gatling-git-extension.jar /opt/gatling/lib/

COPY ./src/test/scala/gerrithub /opt/gatling/user-files/simulations
COPY ./src/test/resources/logback.xml /opt/gatling/conf
COPY ./src/test/resources/application.conf /opt/gatling/conf
COPY ./src/test/resources/data /opt/gatling/user-files/resources/data
