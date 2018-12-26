package com.shy.company;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.*;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    private CompanyService service;

    @Autowired
    private CompanyRepository repository;

    @Autowired
    private CompanyResourceAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Resources<CompanyResource>> findAll() {
        return ResponseEntity.ok(assembler.toResources(repository.findAll()));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
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
    /*@GetMapping(value = "/v2/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public Resource<Company> findOneTwo(@PathVariable UUID id) {
        Resource<Company> resource = new Resource<>(service.findById(id));

        resource.add(
                linkTo(methodOn(CompanyController.class).findOne(id)).withSelfRel()
        );

        return resource;
    }*/

    @PostMapping(value = "/", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CompanyResource> newCompany(@RequestBody Company company) {
        Company savedCompany = repository.save(company);

        return ResponseEntity
                .created(linkTo(methodOn(CompanyController.class).findOne(savedCompany.getUniqueId())).toUri())
                .body(assembler.toResource(savedCompany));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CompanyResource> updateCompany(@PathVariable UUID id, @RequestBody CompanyResource companyResource) {
        Optional<Company> existingCompany = repository.findById(id);

        if(!existingCompany.isPresent())
            return ResponseEntity.notFound().build();

        existingCompany.map(company -> {
            BeanUtils.copyProperties(companyResource, company);

            return repository.save(company);
        });

        return ResponseEntity.ok(assembler.toResource(service.findById(id)));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CompanyResource> updateCompany2(@PathVariable UUID id, @RequestBody CompanyResource companyResource) {
        Optional<Company> existingCompany = repository.findById(id);

        if(!existingCompany.isPresent())
            return ResponseEntity.notFound().build();

        existingCompany.map(company -> {
            BeanUtils.copyProperties(companyResource, company);

            return repository.save(company);
        });

        return ResponseEntity.ok(assembler.toResource(service.findById(id)));
    }
}