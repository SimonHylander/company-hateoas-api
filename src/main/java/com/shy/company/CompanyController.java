package com.shy.company;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.hateoas.*;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    private CompanyRepository repository;

    @Autowired
    private CompanyService service;

    @Autowired
    private CompanyResourceAssembler assembler;

    //ResponseEntity<Resources<Resource<Company>>>
    /*
    @GetMapping("/all")
    public ResponseEntity<Resources<Resource<CompanyResource>>> all(Pageable pageable) {
        List<CompanyResource> r = assembler.toResources(repository.findAll());
        System.out.println(r);
        Link link = new Link("http://example.com/products/");

        //Resources<Resource<CompanyResource>> resources = new Resources<>(r, link);
        Resources<Resource<CompanyResource>> resources = new Resources<>(Arrays.asList(new CompanyResource("Apfelstrudel"), new CompanyResource("Apfelstrudel")), link);
        return ResponseEntity.ok(resources);
    }*/

    //https://www.logicbig.com/tutorials/spring-framework/spring-hateoas/multiple-link-relations.html
    //use only one company object (no resource) ?

    /*
    https://github.com/spring-projects/spring-hateoas-examples/blob/master/api-evolution/original-server/src/main/java/org/springframework/hateoas/examples/EmployeeController.java
    @GetMapping(value = "/", produces = MediaTypes.HAL_JSON_VALUE)
    public ResourceSupport root() {

        ResourceSupport rootResource = new ResourceSupport();

        rootResource.add(
                linkTo(methodOn(CompanyController.class).root()).withSelfRel(),
                linkTo(methodOn(CompanyController.class).findAll()).withRel("employees"));

        return rootResource;
    }*/

    @GetMapping("/")
    public ResponseEntity<Resources<Resource<CompanyResource>>> findAll() {
        CompanyResource p1 = new CompanyResource("Apfelstrudel");
        CompanyResource p2 = new CompanyResource("Schnitzel");

        Resource<CompanyResource> r1 = new Resource<>(p1, new Link("http://example.com/products/1"));
        Resource<CompanyResource> r2 = new Resource<>(p2, new Link("http://example.com/products/2"));

        //List<CompanyResource> companyResources = assembler.toResources(repository.findAll());
        //List<Resource<CompanyResource>> e = repository.findAll().stream().collect(Collectors.toList());

        Link link = new Link("http://example.com/products/");
        Resources<Resource<CompanyResource>> resources = new Resources<>(Arrays.asList(r1, r2), link);

        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyResource> findOne(@PathVariable UUID id) {
        return repository.findById(id)
                .map(assembler::toResource)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Returns hateoas Resource object.
     * This version doesn't require an entity resource object.
     */
    @GetMapping("/v2/{id}")
    public Resource<Company> findOneTwo(@PathVariable UUID id) {
        Resource<Company> resource = new Resource<>(service.findById(id));

        resource.add(
                linkTo(methodOn(CompanyController.class).findOne(id)).withSelfRel()
        );

        return resource;
    }

    @PostMapping("/")
    public ResponseEntity<CompanyResource> newCompany(@RequestBody Company company) {
        Company savedCompany = repository.save(company);

        return ResponseEntity
                .created(linkTo(methodOn(CompanyController.class).findOne(savedCompany.getUniqueId())).toUri())
                .body(assembler.toResource(savedCompany));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompanyResource> updateCompany(@PathVariable UUID id, @RequestBody Company company) {
        Company updatedCompany = repository.save(company);
        return ResponseEntity.ok(assembler.toResource(service.findById(id)));
    }
}