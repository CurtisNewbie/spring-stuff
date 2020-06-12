package com.curtisnewbie.model;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import com.curtisnewbie.controllers.TacoDesignController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class TacoModelAssembler extends RepresentationModelAssemblerSupport<Taco, TacoModel> {

    public TacoModelAssembler() {
        super(TacoDesignController.class, TacoModel.class);
    }

    @Override
    public TacoModel toModel(Taco taco) {
        // create a RepresentationModel for Taco (which is essentially a DTO with mechanism to
        // manage links)
        TacoModel trm = new TacoModel(taco);
        // add a link for itself
        trm.add(linkToSelf(taco));
        return trm;
    }

    public static Link linkToSelf(Taco taco) {
        // structure is essentially { "rel" : "link"}
        return linkTo(methodOn(TacoDesignController.class).tacoById(taco.getId())).withSelfRel();
    }

    public CollectionModel<TacoModel> toModels(Collection<Taco> tacos) {
        List<TacoModel> list = new ArrayList<>();
        tacos.forEach(taco -> {
            list.add(this.toModel(taco));
        });
        return CollectionModel.of(list);
    }
}
