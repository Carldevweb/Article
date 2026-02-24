package com.CarlDevWeb.Blog.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UploadMediaResponse {
    private String filename;
    private String url;
}
