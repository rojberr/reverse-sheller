terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 4.16"
    }
  }
}

provider "aws" {
  profile = "default"
  region  = "eu-central-1"
}

resource "aws_security_group" "reverse_sheller_sg_ssh" {
  name = "reverse_sheller_sg_ssh"

  #Incoming traffic
  ingress {
    from_port = 0
    to_port = 65535
    protocol = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  #Outgoing traffic
  egress {
    from_port = 0
    protocol = "-1"
    to_port = 0
    cidr_blocks = ["0.0.0.0/0"]
  }
}

resource "aws_instance" "reverse_sheller" {
  ami           = "ami-0a5b5c0ea66ec560d"
  instance_type = "t2.micro"
  key_name = "reverse-sheller"
  user_data = file("user-data.sh")
  security_groups = ["reverse_sheller_sg_ssh"]
}
