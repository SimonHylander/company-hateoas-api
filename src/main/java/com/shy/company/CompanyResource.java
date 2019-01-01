package com.shy.company;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

@Data
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Relation(value = "company", collectionRelation = "companies")
public class CompanyResource extends ResourceSupport {

    private String uniqueId;

    @NonNull
    private String organisationNumber;

    @NonNull
    private String name;

    @NonNull
    private String description;

    @Override
    public void add(Link link) {
        super.add(link);
    }
}