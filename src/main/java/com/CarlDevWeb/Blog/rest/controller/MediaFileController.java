package com.CarlDevWeb.Blog.rest.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/media/files")
public class MediaFileController {

    private static final String UPLOAD_DIR = "uploads";

    @GetMapping("/{filename}")
    public ResponseEntity<Resource> get(@PathVariable String filename) throws Exception {
        Path path = Paths.get(UPLOAD_DIR).resolve(filename).normalize();
        Resource res = new UrlResource(path.toUri());

        if (!res.exists() || !res.isReadable()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(res);
    }
}
