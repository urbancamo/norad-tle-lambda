aws lambda update-function-code \
  --function-name norad-tle-lambda \
  --zip fileb://./target/scala-3.1.3/norad-tle-lambda.jar