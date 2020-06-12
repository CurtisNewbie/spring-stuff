package com.curtisnewbie.controllers;

import java.util.List;
import java.util.Optional;

import com.curtisnewbie.model.Taco;
import com.curtisnewbie.model.TacoRepository;
import com.curtisnewbie.model.TacoModel;
import com.curtisnewbie.model.TacoModelAssembler;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.CollectionModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
// application/json is the default
@RequestMapping(path = "/design", consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class TacoDesignController {

    private TacoRepository tacoRepo;
    private TacoModelAssembler assembler;

    public TacoDesignController(TacoRepository tacoRepo, TacoModelAssembler assembler) {
        this.tacoRepo = tacoRepo;
        this.assembler = assembler;
    }

    @GetMapping("/recent")
    public CollectionModel<TacoModel> recentTacos() {
        // TODO: fix range for pagination
        PageRequest page = PageRequest.of(0, 12, Sort.by("createdAt").descending());
        List<Taco> list = tacoRepo.findAll(page);
        // use assembler to convert Taco to RepresentationModel
        CollectionModel<TacoModel> models = assembler.toCollectionModel(list);
        // in addition to the links for those Taco, add "recents" which is the link for this method
        models.add(linkTo(methodOn(TacoDesignController.class).recentTacos()).withRel("recent"));
        return models;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Taco> tacoById(@PathVariable("id") Long id) {
        Optional<Taco> optTaco = tacoRepo.findById(id);
        return ResponseEntity.of(optTaco); // this will return 404 NOT FOUND if optional is not
                                           // present
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Taco postTaco(@RequestBody Taco taco) {
        return tacoRepo.save(taco);
    }
}
