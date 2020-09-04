package com.red.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "comment_images")
@Data
public class CommentImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id", referencedColumnName = "id", nullable = false)
    private Comment commentId;

    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name = "media_id", referencedColumnName = "id", nullable = false)
    private Media mediaId;

    public CommentImage(){}

}
