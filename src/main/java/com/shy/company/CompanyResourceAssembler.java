package com.shy.company;

import org.springframework.hateoas.Resources;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
class CompanyResourceAssembler extends ResourceAssemblerSupport<Company, CompanyResource> {

    public CompanyResourceAssembler() {
        super(CompanyController.class, CompanyResource.class);
    }

    @Override
    public CompanyResource toResource(Company company) {
        CompanyResource resource =  new CompanyResource(
                company.getUniqueId().toString(),
                company.getOrganisationNumber(),
                company.getName(),
                company.getDescription()
        );

        resource.add(
                linkTo(methodOn(CompanyController.class).findOne(company.getUniqueId())).withSelfRel()
        );

        return resource;
    }

    public Resources<CompanyResource> toResources(List<Company> companies) {
        return new Resources<>(
                companies.stream()
                        .map(this::toResource)
                        .collect(Collectors.toList()),
                linkTo(methodOn(CompanyController.class).findAll()).withSelfRel()
        );
    }
}