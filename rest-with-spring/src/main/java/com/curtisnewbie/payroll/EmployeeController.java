package com.curtisnewbie.payroll;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {

    private final EmployeeRepository repo;
    private final EmployeeModelAssembler modelAssembler;

    // EmployeeRepository is injected
    EmployeeController(EmployeeRepository repo, EmployeeModelAssembler modelAssembler) {
        this.repo = repo;
        this.modelAssembler = modelAssembler;
    }

    @GetMapping("/employees")
    CollectionModel<EntityModel<Employee>> all() {
        List<EntityModel<Employee>> listOfLinks = repo.findAll().stream().map(modelAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(listOfLinks,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmployeeController.class).all()).withSelfRel());

    }

    @PostMapping("/employees")
    ResponseEntity<EntityModel<Employee>> newEmployee(@RequestBody Employee newEmp) {
        var model = modelAssembler.toModel(repo.save(newEmp));
        // create a URI for this object and stores it in Location header, plus putting
        // the actual model in response body
        return ResponseEntity.created(model.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(model);
    }

    @GetMapping("/employees/{id}")
    EntityModel<Employee> one(@PathVariable Long id) {
        // instead of returning the Employee directly, return links using HAETEOAS
        Employee emp = repo.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
        return modelAssembler.toModel(emp);
    }

    @PutMapping("/employees/{id}")
    ResponseEntity<EntityModel<Employee>> replaceEmployee(@RequestBody Employee newEmp, @PathVariable Long id) {

        var model = modelAssembler.toModel(repo.findById(id).map(emp -> {
            emp.setName(newEmp.getName());
            emp.setRole(newEmp.getRole());
            return repo.save(emp);
        }).orElseGet(() -> {
            newEmp.setId(id);
            return repo.save(newEmp);
        }));

        return ResponseEntity.created(model.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(model);
    }

    @DeleteMapping("/employees/{id}")
    ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}