package com.shy.company;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Component
class CompanyResourceAssembler extends ResourceAssemblerSupport<Company, CompanyResource> {

    public CompanyResourceAssembler() {
        super(CompanyController.class, CompanyResource.class);
    }

    @Override
    public CompanyResource toResource(Company entity) {
        CompanyResource resource = new CompanyResource(entity.getName());
        //CompanyResource resource = super.createResourceWithId(entity.getUniqueId(), entity);
        resource.add(linkTo(CompanyController.class).withRel("self"));
        //Link(String href) {}
        //Link(String href, String rel) {}
        //Link(UriTemplate template, String rel) {}
        return resource;
    }

    public List<CompanyResource> toResources(Iterable<? extends Company> entities) {
        return super.toResources(entities);
    }

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