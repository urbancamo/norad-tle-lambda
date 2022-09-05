package uk.m0nom.norad

import com.amazonaws.services.lambda.runtime.{ClientContext, CognitoIdentity, Context, LambdaLogger}
import com.amazonaws.services.lambda.runtime.events.{APIGatewayV2HTTPEvent, APIGatewayV2HTTPResponse}
import org.scalatest.*
import collection.mutable.Stack
import org.scalatest.flatspec.AnyFlatSpec

class LambdaSpec extends UnitSpec {

  "NORAD TLE Lambda" should "grab today's TLE file and store in S3" in {
    val lambda: Main = new Main
    val apiGatewayEvent: APIGatewayV2HTTPEvent = new APIGatewayV2HTTPEvent()
    val context: Context = new DummyContext

    assert(lambda.handler(apiGatewayEvent, context).getStatusCode == 201)
  }
}