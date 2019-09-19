#!/bin/bash

# Install docker
sudo yum install -y yum-utils device-mapper-persistent-data lvm2
sudo yum-config-manager --add-repo https://download.docker.com/linux/centos/docker-ce.repo
sudo yum update
sudo yum install docker-ce docker-ce-cli containerd.io -y --nobest

# Install node and newman
sudo yum install -y nodejs
cd ~
sudo npm install -g newman

# Install Git
sudo yum install git -y

# Install Java JDK
sudo yum install -y  java-1.8.0-openjdk-devel

# Download docker-compose
sudo curl -L "https://github.com/docker/compose/releases/download/1.23.1/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose

# Make binary executable
sudo chmod +x /usr/local/bin/docker-compose

# Pull Jenkins, MongoDB image
sudo docker pull jenkins/jenkins:lts
sudo docker pull mongo

# Create Jenkins directory
mkdir ~/jenkins

# Create Docker Network
sudo docker network create icorp-network

# Run Jenkins, MongoDB container
sudo docker run -d --net icorp-network -p 49002:8080 -v $PWD/jenkins:/var/jenkins_home:z --name jenkins -t jenkins/jenkins:lts
sudo docker run -d --net icorp-network -p 27017-27019:27017-27019 --name mongodb mongo


# Create admin user in MongoDB 
sudo docker exec -it mongodb bash -c "mongo --eval 'db.createUser({ user: \"admin\", pwd: \"admin\", roles: [ { role: \"root\", db: \"admin\" } ]});' admin"
