# terraform {
#   backend "s3" {
#     bucket = "linguistai-prod-terraform-state"
#     key    = "vpc/terraform.tfstate"
#     region = "eu-west-3"
#     dynamodb_table = "linguistai-prod-lock"
#     encrypt = true
#   }
# }
