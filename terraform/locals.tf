locals {
  api_gateway_url = data.terraform_remote_state.infra.outputs.api_gateway_url
  jdbc_url        = "jdbc:postgresql://${data.aws_db_instance.db_instance.address}:${data.aws_db_instance.db_instance.port}/${data.aws_db_instance.db_instance.db_name}"
}
