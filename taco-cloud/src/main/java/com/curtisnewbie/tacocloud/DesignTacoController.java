package com.curtisnewbie.tacocloud;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import com.curtisnewbie.tacocloud.Ingredient.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping("/design") // root path
@SessionAttributes("order") // add an addtribute "order" that is accessible within the session, such
                            // that we don't need to manually add this attribute in methods
                            // implicitly.
public class DesignTacoController {

    private static final Logger log = LoggerFactory.getLogger(DesignTacoController.class);

    private final IngredientRepository ingredientRepo;
    private final TacoRepository tacoRepository;

    public DesignTacoController(IngredientRepository ingredientRepo,
            TacoRepository tacoRepository) {
        this.ingredientRepo = ingredientRepo;
        this.tacoRepository = tacoRepository;
    }

    // spesify the order attribute as a model for the view, this method only tells
    // what a "order"
    // attribute is, note that this attribute is in session scope, so can be shared
    // among requests
    // of a session.
    @ModelAttribute("order")
    public Order order() {
        return new Order();
    }

    // this model is not declared as Session Attribute, so it's not shared among the
    // requests
    @ModelAttribute("newTaco")
    public Taco taco() {
        return new Taco();
    }

    @GetMapping
    public String showDesignForm(Model model) {
        // model is a model for UI in MVC, addtributes
        // added to this model is reflected or "accessiable" on the view
        List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepo.findAll().forEach(ingredient -> ingredients.add(ingredient));
        log.info("Fetched: {}", ingredients);

        Type[] types = Ingredient.Type.values();
        for (Type t : types) {
            // add ingredients as attributes by type
            model.addAttribute(t.toString().toLowerCase(), filterByType(ingredients, t));
        }
        return "design"; // go to design.html view ( http://localhost:8080/design )
    }

    // here, @ModelAttribute is again used on order because this method will expose
    // this attribute
    // to the view based on the @ModelAttribute method above. Differently, newTaco
    // is from the
    // client, rather than exposed.
    @PostMapping
    public String processDesign(@Valid Taco newTaco, Errors errors,
            @ModelAttribute(binding = false) Order order, @AuthenticationPrincipal User user) {
        if (errors.hasErrors()) {
            log.error("Taco Design Invalid {}", newTaco.toString());
            // if there is error while validating beans, redirect to design.html
            return "design";
        }
        log.info("Processing design: {}\nDesign belongs to {}", newTaco, user.toString());
        Taco saved = tacoRepository.save(newTaco);
        // this order is in the session, so adding this taco is reflected in the view
        order.addTaco(saved);
        // redirect to orders/current, which is the OrderController
        return "redirect:/orders/current";
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
