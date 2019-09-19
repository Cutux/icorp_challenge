output "ec2_public_ip" {
  value = "${aws_instance.jenkins-ec2.public_ip}"
}

output "ec2_id" {
  value = "${aws_instance.jenkins-ec2.id}"
}