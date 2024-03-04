package com.example.testdemo.Demo;

import com.example.testdemo.Demo.Converter.DemoDtoToDemoConverter;
import com.example.testdemo.Demo.Converter.DemoToDemoDtoConverter;
import com.example.testdemo.Demo.dto.DemoDto;
import com.example.testdemo.System.Result;
import com.example.testdemo.System.StatusCode;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.endpoint.base-url}/demos")

public class DemoController {
    private final  DemoService demoService;
    private  final DemoToDemoDtoConverter demoToDemoDtoConverter;
    private  final DemoDtoToDemoConverter demoDtoToDemoConverter ;

    public DemoController(DemoService demoService, DemoToDemoDtoConverter demoToDemoDtoConverter, DemoDtoToDemoConverter demoDtoToDemoConverter) {
        this.demoService = demoService;
        this.demoToDemoDtoConverter = demoToDemoDtoConverter;
        this.demoDtoToDemoConverter = demoDtoToDemoConverter;
    }

    @GetMapping
    public Result findAllDemos(){
        List<Demo> foundDemos = this.demoService.findAll();
        List<DemoDto> demoDtos = foundDemos.stream()
                .map(this.demoToDemoDtoConverter::convert)
                .collect(Collectors.toList());
        return new Result(true , (long) StatusCode.SUCCESS, " Find one Success " , demoDtos );
    }
    @GetMapping("/{demoId}")
    public Result findDemoById(@PathVariable Integer demoId){
        Demo foundDemo = this.demoService.findById(demoId);
        DemoDto demoDto = this.demoToDemoDtoConverter.convert(foundDemo);
        return new Result(true , (long) StatusCode.SUCCESS, " Find one Success " , demoDto );

    }
    @PostMapping("/demoId")
    public Result addDemo (@Valid @RequestBody DemoDto demoDto ){
        Demo newDemo = this.demoDtoToDemoConverter.convert(demoDto);
        Demo savedDemo = this.demoService.save(newDemo);
        DemoDto savedDemoDto = this.demoToDemoDtoConverter.convert(savedDemo);
        return new Result(true , (long) StatusCode.SUCCESS, " Add Success Success " , savedDemoDto );

    }
    @PostMapping("/demoId")
    public Result updateDemo(@PathVariable Integer demoId , @RequestBody DemoDto demoDto){
        Demo update = this.demoDtoToDemoConverter.convert(demoDto);
        Demo updatedDemo = this.demoService.update(demoId , update);
        DemoDto updateDemoDto =this.demoToDemoDtoConverter.convert( updatedDemo);
        return new Result(true , (long) StatusCode.SUCCESS, " Update  Success " , updateDemoDto );

    }
    @DeleteMapping("/{demoId}")
    public Result deleteDemo(@PathVariable Integer demoId){
        this.demoService.delete(demoId);
        return new Result(true , (long) StatusCode.SUCCESS, " Delete  Success " );

    }

    @PutMapping("/{demoId}/artifacts/{artifactId}")
    public Result assignArtifact(@PathVariable Integer demoId ,@PathVariable String artifactId ){
        this.demoService.assignArtifact(demoId , artifactId);
        return new Result(true , (long) StatusCode.SUCCESS, " Artifact Assignment  Success " );
    }
}
