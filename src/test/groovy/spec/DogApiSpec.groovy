package spec

import geb.spock.GebSpec
import groovy.json.JsonOutput
import module.DogAPI
import groovy.util.logging.Log4j

@Log4j
class DogApiSpec extends GebSpec {

    String defaulturl = "https://dog.ceo/api"
    String listAllDogsApiUrl = "/breeds/list/all"
    String retrieveBreedUrl = "/breed/retriever/list"
    String randomBreedUrl = "/breed/retriever/golden/images/random"
    DogAPI dogAPI = new DogAPI()

    def "Perform an API request to produce a list of all dog breeds" () {
        given: "api request to list all dog breeds"
            def alldogsJSON = dogAPI.getListofDogBreeds(defaulturl, listAllDogsApiUrl)
            log.info("List of all dogs in json format" + JsonOutput.prettyPrint(JsonOutput.toJson(alldogsJSON)))
    }

    def "Using code, verify “retriever” breed is within the list" () {
        when: "Retriever all brands"
            def alldogsJSON = dogAPI.getListofDogBreeds(defaulturl, listAllDogsApiUrl)
        then: "verify breed is in the list"
            dogAPI.getDogBreed(alldogsJSON, "retriever")
    }

    def "Perform an API request to produce a list of sub-breeds for 'retriever'" () {
        given: "list of sub-breeds for 'retriever'"
            def subbreedJSON = dogAPI.getListofSubBreeds(defaulturl, retrieveBreedUrl)
            log.info("list of sub-breeds for 'retriever'" + JsonOutput.prettyPrint(JsonOutput.toJson(subbreedJSON)))
    }

    def "Perform an API request to produce a random image / link for the sub-breed 'golden'" () {
        given: "Get a random image for the subbreed golden"
            def randomJSON = dogAPI.getRandomImage(defaulturl, randomBreedUrl)
            log.info("random image / link for the sub-breed 'golden':" + JsonOutput.prettyPrint(JsonOutput.toJson(randomJSON)))
    }
}
