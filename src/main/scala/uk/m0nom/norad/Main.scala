package uk.m0nom.norad

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.events.{APIGatewayV2HTTPEvent, APIGatewayV2HTTPResponse}
import software.amazon.awssdk.auth.credentials.{DefaultCredentialsProvider, EnvironmentVariableCredentialsProvider, StaticCredentialsProvider}
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.PutObjectRequest

import java.time.format.DateTimeFormatter
import java.time.LocalDate
import scala.io.Source
import scala.util.Properties

class Main {
  private val ADIF_PROC_BUCKET = "adif-processor"

  def handler(apiGatewayEvent: APIGatewayV2HTTPEvent, context: Context): APIGatewayV2HTTPResponse = {

    val accessKey = Properties.envOrNone("AWS_ACCESS_KEY_ID")
    val secretKey = Properties.envOrNone("AWS_SECRET_ACCESS_KEY")

    accessKey match
      case Some(key) =>
        secretKey match
          case Some(secret) =>
            println(s"key is ${key.length} long, secret is ${secret.length} long")

            val localDate = LocalDate.now().format(DateTimeFormatter.ofPattern("YYYYMMdd"))
            val path = s"norad/$localDate-amateur.txt"
            try {
              val htmlContent = Source.fromURL("http://www.celestrak.com/NORAD/elements/amateur.txt")
              val content = htmlContent.mkString
              println(s"Retrieved Celestrak file")

              println("creating s3client...")
              val s3client = S3Client.builder()
                .region(Region.EU_WEST_2)
                .credentialsProvider(EnvironmentVariableCredentialsProvider.create())
                .build
              println(s"s3client created, storing as $ADIF_PROC_BUCKET/$path")
              val request = PutObjectRequest.builder().bucket(ADIF_PROC_BUCKET).key(path).build()
              val body: RequestBody = RequestBody.fromString(content)
              val put = s3client.putObject(request, body)
              println(s"stored file, ETAG is ${put.eTag()} for file $ADIF_PROC_BUCKET/$path")
            }
            catch {
              case e: Exception =>
                println(s"Caught exception: ${e.getMessage}")
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