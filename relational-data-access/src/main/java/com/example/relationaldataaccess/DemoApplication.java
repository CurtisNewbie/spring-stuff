package com.example.relationaldataaccess;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

@SpringBootApplication
public class DemoApplication {

	private static final Logger log = LoggerFactory.getLogger(DemoApplication.class);

	@Autowired // similar to the one used for consuming REST, this one is for JDBC
	JdbcTemplate jdbcTemplate;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner run() {
		return args -> {
			try {

				log.info("Application started, creating tables.");
				jdbcTemplate
						.execute("CREATE TABLE customer (id SERIAL, first_name VARCHAR(255), last_name VARCHAR(255))");
				// use Stream api to split each string to a pair of string (firstname,
				// lastname), and put them in a list. Object type is intentionially set.
				List<Object[]> namePairs = Arrays.asList("John Woo", "Jeff Dean", "Josh Bloch", "Josh Long").stream()
						.map(fullname -> fullname.split(" ")).collect(Collectors.toList());
				namePairs.forEach(name -> log.info("Splited Name: [{}, {}],", name[0], name[1]));

				// batch update the list of name[2]
				jdbcTemplate.batchUpdate("INSERT INTO customer (first_name, last_name) VALUES (?, ?)", namePairs);

				log.info("Querying rows where firstname is Josh");
				// this handles how the result handled, it can also be
				// RowCallbackHandler
				RowMapper<Customer> mapper = (resultSet, numOfRows) -> {
					return new Customer(resultSet.getLong("id"), resultSet.getString("first_name"),
							resultSet.getString("last_name"));
				};
				// this is tha arguments put into the query, thus the result of the query is a
				// list
				Object[] queryArgs = new Object[] { "Josh" };
				List<Customer> list = jdbcTemplate.query(
						"SELECT id, first_name, last_name FROM customer WHERE first_name = ?", queryArgs, mapper);

				list.forEach(c -> log.info(c.toString()));
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		};
	}
}
