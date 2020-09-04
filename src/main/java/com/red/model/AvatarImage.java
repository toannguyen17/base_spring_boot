package com.red.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "avatar_images")
@Data
public class AvatarImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User userId;

    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name = "media_id", referencedColumnName = "id", nullable = false)
    private Media mediaId;

    public AvatarImage() {
    }
}
