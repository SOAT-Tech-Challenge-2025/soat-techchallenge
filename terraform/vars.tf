variable "region" {
  description = "The AWS region to deploy resources in"
  default     = "us-east-1"
}

variable "force_rollout" {
  description = "A dummy variable to force redeployment of resources"
  type    = string
  default = ""
}

variable "broker_image" {
  description = "The Docker image for the message broker"
  default     = "rabbitmq:4.1-alpine"
}

variable "app_image" {
  description = "The Docker image for the application"
}

variable "db_user" {
  description = "The username for the RDS instance"
  sensitive   = true
}

variable "db_password" {
  description = "The password for the RDS instance"
  sensitive   = true
}

variable "jwt_expiration_time" {
  description = "The expiration time for JWT tokens in seconds"
  default     = "3600"
}

variable "mercado_pago_access_token" {
  description = "The access token for Mercado Pago"
  sensitive   = true
}

variable "mercado_pago_pos" {
  description = "The point of sale identifier for Mercado Pago"
  sensitive   = true
}

variable "mercado_pago_user_id" {
  description = "The integration user ID for Mercado Pago"
  sensitive   = true
}

variable "mercado_pago_webhook_token" {
  description = "The internal token for validating Mercado Pago webhooks"
  sensitive   = true
}

variable "jwt_secret_key" {
  description = "The secret key for signing JWT tokens"
  sensitive   = true
}

variable "broker_host" {
  description = "The hostname for the message broker"
  default     = "broker-service.tech-challenge"
  sensitive   = true
}

variable "broker_port" {
  description = "The port for the message broker"
  default     = "5672"
}

variable "broker_username" {
  description = "The username for the message broker"
  sensitive   = true
}

variable "broker_password" {
  description = "The password for the message broker"
  sensitive   = true
}

variable "tags" {
  description = "A map of tags to assign to resources"
  default = {
    Environment = "PRD"
    Project     = "tc-app"
  }
}
