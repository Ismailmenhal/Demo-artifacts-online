package com.example.testdemo.Demo;

import com.example.testdemo.artifact.Artifact;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Demo implements Serializable {
    @Id
    private Integer id ;
    private String name ;
    @OneToMany( cascade = { CascadeType.PERSIST, CascadeType.MERGE } ,    mappedBy = "owner")
    private List<Artifact> artfacts = new ArrayList<>();

    public Demo(){}

    public List<Artifact> getArtifacts(){
        return  artfacts;
    }

    public void setArtfacts(List<Artifact> artfacts) {
        this.artfacts = artfacts;
    }

    public void addArtifact(Artifact artifact) {
        artifact.setOwner(this);
        this.artfacts.add(artifact);
    }

    public Integer getNumberOfArtifacts() {
        return this.artfacts.size();
    }
}
