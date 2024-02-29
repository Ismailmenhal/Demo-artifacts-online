package com.example.testdemo.artifact;

import com.example.testdemo.Demo.Demo;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity

public class Artifact implements Serializable {
    @Id
    private String id ;
    private String name ;
    private String description ;
    private String imageUrl ;


    @ManyToOne
    private Demo owner ;

}
