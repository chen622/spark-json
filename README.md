# Spark 对 JSON 的支持

国科大大数据系统与分析课程大作业，调研 Spark 对 JSON 数据格式的支持，主要测试了读取 JSON 文件、读取 Paraquet 文件、读取 MongoDB、各种 AP 操作。

## 快速上手
### 1. 搭建 Spark
这里使用的是 Kubernetes 进行容器管理，然后使用 Spark Operator 来在 K8s 上运行 Spark 程序。

Kubernetes 安装教程：https://pipe.ccm.ink/archives/k8s
Spark Operator：https://github.com/GoogleCloudPlatform/spark-on-k8s-operator

### 2. 运行 Spark

使用 `k8s-application` 目录下的 yaml 文件，运行对应的 Spark 程序。
```bash
kubectl apply -f readfile.yaml
```
