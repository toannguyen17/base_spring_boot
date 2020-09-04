package com.red.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "medias")
@Data
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User userId;

    @Column(length = 500)
    private String path;

    public Media(){}
}
