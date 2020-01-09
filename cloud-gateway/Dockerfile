FROM java:8
MAINTAINER xianliru@163.com

RUN mkdir -p /var/logs/cloud-gateway
RUN mkdir -p /var/logs/jvm
RUN mkdir -p /var/logs/dump
RUN mkdir -p /agent

ADD ./target/*.jar app.jar
ADD ./skyWalking/ /skyWalking

RUN bash -c 'touch /app.jar'
EXPOSE 9000


ENTRYPOINT ["java", "-Duser.timezone=GMT+08","Dcsp.sentinel.app.type=1","-javaagent:/agent/skywalking-agent.jar", "-Dskywalking.agent.service_name=docker-zuul-server", "-Dskywalking.collector.backend_service=10.33.41.171:11800", "-Djava.security.egd=file:/dev/./urandom","-XX:-PrintGCDetails","-XX:-PrintGCTimeStamps","-XX:-HeapDumpOnOutOfMemoryError","-XX:HeapDumpPath=/var/logs/dump/oom_dump.dump","-Xloggc:/var/logs/jvm/app.log","-Dfile.encoding=UTF8","-Duser.timezone=GMT+08","-XX:CMSInitiatingOccupancyFraction=90","-XX:MaxGCPauseMillis=200","-XX:StringTableSize=20000","-XX:+UseG1GC","-Xss256k","-Xmx1024m","-Xms512m", "-jar", "/app.jar"]
