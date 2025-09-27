data "aws_availability_zones" "available" {}

data "terraform_remote_state" "infra" {
  backend = "s3"
  config = {
    bucket = "tc-2025-tf-states-bucket"
    key    = "PRD/tc-infra"
    region = "us-east-1"
  }
}

data "aws_eks_cluster" "cluster" {
  name = data.terraform_remote_state.infra.outputs.cluster_name
}

data "aws_eks_cluster_auth" "cluster" {
  name = data.aws_eks_cluster.cluster.name
}

data "terraform_remote_state" "database" {
  backend = "s3"
  config = {
    bucket = "tc-2025-tf-states-bucket"
    key    = "PRD/tc-database"
    region = "us-east-1"
  }
}

data "aws_db_instance" "db_instance" {
  db_instance_identifier = data.terraform_remote_state.database.outputs.database_identifier
}
