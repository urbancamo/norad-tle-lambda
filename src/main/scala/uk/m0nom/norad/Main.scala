package uk.m0nom.norad

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.events.{APIGatewayV2HTTPEvent, APIGatewayV2HTTPResponse}

import java.time.format.DateTimeFormatter
import java.time.LocalDate
import scala.io.Source
import scala.util.Properties

class Main  {
    private val ADIF_PROC_BUCKET = "adif-processor"

    def handler(apiGatewayEvent: APIGatewayV2HTTPEvent, context: Context): APIGatewayV2HTTPResponse = {
        //println(s"apiGatewayEvent.body = ${apiGatewayEvent.getBody()}")
        val accessKey = Properties.envOrNone("AWS_ACCESS_KEY")
        val secretKey = Properties.envOrNone("AWS_SECRET_KEY")

        accessKey match
            case Some(key) =>
                secretKey match
                    case Some(secret) =>
                        val htmlContent = Source.fromURL("http://www.celestrak.com/NORAD/elements/amateur.txt")
                        val content = htmlContent.mkString
                        val credentials = new BasicAWSCredentials(key, secret)
                        val s3client = AmazonS3ClientBuilder.standard.withCredentials(
                            new AWSStaticCredentialsProvider(credentials))
                          .withRegion(Regions.EU_WEST_2).build
                        val localDate = LocalDate.now().format(DateTimeFormatter.ofPattern("YYYYMMdd"))
                        val path = s"norad/${localDate}-amateur.txt"
                        try {
                            val put = s3client.putObject(ADIF_PROC_BUCKET, path, content)
                            //println(s"stored MD5 is ${put.getContentMd5}")
                        }
                        catch {
                            case e: Exception =>
                                return APIGatewayV2HTTPResponse.builder()
                                  .withStatusCode(400)
                                  .withBody(String.format("Exception archiving file %s into bucket %s: %s", path, ADIF_PROC_BUCKET, e.getMessage))
                                  .build()
                        }

        APIGatewayV2HTTPResponse.builder()
           .withStatusCode(201) // CREATED
           .withBody("okay")
           .build()
    }
}