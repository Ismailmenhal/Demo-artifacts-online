package com.example.testdemo.Demo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class DemoServiceTest {


    @Mock
    DemoRepository demoRepository;

    @InjectMocks
    DemoService demoService;

    List<Demo> demos ;

    @BeforeEach
    void setUp() {

        Demo d1 = new Demo();
        d1.setId(1);
        d1.setName("Ismail");

        Demo d2 = new Demo();
        d2.setId(2);
        d2.setName("KIKa");

        Demo d3 = new Demo();
        d1.setId(3);
        d1.setName("BOOB");

        this.demos = new ArrayList<>();
        this.demos.add(d1);
        this.demos.add(d2);
        this.demos.add(d3);

    }

    @AfterEach
    void tearDown() {
    }
    @Test
    void testFindAllSuccess(){
        //Given
        given(this.demoRepository.findAll()).willReturn(this.demos);



        //When
        List<Demo> actualDemos = this.demoService.findAll();

        //Then
        assertThat(actualDemos.size()).isEqualTo(this.demos.size());
        verify(demoRepository, times(1)).findAll();

    }
    @Test
    void testfindByIdSuccess() {

        //given
        Demo d = new Demo();
        d.setId(1);
        d.setName("Ismail ");

        given(this.demoRepository.findById(1)).willReturn(Optional.of(d));
        //when
        Demo returnedDemo = this.demoService.findById(1);

        //then
        assertThat(returnedDemo.getId()).isEqualTo(d.getId());
        assertThat(returnedDemo.getName()).isEqualTo(d.getName());
        verify(this.demoRepository, times(1)).findById(1);

    }

    @Test
    void testFindByIdNotFound(){
        //Given
        given(this.demoRepository.findById(Mockito.any(Integer.class))).willReturn(Optional.empty() );

        //When
        Throwable thrown = catchThrowable( ()->{
            Demo returnedDemo = this.demoService.findById(1);
        } );

        //Then
        assertThat(thrown)
                .isInstanceOf(DemoNotFoundException.class)
                .hasMessage("Could not find artifact whit id 1 ");
        verify(this.demoRepository, times(1)).findById(1);

    }
    @Test
    void testSaveSuccess(){
        Demo newDemo = new Demo();
        newDemo.setName("BOOB 3 ");

        given(this.demoRepository.save(newDemo)).willReturn(newDemo);

        //when
        Demo returnedDemo = this.demoService.save(newDemo);
        //Then

        assertThat(returnedDemo.getName()).isEqualTo(newDemo.getName());
        verify(this.demoRepository, times(1)).save(newDemo);


    }



    @Test
    void TestUpdateSuccess(){
        //given
        Demo oldDemo   = new Demo();
        oldDemo.setId(1);
        oldDemo.setName(" Ismail ");

        Demo update = new Demo();
        update.setName("ismail  updated ");

        given(this.demoRepository.findById(1)).willReturn(Optional.of(oldDemo));
        given(this.demoRepository.save(oldDemo)).willReturn(oldDemo);

        //when

        Demo updateArtifact = this.demoService.update(1 , update);
        //then
        assertThat(updateArtifact.getId()).isEqualTo(1);
        assertThat(updateArtifact.getName()).isEqualTo(update.getName());
        verify(this.demoRepository, times(1)).findById(1);
        verify(this.demoRepository, times(1)).save(oldDemo);


    }

    @Test
    void TestUpdateNotFound(){
        Demo update   = new Demo();
        update.setId(1);
        update.setName(" Ismail updated ");
        given(this.demoRepository.findById(1)).willReturn(Optional.empty());

        //When
        assertThrows(DemoNotFoundException.class, ()->{
            this.demoService.update(1 , update );

            //then
            verify(this.demoRepository, times(1)).findById(1);

        });

    }
    @Test
    void testDeleteSuccess(){
        //given
        Demo demo   = new Demo();
        demo.setId(1);
        demo.setName(" Ismail ");
        given(this.demoRepository.findById(1)).willReturn(Optional.of(demo));
        doNothing().when(this.demoRepository).deleteById(1);

        //When
        this.demoService.delete(1);
        //Then
        verify(this.demoRepository, times(1)).deleteById(1);

    }
    @Test
    void testDeleteNotFound(){
        //given
        given(this.demoRepository.findById(1)).willReturn(Optional.empty());

        //When
        assertThrows(DemoNotFoundException.class , () -> {
            this.demoService.delete(1);
        });

        //Then
        verify(this.demoRepository, times(1)).findById(1);

    }
}
