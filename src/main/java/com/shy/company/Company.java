package com.shy.company;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Table(name = "company")
@Getter
@NoArgsConstructor
@RequiredArgsConstructor
public class Company {
    @Id
    @GeneratedValue(generator = "uuid4")
    @GenericGenerator(name = "uuid4", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "unique_id")
    private UUID uniqueId;

    @NonNull
    @Column(name = "name")
    @NotNull
    private String name;
}