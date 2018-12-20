package com.shy.company;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CompanyRepository repository;

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

    @GetMapping("/all")
    ResponseEntity<Resources<Resource<CompanyResource>>> all() {
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

    /*@GetMapping("/{id}")
    public ResponseEntity<Resources<Resource<Company>>> get(@RequestParam(value = "id", required = true, defaultValue = "") Long id) {
        return new ResponseEntity.ok(
                assembler.toResource(repository.findById(id));
        );
    }*/
}