package com.shy.company;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

@Getter
public class CompanyResource extends ResourceSupport {

    private String name;

    @JsonCreator
    public CompanyResource(@JsonProperty("company") String name) {
        this.name = name;
    }

    @Override
    public void add(Link link) {
        super.add(link);
    }
}