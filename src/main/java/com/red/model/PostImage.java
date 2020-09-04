package com.red.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "post_images")
@Data
public class PostImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", referencedColumnName = "id", nullable = false)
    private Post postId;

    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name = "media_id", referencedColumnName = "id", nullable = false)
    private Media mediaId;

    public PostImage(){}

}
