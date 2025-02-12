package com.CarlDevWeb.Blog.mapper;

import com.CarlDevWeb.Blog.dto.MediaDto;
import com.CarlDevWeb.Blog.entity.Media;
import org.springframework.stereotype.Component;

@Component
public class MediaMapper {

    public MediaDto toDto(Media media) {
        if (media == null) {
            return null;
        }

        MediaDto mediaDto = new MediaDto();

        mediaDto.setId(media.getId());
        mediaDto.setUrl(media.getUrl());
        mediaDto.setType(media.getType());
        mediaDto.setArticle(media.getArticle());

        return mediaDto;
    }

    public Media toEntity(MediaDto mediaDto) {
        if (mediaDto == null) {
            return null;
        }

        Media media = new Media();

        media.setId(mediaDto.getId());
        media.setType(mediaDto.getType());
        media.setUrl(mediaDto.getUrl());
        media.setArticle(mediaDto.getArticle());

        return media;
    }
}
