package com.example.testdemo.artifact;

import com.example.testdemo.System.Exception.ObjectNotFoundException;
import com.example.testdemo.System.StatusCode;
import com.example.testdemo.artifact.dto.ArtifactDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest
@AutoConfigureMockMvc
class ControllerTest {


    @Autowired
    MockMvc mockMvc;

    @MockBean
    Service artifactServcice;


    @Autowired
    ObjectMapper objectMapper;

    List<Artifact> artifacts;

    @Value("${api.endpoint.base-url}")
    String baseUrl;
    @BeforeEach
    void setUp() {
        this.artifacts = new ArrayList<>();

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


        Artifact a3 = new Artifact();
        a3.setId("1250808601744904193");
        a3.setName("Elder");
        a3.setDescription(" A Elder is a device ....");
        a3.setImageUrl("imageUrl");
        this.artifacts.add(a3);



    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testFindArtifactByIdSuccess() throws Exception {

        //Given
        given(this.artifactServcice.findById("1250808601744904191")).willReturn(this.artifacts.get(0));

        //When and then
        this.mockMvc.perform(get(this.baseUrl+"/artifacts/1250808601744904191").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find One Success"))
                .andExpect(jsonPath("$.data.id").value("1250808601744904191"))
                .andExpect(jsonPath("$.data.name").value("Deluminator"));

    }
    @Test
    void testFindArtifactByIdNotFound() throws Exception {

        //Given
        given(this.artifactServcice.findById("1250808601744904191")).willThrow(new ObjectNotFoundException("1250808601744904191"));

        //When and then
        this.mockMvc.perform(get("/api/v1/artifacts/1250808601744904191").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find"))
                .andExpect(jsonPath("$.data").isEmpty());

    }
    @Test
    void testFindAllArtifactSuccess() throws Exception {
        //given
        given(this.artifactServcice.findAll()).willReturn(this.artifacts);

        // whrn and then
        this.mockMvc.perform(get("api/V1/artifacts").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value(" find ll Success "))
                .andExpect(jsonPath("$.data", Matchers.hasSize(this.artifacts.size())))
                .andExpect(jsonPath("$.data(0).id").value(" 1250808601744904191 "))
                .andExpect(jsonPath("$.data(0).name").value(" Deluminator "))
                .andExpect(jsonPath("$.data(1).id").value(" 1250808601744904191 "))
                .andExpect(jsonPath("$.data(1).name").value(" Invisibility Cloak "));

    }
    @Test
    void testAddArtifactSuccess() throws Exception {
        //Given
        ArtifactDto artifactDto = new ArtifactDto(null, "Remembrall " , "XXXXXXX" , "ImageUrl" , null);

        String json = this.objectMapper.writeValueAsString(artifactDto);

        Artifact savedArtifact = new Artifact();
        savedArtifact.setId("1250808601744904191");
        savedArtifact.setName("Deluminator");
        savedArtifact.setDescription("XXXXXXX");
        savedArtifact.setImageUrl("ImageUrl");

        given(this.artifactServcice.save(Mockito.any(Artifact.class))).willReturn(savedArtifact);


        //WHen and Then
        this.mockMvc.perform(post("api/V1/artifacts").contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value(" ADD Success "))
                .andExpect(jsonPath("$.data.id").isNotEmpty())
                .andExpect(jsonPath("$.data.name").value(savedArtifact.getName()))
                .andExpect(jsonPath("$.data.description").value(savedArtifact.getDescription()))
                .andExpect(jsonPath("$.data.imageUrl").value(savedArtifact.getImageUrl()));


    }
    @Test
    void TestUpdateArtifactSuccess() throws Exception {
        ArtifactDto artifactDto = new ArtifactDto("1250808601744904191", "Remembrall " , "XXXXXXX" , "ImageUrl" , null);

        String json = this.objectMapper.writeValueAsString(artifactDto);

        Artifact updatedArtifact = new Artifact();
        updatedArtifact.setId("1250808601744904191");
        updatedArtifact.setName("Invalide Cloak ");
        updatedArtifact.setDescription(" XGDEGDIUEHIUDHIUH ");
        updatedArtifact.setImageUrl("imageURl");

        given(this.artifactServcice.update( eq("1250808601744904191") , Mockito.any(Artifact.class))).willReturn(updatedArtifact);


        //WHen and Then
        this.mockMvc.perform(put("api/V1/artifacts/1250808601744904191").contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value(" UPDATE Success "))
                .andExpect(jsonPath("$.data.id").value("1250808601744904191"))
                .andExpect(jsonPath("$.data.name").value(updatedArtifact.getName()))
                .andExpect(jsonPath("$.data.description").value(updatedArtifact.getDescription()))
                .andExpect(jsonPath("$.data.imageUrl").value(updatedArtifact.getImageUrl()));
    }
    @Test
    void testUpdateArtifactErrorWithNoExistentIf() throws Exception {
        ArtifactDto artifactDto = new ArtifactDto("1250808601744904191", "Remembrall " , "XXXXXXX" , "ImageUrl" , null);

        String json = this.objectMapper.writeValueAsString(artifactDto);

        given(this.artifactServcice.update( eq("1250808601744904191") , Mockito.any(Artifact.class))).willThrow(new ObjectNotFoundException("1250808601744904191"));


        //WHen and Then
        this.mockMvc.perform(put("api/V1/artifacts/1250808601744904191").contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value(" UPDATE Not Success "))
                .andExpect(jsonPath("$.data").isEmpty());


    }
    @Test
    void testDeleteArtifactSuccess() throws Exception {
        doNothing().when(this.artifactServcice).delete("1250808601744904191");

        this.mockMvc.perform(delete("api/V1/artifacts/1250808601744904191").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value(" Delete Success "))
                .andExpect(jsonPath("$.data").isEmpty());
    }
    @Test
    void testDeleteArtifactErrorWhitNonExistentId() throws Exception {
        doThrow(new ObjectNotFoundException("1250808601744904191")).when(this.artifactServcice).delete("1250808601744904191");

        this.mockMvc.perform(delete("api/V1/artifacts/1250808601744904191").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value(" Delete Not  Success whit id 1250808601744904191 "))
                .andExpect(jsonPath("$.data").isEmpty());
    }


}