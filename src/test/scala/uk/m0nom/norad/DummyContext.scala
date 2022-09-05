package uk.m0nom.norad

import com.amazonaws.services.lambda.runtime.{ClientContext, CognitoIdentity, Context, LambdaLogger}
import com.amazonaws.services.lambda.runtime.events.{APIGatewayV2HTTPEvent, APIGatewayV2HTTPResponse}

class DummyContext extends Context {
  /** As seen from class DummyContext, the missing signatures are as follows.
   *  For convenience, these are usable as stub implementations.
   */
  def getAwsRequestId(): String = ???
  def getClientContext(): com.amazonaws.services.lambda.runtime.ClientContext = ???
  def getFunctionName(): String = ???
  def getFunctionVersion(): String = ???
  def getIdentity(): com.amazonaws.services.lambda.runtime.CognitoIdentity = ???
  def getInvokedFunctionArn(): String = ???
  def getLogGroupName(): String = ???
  def getLogStreamName(): String = ???
  def getLogger(): com.amazonaws.services.lambda.runtime.LambdaLogger = ???
  def getMemoryLimitInMB(): Int = ???
  def getRemainingTimeInMillis(): Int = ???

}
