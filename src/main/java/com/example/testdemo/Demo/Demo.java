package com.example.testdemo.Demo;

import com.example.testdemo.artifact.Artifact;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Demo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    public void removeAllArtifacts() {
        this.artfacts.stream().forEach(artifact -> artifact.setOwner(null));
        this.artfacts = null ;
    }

    public void removeArtifact(Artifact artifactToBeAssigned) {
        artifactToBeAssigned.setOwner(null);
        this.artfacts.remove(artifactToBeAssigned);

    }
}
