package com.example.testdemo.artifact;

import com.example.testdemo.Demo.Converter.ArtifactDtoToArtifactConverter;
import com.example.testdemo.System.Result;
import com.example.testdemo.System.StatusCode;
import com.example.testdemo.artifact.Converter.ArtifactToArtifactDtoConverter;
import com.example.testdemo.artifact.dto.ArtifactDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.ResolutionSyntax;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/artifacts")


public class Controller {
    private final  Service artifactService;
    private  final ArtifactToArtifactDtoConverter artifactToArtifactDtoConverter ;
    private final ArtifactDtoToArtifactConverter artifactDtoToArtifactConverter;

    public Controller(Service artifactService, ArtifactToArtifactDtoConverter artifactToArtifactDtoConverter, ArtifactDtoToArtifactConverter artifactDtoToArtifactConverter) {
        this.artifactService = artifactService;
        this.artifactToArtifactDtoConverter = artifactToArtifactDtoConverter;
        this.artifactDtoToArtifactConverter = artifactDtoToArtifactConverter;
    }

    @GetMapping("/{artifactID}")
    public Result findArtifactById (@PathVariable String artifactID){
        Artifact foundArtifact = this.artifactService.findById(artifactID);
        ArtifactDto artifactDto = this.artifactToArtifactDtoConverter.convert(foundArtifact);
        return new Result(  true , (long) StatusCode.SUCCESS,  "Find One Success " , artifactDto );

    }

    @GetMapping
    public Result findAllArtifacts(){
        this.artifactService.findAll();
        List<Artifact> foundArtifacts = this.artifactService.findAll();
        // Convert
        List<ArtifactDto> artifactDtos=foundArtifacts.stream().map(this.artifactToArtifactDtoConverter::convert).collect(Collectors.toList());
        return new Result(true , (long) StatusCode.SUCCESS, "Find All Success" , artifactDtos) ;

    }
    @PostMapping
    public Result addArtifact(@Valid @RequestBody ArtifactDto artifactDto){


       Artifact newArtifact =  this.artifactDtoToArtifactConverter.convert(artifactDto);
       Artifact savedArtifact = this.artifactService.save(newArtifact);
       ArtifactDto savedArtifactDto = this.artifactToArtifactDtoConverter.convert(savedArtifact);
        return new Result(true , (long) StatusCode.SUCCESS, "Add Siccess " , savedArtifactDto);
    }

    @PostMapping("/{artifactId}")
    public Result updateArtifact(@PathVariable String artifactId , @RequestBody ArtifactDto artifactDto){

     Artifact update =    this.artifactDtoToArtifactConverter.convert(artifactDto);
     Artifact updatedArtifact = this.artifactService.update(artifactId , update);
     ArtifactDto updatedArtifactDto =   this.artifactToArtifactDtoConverter.convert(updatedArtifact);
        return new Result(true , (long) StatusCode.SUCCESS , "Update Success ", updatedArtifactDto) ;
    }

    @DeleteMapping("/{artifactId}")
        public Result deleteArtifact(@PathVariable  String artifactId ){
        this.artifactService.delete(artifactId);
        return new Result(true , (long) StatusCode.SUCCESS, "Delete Success ") ;
        }
    }

