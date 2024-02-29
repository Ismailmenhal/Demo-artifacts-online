package com.example.testdemo.artifact.dto;

import com.example.testdemo.Demo.dto.DemoDto;
import jakarta.validation.constraints.NotEmpty;

public record ArtifactDto(String id , @NotEmpty(message = "name is required " ) String name
        , @NotEmpty(message = "Description is required ")  String description
        , @NotEmpty(message = "ImageUrl is required ") String imageUrl , DemoDto owner ){
}
