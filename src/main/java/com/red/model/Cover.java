package com.red.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "covers")
@Data
public class Cover {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = {CascadeType.DETACH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "cover_id", referencedColumnName = "id")
    private CoverImage coverId;

    @OneToOne(cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, unique = true)
    private User userId;

    public Cover(){}
}
