provider "aws" {
  region = "eu-west-3"
}

module "vpc" {
  source = "../modules/vpc"

  project_name = "linguistai"
  environment  = "dev"
  vpc_cidr     = "10.0.0.0/16"

  azs = ["eu-west-3a", "eu-west-3b"]

  public_subnets      = ["10.0.1.0/24", "10.0.2.0/24"]
  private_app_subnets = ["10.0.11.0/24", "10.0.12.0/24"]
  private_db_subnets  = ["10.0.21.0/24", "10.0.22.0/24"]
}

