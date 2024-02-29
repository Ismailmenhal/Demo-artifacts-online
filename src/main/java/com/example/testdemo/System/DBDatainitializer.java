package com.example.testdemo.System;

import com.example.testdemo.Demo.Demo;
import com.example.testdemo.Demo.DemoRepository;
import com.example.testdemo.artifact.Artifact;
import com.example.testdemo.artifact.Repository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component

public class DBDatainitializer implements CommandLineRunner {

    private final Repository artifactRepository ;
    private final  DemoRepository demoRepository ;

    public DBDatainitializer(Repository artifactRepository, DemoRepository demoRepository) {
        this.artifactRepository = artifactRepository;
        this.demoRepository = demoRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Artifact a1 = new Artifact();
        a1.setId("1250808601744904191");
        a1.setName("Deluminator");
        a1.setDescription(" A Deluminator is a device ....");
        a1.setImageUrl("imageUrl");



        Artifact a2 = new Artifact();
        a2.setId("1250808601744904192");
        a2.setName("Invisbility Cloak");
        a2.setDescription(" An Invisiblity is a device ....");
        a2.setImageUrl("imageUrl");


        Artifact a3 = new Artifact();
        a3.setId("1250808601744904193");
        a3.setName("Elder");
        a3.setDescription(" A Elder is a device ....");
        a3.setImageUrl("imageUrl");


        Demo d1 = new Demo();
        d1.setId(1);
        d1.setName("Deomo");
        d1.addArtifact(a1);
        d1.addArtifact(a3);

        Demo d2 = new Demo();
        d2.setId(1);
        d2.setName("Deomo2");
        d1.addArtifact(a2);
        d1.addArtifact(a1);



        demoRepository.save(d1);
        demoRepository.save(d2);


        artifactRepository.save(a3);

    }
}
