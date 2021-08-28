package com.dfedorino.wm.actions;


import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ActionModelAssembler implements RepresentationModelAssembler<Action, EntityModel<Action>> {

    @Override
    public EntityModel<Action> toModel(Action action) {
        switch (action.getName()) {
            case "drain":
                return  EntityModel.of(action,
                        linkTo(methodOn(ActionsApi.class).postDrain()).withSelfRel(),
                        linkTo(methodOn(ActionsApi.class).getActions()).withRel("actions"));
            case "unlock":
                return  EntityModel.of(action,
                        linkTo(methodOn(ActionsApi.class).postUnlock()).withSelfRel(),
                        linkTo(methodOn(ActionsApi.class).getActions()).withRel("actions"));
            case "run":
                return  EntityModel.of(action,
                        linkTo(methodOn(ActionsApi.class).postRun(null)).withSelfRel(),
                        linkTo(methodOn(ActionsApi.class).getActions()).withRel("actions"));
        }
        throw new IllegalArgumentException();
    }

    public CollectionModel<EntityModel<Action>> toCollection(List<Action> actions) {
        return CollectionModel.of(this.toCollectionModel(actions), linkTo(methodOn(ActionsApi.class).getActions()).withSelfRel());
    }
}
