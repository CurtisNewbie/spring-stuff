package com.curtisnewbie.tacocloud;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository // @Repository is a specialisation of @Component
public class JdbcIngredientRepository implements IngredientRepository {

    private JdbcTemplate jdbc;

    // constructor injection doesn't need @Autowired
    public JdbcIngredientRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Iterable<Ingredient> findAll() {
        return jdbc.query("SELECT id, name, type FROM Ingredient", this::ingredientRowMapper);
    }

    @Override
    public Ingredient findOne(String id) {
        return jdbc.queryForObject("SELECT id, name, type FROM Ingredient WHERE id=?",
                this::ingredientRowMapper, id);
    }

    @Override
    public Ingredient save(Ingredient ingredient) {
        jdbc.update("INSERT INTO Ingredient (id, name, type) VALUES (?, ?, ?)", ingredient.getId(),
                ingredient.getName(), ingredient.getType().toString());
        return ingredient;
    }

    private Ingredient ingredientRowMapper(ResultSet rs, int rowNum) throws SQLException {
        return new Ingredient(rs.getString("id"), rs.getString("name"),
                Ingredient.Type.valueOf(rs.getString("type")));
    }

}
