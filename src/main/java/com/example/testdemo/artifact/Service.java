package com.example.testdemo.artifact;


import com.example.testdemo.artifact.utils.IdWorker;
import jakarta.transaction.Transactional;

import java.util.List;

@org.springframework.stereotype.Service
@Transactional

public class Service {

    private final Repository artifactRepository;

    private final IdWorker idWorker;

    public Service(Repository artifactRepository, IdWorker idWorker) {
        this.artifactRepository = artifactRepository;
        this.idWorker = idWorker;
    }

    public Artifact findById(String artifactId) {
        return this.artifactRepository
                .findById(artifactId)
                .orElseThrow(() -> new ArtifactNotFoundException(artifactId));
    }

    public List<Artifact> findAll() {
        return this.artifactRepository.findAll();
    }


    public Artifact save(Artifact newArtifact) {
        newArtifact.setId(idWorker.nextId() + "");
        return this.artifactRepository.save(newArtifact);
    }

    public Artifact update(String artifactId, Artifact update) {
        return this.artifactRepository.findById(artifactId).map(
                        oldArtifact -> {
                            oldArtifact.setName(update.getName());
                            oldArtifact.setDescription(update.getDescription());
                            oldArtifact.setImageUrl(update.getImageUrl());
                            return this.artifactRepository.save(oldArtifact);
                        })
                .orElseThrow(() -> new ArtifactNotFoundException(artifactId));

    }

    public void delete(String artifactId){
       this.artifactRepository.findById(artifactId)
               .orElseThrow(()-> new ArtifactNotFoundException(artifactId));
        this.artifactRepository.deleteById(artifactId);

    }
}
