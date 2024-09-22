package com.samuelcharles.docker_manager.controller;

import com.github.dockerjava.api.model.Image;
import com.samuelcharles.docker_manager.service.DockerManagerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("docker/images")
public class DockerImagesController {
    private final DockerManagerService dockerManagerService;

    @GetMapping()
    public ResponseEntity<List<Image>> getAllImages() {
        return ResponseEntity.ok(dockerManagerService.listImages());
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Image>> getAllImagesByName(@RequestParam String imageName) {
        return ResponseEntity.ok(dockerManagerService.listImageByName(imageName));
    }
}
