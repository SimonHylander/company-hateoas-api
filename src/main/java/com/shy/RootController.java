package com.shy;

import com.shy.company.CompanyController;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.ResourceSupport;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController implements ErrorController {

    @GetMapping(value = "/", produces = MediaTypes.HAL_JSON_VALUE)
    public ResourceSupport root() {
        ResourceSupport resource = new ResourceSupport();

        resource.add(
            linkTo(methodOn(RootController.class).root()).withSelfRel(),
            linkTo(methodOn(CompanyController.class).findAll()).withRel("companies")
        );

        return resource;
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}