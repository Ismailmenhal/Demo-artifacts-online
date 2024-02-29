package com.example.testdemo.artifact.Converter;

import com.example.testdemo.Demo.Converter.DemoToDemoDtoConverter;
import com.example.testdemo.artifact.Artifact;
import com.example.testdemo.artifact.dto.ArtifactDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component

public class ArtifactToArtifactDtoConverter implements Converter<Artifact , ArtifactDto> {


    private final DemoToDemoDtoConverter demoToDemoDtoConverter ;

    public ArtifactToArtifactDtoConverter(DemoToDemoDtoConverter demoToDemoDtoConverter) {
        this.demoToDemoDtoConverter = demoToDemoDtoConverter;
    }

    @Override
    public ArtifactDto convert(Artifact source) {
        ArtifactDto artifactDto = new ArtifactDto(source.getId(), source.getName(), source.getDescription(), source.getImageUrl()
                , source.getOwner() != null ? this.demoToDemoDtoConverter.convert(source.getOwner()):null);
        return artifactDto;
    }
}
