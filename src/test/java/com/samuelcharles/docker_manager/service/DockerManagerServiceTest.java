package com.samuelcharles.docker_manager.service;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.ListContainersCmd;
import com.github.dockerjava.api.command.ListImagesCmd;
import com.github.dockerjava.api.exception.NotFoundException;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Image;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DockerManagerServiceTest {

    @Mock
    private DockerClient dockerClient;

    @Mock
    private ListContainersCmd listContainersCmd;

    @Mock
    private ListImagesCmd listImagesCmd;

    @InjectMocks
    private DockerManagerService dockerManagerService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Testa se os constainers são mostrados com showAll = true")
    public void listContainers( ){
        List<Container> mockcContainers = Collections.emptyList();
        when(dockerClient.listContainersCmd()).thenReturn(listContainersCmd);
        when(listContainersCmd.withShowAll(true)).thenReturn(listContainersCmd);
        when(listContainersCmd.exec()).thenReturn(mockcContainers);

        List<Container> result =  dockerManagerService.listContainers(true);

        assertEquals(mockcContainers, result);
        verify(dockerClient).listContainersCmd();
        verify(listContainersCmd).withShowAll(true);
        verify(listContainersCmd).exec();

    }

    @Test
    @DisplayName("Testa se os constainers em execução são mostrados com showAll = false")
    public void listOnlyContainersRunning( ){
        List<Container> mockcContainers = Collections.emptyList();
        when(dockerClient.listContainersCmd()).thenReturn(listContainersCmd);
        when(listContainersCmd.withShowAll(false)).thenReturn(listContainersCmd);
        when(listContainersCmd.exec()).thenReturn(mockcContainers);

        List<Container> result =  dockerManagerService.listContainers(false);

        assertEquals(mockcContainers, result);
        verify(dockerClient).listContainersCmd();
        verify(listContainersCmd).withShowAll(false);
        verify(listContainersCmd).exec();

    }

    @Test
    @DisplayName("Deve retornar a imagem a partir de um nome")
    public void listImageByName(){
        String imageName = "ImageTest";
        List<Image> images = Collections.emptyList();
        when(dockerClient.listImagesCmd()).thenReturn(listImagesCmd);
        when(listImagesCmd.withImageNameFilter(imageName)).thenReturn(listImagesCmd);
        when(listImagesCmd.exec()).thenReturn(images);

        List<Image> result = dockerManagerService.listImageByName(imageName);

        assertEquals(images, result);
        verify(dockerClient).listImagesCmd();
        verify(listImagesCmd).withImageNameFilter(imageName);
        verify(listImagesCmd).exec();

    }

    @Test
    @DisplayName("Deve retornar uma exceção do ao não ter uma imagem com o nome recebido")
    public void listImageByNameException(){

        when(dockerClient.listImagesCmd()).thenReturn(listImagesCmd);
        when(listImagesCmd.withImageNameFilter("teste")).thenReturn(listImagesCmd);
        when(listImagesCmd.exec()).thenThrow(new NotFoundException("Not founded"));

        assertThrows(NotFoundException.class, () ->
                dockerManagerService.listImageByName("teste"));

        verify(dockerClient).listImagesCmd();
        verify(listImagesCmd).withImageNameFilter("teste");
        verify(listImagesCmd).exec();

    }
}