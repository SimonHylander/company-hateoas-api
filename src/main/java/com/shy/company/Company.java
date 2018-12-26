package com.shy.company;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.hateoas.ResourceSupport;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "company")
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class Company extends ResourceSupport {
    @Id
    @GeneratedValue(generator = "uuid4")
    @GenericGenerator(name = "uuid4", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "unique_id")
    private UUID uniqueId;

    @NonNull
    @Column(name = "organisation_number")
    private String organisationNumber;

    @Column(name = "name")
    @NonNull
    private String name;

    @Column(name = "description")
    @NonNull
    private String description;
}