package com.example.testdemo.Demo;

import com.example.testdemo.artifact.utils.IdWorker;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Transactional
@Service
public class DemoService {
    private  final  DemoRepository demoRepository ;

    public DemoService(DemoRepository demoRepository, IdWorker idWorker) {
        this.demoRepository = demoRepository;

    }

    public Demo findById(Integer demoId) {
            return this.demoRepository
                    .findById(demoId)
                    .orElseThrow(()-> new DemoNotFoundException(demoId));
    }

    public List<Demo> findAll() {
        return this.demoRepository.findAll();
    }

    public Demo save(Demo newDemo) {
        return this.demoRepository.save(newDemo);
    }

    public Demo update(Integer demoId, Demo update) {
            return this.demoRepository.findById(demoId)
                    .map(oldDemo -> {
                            oldDemo.setName(update.getName());
        return this.demoRepository.save(oldDemo);
    })
            .orElseThrow(() -> new DemoNotFoundException(demoId));
}

    public void delete(Integer demoId) {
    Demo demoToBeDeleted = this.demoRepository.findById(demoId)
            .orElseThrow(()-> new DemoNotFoundException(demoId));
    demoToBeDeleted.removeAllArtifacts();
    this.demoRepository.deleteById(demoId);
    }
}
