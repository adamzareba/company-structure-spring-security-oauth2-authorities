package com.adamzareba.spring.security.oauth2.model.security;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.Immutable;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "PERMISSION", uniqueConstraints = {@UniqueConstraint(columnNames = {"NAME"})})
@Immutable
@Getter
@Setter
public class Permission implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @ManyToMany(mappedBy = "permissions")
    @JsonIgnore
    @OrderBy
    private Set<Role> roles;
}

