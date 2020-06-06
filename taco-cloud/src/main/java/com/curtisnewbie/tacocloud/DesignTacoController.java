package com.curtisnewbie.tacocloud;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import com.curtisnewbie.tacocloud.Ingredient.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/design") // root path
public class DesignTacoController {

    private static final Logger log = LoggerFactory.getLogger(DesignTacoController.class);

    @GetMapping
    public String showDesignForm(Model model) {
        // model is a model for UI in MVC, addtributes
        // added to this model is reflected or "accessiable" on the view
        List<Ingredient> ingredients =
                Arrays.asList(new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
                        new Ingredient("COTO", "Corn Tortilla", Type.WRAP),
                        new Ingredient("GRBF", "Ground Beadf", Type.PROTEIN),
                        new Ingredient("CARN", "Carnitas", Type.PROTEIN),
                        new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES),
                        new Ingredient("LECT", "Lettuce", Type.VEGGIES),
                        new Ingredient("CHED", "Cheddar", Type.CHEESE),
                        new Ingredient("JACK", "Monterrey Jack", Type.CHEESE),
                        new Ingredient("SLSA", "Salsa", Type.SAUSE),
                        new Ingredient("SRCR", "Sour Cream", Type.SAUSE));

        Type[] types = Ingredient.Type.values();
        for (Type t : types) {
            // add ingredients as attributes by type
            model.addAttribute(t.toString().toLowerCase(), filterByType(ingredients, t));
        }
        // model.addAttribute("design", new Taco()); // TODO: not implmeneted yet
        return "design"; // go to design.html view ( http://localhost:8080/design )
    }

    /**
     * Return List of Ingredient(s) which are of {@code type}
     * 
     * @param ingredients
     * @param type
     * @return
     */
    private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        return ingredients.stream().filter(v -> v.getType().equals(type))
                .collect(Collectors.toList());
    }
}
