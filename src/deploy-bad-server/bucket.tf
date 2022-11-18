resource "aws_s3_bucket" "reverse_bucket" {
  bucket = "jar-reverse-sheller"
}

resource "aws_s3_bucket_acl" "example" {
  bucket = aws_s3_bucket.reverse_bucket.id
  acl    = "public-read"
}