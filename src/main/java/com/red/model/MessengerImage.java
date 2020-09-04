package com.red.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "messenger_images")
@Data
public class MessengerImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "messenger_id", referencedColumnName = "id", nullable = false)
    private Messenger messengerId;

    @Column(length = 500, nullable = false)
    private String path;

    public MessengerImage(){}
}
