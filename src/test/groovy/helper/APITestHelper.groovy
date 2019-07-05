package helper

import groovy.util.logging.Log4j
import static groovyx.net.http.Method.GET
import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder

@Log4j
class APITestHelper {

  public static String executeAPIGET(String apiURL) {
    String contenttext = ""
    def http = new HTTPBuilder(apiURL)
    http.request(GET) {
      requestContentType = ContentType.URLENC

      response.success = { resp ->
        log.info("Execute API Successful : $resp.statusLine")
        contenttext = resp.entity.content.text
        log.info("API Details : $contenttext")
        contenttext
      }
      response.failure = { resp ->
        log.info("Execute API Failed : $resp.statusLine")
        log.info("Execute API Failure : $resp.entity.content.text")
        null
      }
    }

  }


}
