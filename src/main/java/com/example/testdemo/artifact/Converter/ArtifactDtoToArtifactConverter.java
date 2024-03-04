package com.example.testdemo.artifact.Converter;

import com.example.testdemo.artifact.Artifact;
import com.example.testdemo.artifact.dto.ArtifactDto;
import org.springframework.core.convert.converter.Converter;

public class ArtifactDtoToArtifactConverter implements Converter<ArtifactDto , Artifact> {
    @Override
    public Artifact convert(ArtifactDto source) {
        Artifact artifact = new Artifact();
        artifact.setId(source.id());
        artifact.setName(source.name());
        artifact.setDescription(source.description());
        artifact.setImageUrl(source.imageUrl());

        return artifact;
    }


}
