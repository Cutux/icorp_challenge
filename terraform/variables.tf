variable "env_name" {
    description = "Environment name and resource namespace."
    default = "test"
}

variable "region" {
    description = "Target AWS region."
    default = "us-east-1"
}

variable "s3prefix" {
    description = "Unique S3 prefix for bucket names."
    default = "icorptest"
}

variable "aws_ami" {
    description = "RHEL 8 AMI."
    default = "ami-0c322300a1dd5dc79"
}

variable "instance_type" {
    description = "AWS instance type."
    default = "t2.micro"
}

variable "ssh_key_name" {
    description = "Name of preloaded root ssh key."
    default = "icorp_test"
}