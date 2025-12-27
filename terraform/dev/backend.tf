# terraform {
#   backend "s3" {
#     bucket = "linguistai-dev-terraform-state"
#     key    = "vpc/terraform.tfstate"
#     region = "eu-west-3"
#     dynamodb_table = "linguistai-dev-lock"
#     encrypt = true
#   }
# }
