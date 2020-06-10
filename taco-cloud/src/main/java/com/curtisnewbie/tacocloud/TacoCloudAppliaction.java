package com.curtisnewbie.tacocloud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class TacoCloudAppliaction {

    @Autowired
    private JdbcTemplate jdbc;

    public static void main(String[] args) {
        SpringApplication.run(TacoCloudAppliaction.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(IngredientRepository repo) {
        return args -> {
            // user for Spring security, this only works for jdbcAuthentication, not
            // ldapAuthentication
            // int row = jdbc.update(
            // "INSERT INTO User (name, password, enabled) VALUES ('apple', 'juice',
            // TRUE)");
            // row += jdbc.update("INSERT INTO Authority (name, auth) VALUES ('apple',
            // 'USER_ROLE')");
            // System.out.println(row == 0 ? "Insert failed" : "There are users!");
            // for demo data
            repo.save(new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP));
            repo.save(new Ingredient("COTO", "Corn Tortilla", Ingredient.Type.WRAP));
            repo.save(new Ingredient("GRBF", "Ground Beadf", Ingredient.Type.PROTEIN));
            repo.save(new Ingredient("CARN", "Carnitas", Ingredient.Type.PROTEIN));
            repo.save(new Ingredient("TMTO", "Diced Tomatoes", Ingredient.Type.VEGGIES));
            repo.save(new Ingredient("LECT", "Lettuce", Ingredient.Type.VEGGIES));
            repo.save(new Ingredient("CHED", "Cheddar", Ingredient.Type.CHEESE));
            repo.save(new Ingredient("JACK", "Monterrey Jack", Ingredient.Type.CHEESE));
            repo.save(new Ingredient("SLSA", "Salsa", Ingredient.Type.SAUSE));
            repo.save(new Ingredient("SRCR", "Sour Cream", Ingredient.Type.SAUSE));
        };
    }
}
