#!/bin/bash

harbor_addr=$1
harbor_repo=$2
project=$3
version=$4
# service port
port=$5

imageName="$harbor_addr/$harbor_repo/$project:$version"

echo "imageName: $imageName"

# 查看当前服务器是否有容器拥有目标images的存在
containerId=$(docker ps -a | grep "$project" | awk '{print $1}')

# 如果存在,停止并删除容器
if [ -n "$containerId" ]; then
    docker stop "$containerId"
    docker rm "$containerId"
    echo "containerId: $containerId deleted"
fi

# 如果存在，删除镜像
imageId=$(docker images | grep "$project" | awk '{print $3}')
if [ -n "$imageId" ]; then
    docker rmi "$imageId"
    echo "imageId: $imageId deleted"
fi

# docker login
docker login -u admin -p Harbor12345 "$harbor_addr"

# 拉取镜像
docker pull "$imageName"

# 确定容器拉取成功
newImageId=$(docker images | grep "$project" | awk '{print $3}')
if [ -n "$newImageId" ]; then
    echo "newImageId: $newImageId"
else
    echo "image pull failed."
    exit 1
fi

# 检查端口是否占用
portUsed=$(netstat -anp | grep "$port")
if [ -n "$portUsed" ]; then
    echo "port $port is used."
    exit 1
fi

# 启动容器
docker run -d -p "$port:$port" --name "$project" "$imageName"

# 确认容器启动成功
containerId=$(docker ps -a | grep "$project" | awk '{print $1}')
if [ -n "$containerId" ]; then
    echo "containerId: $containerId"
else
    echo "container start failed."
    exit 1
fi