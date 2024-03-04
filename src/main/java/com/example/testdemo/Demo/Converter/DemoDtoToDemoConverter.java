package com.example.testdemo.Demo.Converter;

import com.example.testdemo.Demo.Demo;
import com.example.testdemo.Demo.dto.DemoDto;
import com.example.testdemo.artifact.Artifact;
import com.example.testdemo.artifact.dto.ArtifactDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.awt.*;

@Component
public class DemoDtoToDemoConverter implements Converter<DemoDto , Demo> {

    @Override
    public Demo convert(DemoDto source){
        Demo demo = new Demo() ;
        demo.setId(source.id());
        demo.setName(source.name());
        return demo ;
    }

    public Artifact convert(ArtifactDto artifactDto) {
            return null;
    }
}
