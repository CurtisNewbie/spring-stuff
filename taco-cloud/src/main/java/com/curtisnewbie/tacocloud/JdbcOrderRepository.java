// package com.curtisnewbie.tacocloud;

// import java.util.Date;
// import java.util.HashMap;
// import java.util.Map;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.jdbc.core.JdbcTemplate;
// import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
// import org.springframework.stereotype.Repository;

// @Repository
// public class JdbcOrderRepository implements OrderRepository {

// private static final Logger log = LoggerFactory.getLogger(JdbcOrderRepository.class);
// // SimpleJdbcInsert further simplifies Insert operation by taking a Map of param
// private SimpleJdbcInsert orderInserter;
// private SimpleJdbcInsert orderTacoInserter;

// // optional, it can help converting objects to Map
// private ObjectMapper ObjectMapper = new ObjectMapper();

// public JdbcOrderRepository(JdbcTemplate jdbc) {
// this.orderInserter = new SimpleJdbcInsert(jdbc).withTableName("TacoOrder")
// .usingGeneratedKeyColumns("id");
// this.orderTacoInserter = new SimpleJdbcInsert(jdbc).withTableName("TacoOrder_Taco");
// }

// @Override
// public Order save(Order order) {
// // this operation follows the same logic as in JdbcTacoRepository#save() method
// order.setPlacedAt(new Date());
// long orderId = persistOrder(order);
// order.setId(orderId);
// saveTacosToOrder(order);
// log.info("processed Order: " + order);
// return order;
// }

// @SuppressWarnings("unchecked")
// private long persistOrder(Order order) {
// Map<String, Object> params = ObjectMapper.convertValue(order, Map.class);
// // this overwrites the previous placedAt property, as ObjectMapper converts date to long,
// // which is not what we want.
// params.put("placedAt", order.getPlacedAt());
// long orderId = orderInserter.executeAndReturnKey(params).longValue();
// return orderId;
// }

// private void saveTacosToOrder(Order order) {
// order.getTacos().forEach(taco -> {
// Map<String, Object> param = new HashMap<>();
// param.put("taco", taco.getId());
// param.put("tacoOrder", order.getId());
// orderTacoInserter.execute(param);
// });
// }
// }
