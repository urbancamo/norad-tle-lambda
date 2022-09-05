aws lambda create-function \
  --function-name norad-tle-lambda \
  --role "arn:aws:iam::813234712574:role/lambda-executor" \
  --zip fileb://./target/scala-3.1.3/norad-tle-lambda.jar \
  --runtime java11 \
  --memory 256 \
  --handler "uk.m0nom.norad.Main::handler"
