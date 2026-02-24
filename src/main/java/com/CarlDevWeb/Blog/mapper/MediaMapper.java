package com.CarlDevWeb.Blog.mapper;

import com.CarlDevWeb.Blog.dto.MediaDto;
import com.CarlDevWeb.Blog.entity.Media;
import org.springframework.stereotype.Component;

@Component
public class MediaMapper {

    public MediaDto toDto(Media media) {
        if (media == null) return null;

        return MediaDto.builder()
                .id(media.getId())
                .url(media.getUrl())
                .type(media.getType())
                .articleId(media.getArticle() != null ? media.getArticle().getId() : null)
                .build();
    }
}
