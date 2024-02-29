package com.example.testdemo.artifact;

import com.example.testdemo.Demo.Demo;
import com.example.testdemo.artifact.utils.IdWorker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

class ServiceTest {


    @Mock
    Repository artifactRepositpory;

    @Mock
    IdWorker idWorker;

    @InjectMocks
    Service artifactService;

    List<Artifact> artifacts ;

    @BeforeEach
    void setUp() {

        Artifact a1 = new Artifact();
        a1.setId("1250808601744904191");
        a1.setName("Deluminator");
        a1.setDescription(" A Deluminator is a device ....");
        a1.setImageUrl("imageUrl");
        this.artifacts.add(a1);


        Artifact a2 = new Artifact();
        a2.setId("1250808601744904192");
        a2.setName("Invisbility Cloak");
        a2.setDescription(" An Invisiblity is a device ....");
        a2.setImageUrl("imageUrl");
        this.artifacts.add(a2);
        this.artifacts = new ArrayList<>();
        this.artifacts.add(a1);
        this.artifacts.add(a2);


    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testfindByIdSuccess() {

        //given
        Artifact a = new Artifact();
        a.setId("1250808601744904192");
        a.setName("Invalide Cloak ");
        a.setDescription("An invisibility cloak is used to make the wearer invisible ");
        a.setImageUrl("imageURl");


        Demo d = new Demo();
        d.setId(2);
        d.setName("Demo Test");

        a.setOwner(d);

        given(artifactRepositpory.findById("1250808601744904192")).willReturn(of(a));


        //when
        Artifact returnedArtifact = artifactService.findById("1250808601744904192");

        //then
        assertThat(returnedArtifact.getId()).isEqualTo(a.getId());
        assertThat(returnedArtifact.getName()).isEqualTo(a.getName());
        assertThat(returnedArtifact.getDescription()).isEqualTo(a.getDescription());
        assertThat(returnedArtifact.getImageUrl()).isEqualTo(a.getImageUrl());
        verify(artifactRepositpory, times(1)).findById("1250808601744904192");


    }
    @Test
    void testFindByIdNotFound(){
        //Given
        given(artifactRepositpory.findById(Mockito.any(String.class))).willReturn(Optional.empty() );

         //When
        Throwable thrown = catchThrowable( ()->{
            Artifact returnedArtifact = artifactService.findById("1250808601744904192");
        } );

        //Then
        assertThat(thrown)
                .isInstanceOf(ArtifactNotFoundException.class)
                .hasMessage("Could not find artifact whit id : 1250808601744904192");
        verify(artifactRepositpory, times(1)).findById("1250808601744904192");

    }
    @Test
    void testFindAllSuccess(){
        //Given
        given(artifactRepositpory.findAll()).willReturn(this.artifacts);



        //When
    List<Artifact> actualArtifacts =   artifactService.findAll();

        //Then
        assertThat(actualArtifacts.size()).isEqualTo(this.artifacts.size());
        verify(artifactRepositpory, times(1)).findAll();

    }

    @Test
    void testSaveSuccess(){
        Artifact newArtifact = new Artifact();
        newArtifact.setName("Artifact 3 ");
        newArtifact.setDescription("Description.......");
        newArtifact.setImageUrl("ImageUrl......");

        given(idWorker.nextId()).willReturn(123456L);
        given(artifactRepositpory.save(newArtifact)).willReturn(newArtifact);

        //when
        Artifact savedArtifact = artifactService.save(newArtifact);
        //Then
        assertThat(savedArtifact.getId()).isEqualTo("123456");
        assertThat(savedArtifact.getName()).isEqualTo(newArtifact.getName());
        assertThat(savedArtifact.getDescription()).isEqualTo(newArtifact.getDescription());
        assertThat(savedArtifact.getImageUrl()).isEqualTo(newArtifact.getImageUrl());
        verify(artifactRepositpory, times(1)).save(newArtifact);


    }

    @Test
    void TestUpdateSuccess(){
        //given
        Artifact oldArtifact  = new Artifact();
        oldArtifact.setId("1250808601744904192");
        oldArtifact.setName("Invalide Cloak ");
        oldArtifact.setDescription("An invisibility cloak is used to make the wearer invisible ");
        oldArtifact.setImageUrl("imageURl");

        Artifact update = new Artifact();
        update.setId("1250808601744904192");
        update.setName("Invalide Cloak ");
        update.setDescription(" XGDEGDIUEHIUDHIUH ");
        update.setImageUrl("imageURl");

        given(artifactRepositpory.findById("1250808601744904192")).willReturn(Optional.of(oldArtifact));
        given(artifactRepositpory.save(oldArtifact)).willReturn(oldArtifact);

        //when

        Artifact updateArtifact = artifactService.update("1250808601744904192" , update);
        //then
        assertThat(updateArtifact.getId()).isEqualTo(update.getId());
        assertThat(updateArtifact.getDescription()).isEqualTo(update.getDescription());
        verify(artifactRepositpory, times(1)).findById("1250808601744904192");
        verify(artifactRepositpory, times(1)).save(oldArtifact);


    }

    @Test
    void TestUpdateNotFound(){
        Artifact update = new Artifact();
        update.setName("Invalide Cloak ");
        update.setDescription(" XGDEGDIUEHIUDHIUH ");
        update.setImageUrl("imageURl");
        given(artifactRepositpory.findById("1250808601744904192")).willReturn(Optional.empty());

        //When
        assertThrows(ArtifactNotFoundException.class, ()->{
            artifactService.update("1250808601744904192" , update);

            //then
            verify(artifactRepositpory, times(1)).findById("1250808601744904192");

        });

    }
    @Test
    void testDeleteSuccess(){
        //given
        Artifact artifact = new Artifact();
        artifact.setId("1250808601744904192");
        artifact.setName("Invalide Cloak ");
        artifact.setDescription("An invisibility cloak is used to make the wearer invisible ");
        artifact.setImageUrl("imageURl");
        given(artifactRepositpory.findById("1250808601744904192")).willReturn(Optional.of(artifact));
        doNothing().when(artifactRepositpory).deleteById("1250808601744904192");

        //When
        artifactService.delete("1250808601744904192");
        //Then
        verify(artifactRepositpory, times(1)).deleteById("1250808601744904192");

    }
    @Test
    void testDeleteNotFound(){
        //given
        given(artifactRepositpory.findById("1250808601744904192")).willReturn(Optional.empty());

        //When
       assertThrows(ArtifactNotFoundException.class , () -> {
           artifactService.delete("1250808601744904192");
       });

        //Then
        verify(artifactRepositpory, times(1)).findById("1250808601744904192");

    }
}