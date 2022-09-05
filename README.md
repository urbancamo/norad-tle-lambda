# NORAD TLE Lambda

## Introduction
This is an AWS Lambda that downloads the NORAD daily amateur radio satellite TLE data from the
[Celestrak Amateur Satellite Data File](http://www.celestrak.com/NORAD/elements/amateur.txt) 
and stores it in S3 for use by the [ADIF Processor](https://bit.ly/adifproc).

## Configuration
The script [create-lambda.sh](./src/main/scripts/create-lambda.sh) will create the lambda. You will need
to define the role `lambda-executor` in AWS with permission policy `AWSLambdaRole` and use `aws configure`
setup [AWS cli](https://aws.amazon.com/cli/) with an account with permissions to create a lambda.

## Triggering the Lambda
The lambda needs to be triggered once a day to download the latest TLE data.
This can be done using [AWS Event Bridge]().
Create a new rule with the following information:

- **Name**: `norad-tle-lambda-daily-trigger`
- **Description**: Triggers the download of NORAD Amateur Satellite TLE data for the ADIF Processor every day.
- Select `Schedule` option.
- Select `A schedule that runs at a regular rate, such as every 10 minutes.`
- Enter rate of `1 days`
- Select Target 1 of `AWS Service`
- Choose `Lambda Function`
- Select the `norad-tle-lambda`

That's it, other than to check that the lambda is invoked correctly and puts the TLE data with the right date
in the S3 `adif-processor/norad` bucket.

## References
- [AWS Lambda Functions in Scala](https://www.varokas.com/aws-lambda-functions-in-scala/)
- [Using AWS Lambda with Amazon EventBridge](https://docs.aws.amazon.com/lambda/latest/dg/services-cloudwatchevents.html)
