# Spark 对 JSON 的支持

大数据系统与大规模数据分析课程大作业，调研 Spark 对 JSON 数据格式的支持，主要测试了读取 JSON 文件、读取 Paraquet 文件、读取 MongoDB、各种 AP 操作。

## 快速上手

> 本文只介绍有关 Spark 的安装，Hadoop 和 MongoDB 请自行寻找安装教程。

### 1. 搭建 Spark
这里使用的是 Kubernetes 进行容器管理，然后使用 Spark Operator 来在 K8s 上运行 Spark 程序。

Kubernetes 安装教程：https://pipe.ccm.ink/archives/k8s

Spark Operator：https://github.com/GoogleCloudPlatform/spark-on-k8s-operator

### 2. 测试数据

测试数据生成程序保存在了 `data` 目录下，使用 Golang 编写，采用了 Go mod 进行包管理。

``` bash
cd data
go run data
```

### 3. 运行 Spark

使用 `k8s-application` 目录下的 yaml 文件，运行对应的 Spark 程序。

```bash
# 启动程序
kubectl apply -f readfile.yaml

# 停止程序
kubectl delete -f readfile.yaml
```

运行成功后，即可看到 K8s 中有对应的 driver 和 executor 生成。

![spark-application](https://user-images.githubusercontent.com/43266446/123055681-bbdcd100-d438-11eb-8559-32e6b133d28b.png)

### 4. 查看运行时间

根据自身 Spark Operator 的配置，访问 Spark 程序对应的分析页面，在页面中能够看到每个 Job、Stage、Task 的执行时间。

![image](https://user-images.githubusercontent.com/43266446/123108228-d2048480-d46c-11eb-9928-0332b1498c9c.png)
