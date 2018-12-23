package com.shy.person;

import com.shy.company.CompanyResource;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.UUID;

@RestController
@RequestMapping("/persons")
public class PersonController {

    @GetMapping("/")
    public Resources<Resource<CompanyResource>> findAll() {
        CompanyResource p1 = new CompanyResource("Apfelstrudel");
        CompanyResource p2 = new CompanyResource("Schnitzel");

        Resource<CompanyResource> r1 = new Resource<>(p1, new Link("http://example.com/products/1"));
        Resource<CompanyResource> r2 = new Resource<>(p2, new Link("http://example.com/products/2"));

        Link link = new Link("http://example.com/products/");
        Resources<Resource<CompanyResource>> resources = new Resources<>(Arrays.asList(r1, r2), link);

        return resources;
    }
}