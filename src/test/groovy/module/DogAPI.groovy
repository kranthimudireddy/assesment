package module

import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import groovy.util.logging.Log4j
import helper.APITestHelper
import org.hamcrest.MatcherAssert

@Log4j
class DogAPI {

    public static Object getListofDogBreeds(String defaulturl, String apiUrl) {
        APITestHelper helper = new APITestHelper()
        def alldogsJSON = ""
        String alldogsStr = helper.executeAPIGET(defaulturl + apiUrl)
        if (alldogsStr != null) {
            log.info("String format of all dog breeds: " + alldogsStr)
            def parser = new JsonSlurper()
            alldogsJSON = parser.parseText(alldogsStr)
        }
        alldogsJSON
    }

    public static boolean getDogBreed(def dogsJSON, String breedName) {
        MatcherAssert.assertThat("$breedName breed is not in the list", dogsJSON?.message.getAt(breedName) != null)
        true
    }

    public static Object getListofSubBreeds(String defaulturl, String apiUrl) {
        APITestHelper helper = new APITestHelper()
        String subbreedStr = helper.executeAPIGET(defaulturl + apiUrl)
        def subbreedJSON = ""
        if (subbreedStr != null) {
            log.info("String format of all sub breed breeds: " + subbreedStr)
            def parser = new JsonSlurper()
            subbreedJSON = parser.parseText(subbreedStr)
        }
        subbreedJSON
    }

    public static Object getRandomImage(String defaulturl, String apiUrl) {
        APITestHelper helper = new APITestHelper()
        String randomStr = helper.executeAPIGET(defaulturl + apiUrl)
        def randomJSON
        if (randomStr != null) {
            log.info("String format of all sub breed golden: " + randomStr)
            def parser = new JsonSlurper()
            randomJSON = parser.parseText(randomStr)
        }
        randomJSON
    }


}