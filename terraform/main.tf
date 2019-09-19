# Configure the AWS Provider
provider "aws" {
  version = "~> 2.0"

  access_key = ""
  secret_key = ""
  region  = "${var.region}"
}

data "template_file" "userdata" {
    template = "${file("userdata.tpl")}"
}

resource "tls_private_key" "icorp-key" {
  algorithm = "RSA"
  rsa_bits  = 4096
}

resource "aws_key_pair" "generated_key" {
  key_name   = "${var.ssh_key_name}"
  public_key = "${tls_private_key.icorp-key.public_key_openssh}"
}
resource "aws_instance" "jenkins-ec2" {
    ami = "${var.aws_ami}"
    instance_type = "${var.instance_type}"
    key_name = "${var.ssh_key_name}"
    vpc_security_group_ids = [
        "${aws_security_group.public.id}"
    ]
    subnet_id = "${aws_subnet.public.id}"
    user_data = "${data.template_file.userdata.rendered}"
}

resource "aws_vpc" "main" {
    cidr_block = "10.0.0.0/16" 

    tags = {
        Name = "icorp-${var.env_name}-vpc"
    }
}

resource "aws_internet_gateway" "main" {
    vpc_id = "${aws_vpc.main.id}"
}

resource "aws_route" "internet_access" {
    route_table_id = "${aws_vpc.main.main_route_table_id}"
    destination_cidr_block = "0.0.0.0/0"
    gateway_id   = "${aws_internet_gateway.main.id}"
}

resource "aws_subnet" "public" {
    vpc_id = "${aws_vpc.main.id}"
    cidr_block = "10.0.0.0/24"
    map_public_ip_on_launch = true

    depends_on = ["aws_internet_gateway.main"]
}

resource "aws_security_group" "public" {
    name = "icorp-${var.env_name}-public-sg"
    vpc_id = "${aws_vpc.main.id}"

    # SSH
    ingress {
        from_port = 22
        to_port = 22
        protocol = "tcp"
        cidr_blocks = [
            "0.0.0.0/0"
        ]
    }

    # HTTP
    ingress {
        from_port = 80
        to_port = 80
        protocol = "tcp"
        cidr_blocks = [
            "0.0.0.0/0"
        ]
    }

    # Jenkins Web UI
    ingress {
        from_port = 49001
        to_port = 49001
        protocol = "tcp"
        cidr_blocks = [
            "0.0.0.0/0"
        ]
    }
    # Outbount traffic
    egress {
        from_port = 0
        to_port = 0
        protocol = "-1"
        cidr_blocks = [
            "0.0.0.0/0"
        ]
    }
}

resource "local_file" "icorp-private-key" {
    content     = "${tls_private_key.icorp-key.private_key_pem}"
    filename = "./icorp.pem"
}
