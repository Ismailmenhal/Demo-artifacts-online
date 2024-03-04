package com.example.testdemo.Demo;

import com.example.testdemo.Demo.dto.DemoDto;
import com.example.testdemo.System.StatusCode;
import com.example.testdemo.artifact.Artifact;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class DemoControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    DemoService demoService ;


    @Autowired
    ObjectMapper objectMapper ;

    List<Demo> demos ;

    @BeforeEach
    void setUp() {

        Artifact a1 = new Artifact();
        a1.setId("1250808601744904191");
        a1.setName("Deluminator");
        a1.setDescription(" A Deluminator is a device ....");
        a1.setImageUrl("imageUrl");


        Artifact a2 = new Artifact();
        a2.setId("1250808601744904192");
        a2.setName("Invisbility Cloak");
        a2.setDescription(" An Invisiblity is a device ....");
        a2.setImageUrl("imageUrl");


        Artifact a3 = new Artifact();
        a3.setId("1250808601744904193");
        a3.setName("Elder");
        a3.setDescription(" A Elder is a device ....");
        a3.setImageUrl("imageUrl");

        this.demos = new ArrayList<>();
        Demo d1 = new Demo();
        d1.setId(1);
        d1.setName("Ismail");
        d1.addArtifact(a1);
        d1.addArtifact(a3);
        this.demos.add(d1);

        Demo d2 = new Demo();
        d2.setId(2);
        d2.setName("KIKa");
        d2.addArtifact(a2);
        d2.addArtifact(a3);
        this.demos.add(d2);

        Demo d3 = new Demo();
        d3.setId(3);
        d3.setName("BOOB");
        d3.addArtifact(a1);
        d3.addArtifact(a2);
        this.demos.add(d3);




    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testFindDemosByIdSuccess() throws Exception {

        //Given
        given(this.demoService.findById(1)).willReturn(this.demos.get(0));

        //When and then
        this.mockMvc.perform(get("/api/v1/demos/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find One Success"))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.name").value("Deluminator"));

    }
    @Test
    void testFindDemosByIdNotFound() throws Exception {

        //Given
        given(this.demoService.findById(1)).willThrow(new DemoNotFoundException(1));
        //When and then
        this.mockMvc.perform(get("/api/v1/demos/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find"))
                .andExpect(jsonPath("$.data").isEmpty());

    }
    @Test
    void testFindAllDemosSuccess() throws Exception {
        //given
        given(this.demoService.findAll()).willReturn(this.demos);

        // whrn and then
        this.mockMvc.perform(get("api/V1/demos").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value(" find ll Success "))
                .andExpect(jsonPath("$.data", Matchers.hasSize(this.demos.size())))
                .andExpect(jsonPath("$.data(0).id").value(" 1250808601744904191 "))
                .andExpect(jsonPath("$.data(0).name").value(" Deluminator "))
                .andExpect(jsonPath("$.data(1).id").value(" 1250808601744904191 "))
                .andExpect(jsonPath("$.data(1).name").value(" Invisibility Cloak "));

    }
    @Test
    void testAddDemosSuccess() throws Exception {
        //Given
        DemoDto demoDto = new DemoDto(null, "Remembrall "  , 1);

        String json = this.objectMapper.writeValueAsString(demoDto);

        Demo savedDemo  = new Demo();
        savedDemo.setId(2);
        savedDemo.setName("KIIKA");

        given(this.demoService.save(Mockito.any(Demo.class))).willReturn(savedDemo);


        //WHen and Then
        this.mockMvc.perform(post("api/V1/demos").contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value(" ADD Success "))
                .andExpect(jsonPath("$.data.id").isNotEmpty())
                .andExpect(jsonPath("$.data.name").value(savedDemo.getName()));

    }
    @Test
    void TestUpdateDemosSuccess() throws Exception {
        DemoDto demoDto = new DemoDto(null , "Remembrall " , 1 );
        Demo updatedDemo  = new Demo();
        updatedDemo.setId(1);
        updatedDemo.setName("Updated Ismail  ");
        String json = this.objectMapper.writeValueAsString(demoDto);


        given(this.demoService.update( eq(1) , Mockito.any(Demo.class))).willReturn(updatedDemo);


        //WHen and Then
        this.mockMvc.perform(put("api/V1/demos/1").contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value(" UPDATE Success "))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.name").value(updatedDemo.getName()));
              }
    @Test
    void testUpdateDemosErrorWithNoExistentId() throws Exception {
        DemoDto demoDto = new DemoDto(1, "Remembrall " , 4);

        String json = this.objectMapper.writeValueAsString(demoDto);

        given(this.demoService.update( eq(1) , Mockito.any(Demo.class))).willThrow(new DemoNotFoundException(2));


        //WHen and Then
        this.mockMvc.perform(put("api/V1/demos/1").contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value(" UPDATE Not Success "))
                .andExpect(jsonPath("$.data").isEmpty());


    }
    @Test
    void testDeleteDemosSuccess() throws Exception {
        doNothing().when(this.demoService).delete(1);

        this.mockMvc.perform(delete("api/V1/demos/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value(" Delete Success "))
                .andExpect(jsonPath("$.data").isEmpty());
    }
    @Test
    void testDeleteDemosErrorWhitNonExistentId() throws Exception {
        doThrow(new DemoNotFoundException(1)).when(this.demoService).delete(1);

        this.mockMvc.perform(delete("api/V1/demos /1").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value(" Delete Not  Success whit id 1250808601744904191 "))
                .andExpect(jsonPath("$.data").isEmpty());
    }


}
