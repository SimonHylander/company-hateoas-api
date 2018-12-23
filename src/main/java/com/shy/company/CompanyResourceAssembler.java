package com.shy.company;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.hateoas.core.EmbeddedWrapper;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class CompanyResourceAssembler extends ResourceAssemblerSupport<Company, CompanyResource> {

    public CompanyResourceAssembler() {
        super(CompanyController.class, CompanyResource.class);
    }

    @Override
    public CompanyResource toResource(Company company) {
        CompanyResource resource = new CompanyResource(company.getName());
        //CompanyResource resource = super.createResourceWithId(entity.getUniqueId(), entity);

        resource.add(
                linkTo(methodOn(CompanyController.class).findOne(company.getUniqueId())).withSelfRel()
        );

        //Resources<EmbeddedWrapper> persons = new Resources();

        return resource;
    }

    /*public List<CompanyResource> toResources(Iterable<? extends Company> entities) {
        return super.toResources(entities);
    }*/

    //@Override
    //public Resources<Resource<CompanyResource>> toResources(Iterable<? extends Company> entities) {
        //Assert.notNull(entities, "Entities must not be null!");
        /*List<Resource<T>> result = new ArrayList<Resource<T>>();
        for (CompanyResource entity : entities) {
            result.add(toResource(entity));
        }
        Resources<Resource<T>> resources = new Resources<>(result);
        addLinks(resources);
        return resources;*/
        //return null;
        //return super.toResources(entities);
    //}
}