package com.samuelcharles.docker_manager.controller;

import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Image;
import com.samuelcharles.docker_manager.service.DockerManagerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("docker/containers")
public class DockerContainersController {
    private final DockerManagerService dockerManagerService;

    public DockerContainersController(DockerManagerService service) {
        this.dockerManagerService = service;
    }

    @GetMapping()
    public ResponseEntity<List<Container>> getAllContainersByName(@RequestParam(required = false, defaultValue = "true") boolean showAll) {
        return ResponseEntity.ok(dockerManagerService.listContainers(showAll));
    }

    @PostMapping("/{id}/start")
    public ResponseEntity<?> startContainer(@PathVariable String id){
        dockerManagerService.startContainer(id);
        return ResponseEntity.ok().build();
    }
}
