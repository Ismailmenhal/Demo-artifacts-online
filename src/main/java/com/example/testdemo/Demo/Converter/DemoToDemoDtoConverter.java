package com.example.testdemo.Demo.Converter;

import com.example.testdemo.Demo.Demo;
import com.example.testdemo.Demo.dto.DemoDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DemoToDemoDtoConverter implements Converter<Demo , DemoDto> {

    @Override
    public DemoDto convert(Demo source) {
        DemoDto demoDto = new DemoDto(source.getId(), source.getName(), source.getNumberOfArtifacts());
        return demoDto;
    }
}
