# 基础镜像
FROM gcr.io/spark-operator/spark:v3.1.1

# 作者
LABEL maintainer "ccm@ccm.ink"

#拷贝jar包到制定目录
COPY ./mongo-spark-connector_2.12-3.0.1.jar /opt/spark/jars/
COPY ./mongodb-driver-sync-4.0.5.jar  /opt/spark/jars/
COPY ./mongodb-driver-core-4.0.5.jar /opt/spark/jars/
COPY ./bson-4.0.5.jar /opt/spark/jars/
COPY ./Test.jar /opt/spark/examples/jars/
