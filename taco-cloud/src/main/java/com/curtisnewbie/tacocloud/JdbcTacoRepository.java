package com.curtisnewbie.tacocloud;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcTacoRepository implements TacoRepository {

    private JdbcTemplate jdbc;

    public JdbcTacoRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Taco save(Taco taco) {
        // this operation consists of two steps:
        // 1. persist taco, get its id
        long tacoId = persistTaco(taco);
        taco.setId(tacoId);
        // 2. connect newly created taco with its ingredients
        saveIngredientsToTaco(taco);
        return taco;
    }

    // persist taco's info, but it's not associated with any ingredients yet
    private long persistTaco(Taco taco) {
        taco.setCreatedAt(new Date());
        // PreparedStatementCreatorFactory is a helper class used to create multiple PSC with
        // different param
        PreparedStatementCreator psc = new PreparedStatementCreatorFactory(
                "INSERT INTO Taco (name, createdAt) VALUES (?, ?)", Types.VARCHAR, Types.DATE)
                        .newPreparedStatementCreator(
                                Arrays.asList(taco.getName(), taco.getCreatedAt()));
        // create a key holder to get the id of this newly created Taco
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(psc, keyHolder);
        return keyHolder.getKey().longValue();
    }

    private void saveIngredientsToTaco(Taco taco) {
        List<Object[]> params = new ArrayList<>();
        taco.getIngredients().forEach(ingre -> { // batch processing
            params.add(new Object[] {taco.getId(), ingre.getId()});
        });
        jdbc.update("INSERT INTO Taco_Ingredient (taco, ingredient) VALUES (?, ?)", params);
    }

}
