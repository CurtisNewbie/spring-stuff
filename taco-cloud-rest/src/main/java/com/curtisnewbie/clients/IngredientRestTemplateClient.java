package com.curtisnewbie.clients;

import java.net.URI;
import java.util.*;
import com.curtisnewbie.model.Ingredient;
import com.curtisnewbie.model.IngredientModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

// RestTemplate is good for modifying the data, but Traverson is much better at traversing
public class IngredientRestTemplateClient {

    private static final Logger log = LoggerFactory.getLogger(IngredientRestTemplateClient.class);
    private RestTemplate restTemplate;

    public IngredientRestTemplateClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // insert path variable through {}, the variables inserted must be in correct order
    public IngredientModel getIngredientByIdWithVarInUrl(String ingredientId) {
        return restTemplate.getForObject("http://localhost:8080/ingredients/{id}",
                IngredientModel.class, ingredientId);
    }

    // this one is much verbose than the one above, but it doesn't require to maintain an order of
    // these orders
    public IngredientModel getIngredientByIdWithVarInMap(String ingredientId) {
        Map<String, String> urlVar = new HashMap<>();
        urlVar.put("id", ingredientId);
        URI uri = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/ingredients/{id}")
                .build(urlVar);
        return restTemplate.getForObject(uri, IngredientModel.class);
    }

    public IngredientModel getIngredientByResponseEntity(String ingredientId) {
        ResponseEntity<IngredientModel> responseEntity = restTemplate.getForEntity(
                "http://localhost:8080/ingredients/{id}", IngredientModel.class, ingredientId);

        // with getForEntity() we can not only get the model we need, but extra information about
        // the response
        HttpHeaders headers = responseEntity.getHeaders();
        log.info("Ingredient Fetched at: {}, Content-Type: {}, Origin: {}", headers.getDate(),
                headers.getContentType(), headers.getOrigin());

        // the model is stored in the response body
        return responseEntity.getBody();
    }

    public void updateIngredient(Ingredient ingredient) {
        restTemplate.put("http://localhost:8080/ingredients/{id}", ingredient, ingredient.getId());
    }

    public void deleteIngredient(String ingredientId) {
        restTemplate.delete("http://localhost:8080/ingredients/{id}", ingredientId);
    }

    public IngredientModel postIngredientForObject(Ingredient ingredient) {
        return restTemplate.postForObject("http://localhost:8080/ingredients", ingredient,
                IngredientModel.class);
    }

    // This one is for location, but if we want both the object and location, we can use
    // RestTemplate#postForEntity()
    public URI postIngredientForLocation(Ingredient ingredient) {
        return restTemplate.postForLocation("http://localhost:8080/ingredients", ingredient);
    }
}
