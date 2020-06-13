package com.curtisnewbie.clients;

import java.net.URI;
import java.util.Collection;
import com.curtisnewbie.model.Ingredient;
import com.curtisnewbie.model.IngredientModel;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.client.Traverson;
import org.springframework.web.client.RestTemplate;

// Traverson is good for traversing, but it's not good at modifying the data
public class IngredientHateoasClient {

    public static final String INGREDIENTS_URI = "http://localhost:8080/ingredients";
    private RestTemplate restTemplate;

    public IngredientHateoasClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Collection<IngredientModel> allIngredients() {
        Traverson traverson = allIngredientsTraverson();
        // start traversing
        return traverson.follow("ingredients").toObject(ingredientsType()).getContent();
        // the follow() can be nested, consider it like traversing tree
        // e..g., -> traverson.follow("ingredients").follow("subIngredients").toObject(...);
        // or -> traverson.follow("ingredients","subIngredients").toObject(...);
    }

    // we can use Traverson for traversing, and use RestTemplate to update the data
    public IngredientModel addIngredient(Ingredient ingredient) {
        Traverson traverson = allIngredientsTraverson();
        // use Traverson to navigate and get associated URL
        String ingredientsUrl = traverson.follow("ingredients").asLink().getHref();
        // use RestTemplate to use REST services
        return restTemplate.postForObject(ingredientsUrl, ingredient, IngredientModel.class);
    }

    public Traverson allIngredientsTraverson() {
        // create Traverson as a HATEOAS/HAL JSON
        Traverson traverson = new Traverson(URI.create(INGREDIENTS_URI), MediaTypes.HAL_JSON);
        return traverson;
    }

    /**
     * Get a {@code ParameterizedTypeReference} for {@code CollectionModel<IngredientModel>} which
     * helps the Traverson to convert one to Object
     */
    public ParameterizedTypeReference<CollectionModel<IngredientModel>> ingredientsType() {
        // create a type as reference to what the traverson should convert the json object to
        return new ParameterizedTypeReference<CollectionModel<IngredientModel>>() {
        };
    }
}
