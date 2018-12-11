package com.netflix.assignment.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author bhavana.k on 10/12/18.
 */
@Data
@Entity
@Table(name = "price")
@Access(AccessType.FIELD)
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    private String country;

    @NotNull
    @Column(name = "plan")
    @Enumerated(EnumType.STRING)
    private ServicePlan plan;

    @NotNull
    private Double price;

    @NotNull
    private String currency;
}
