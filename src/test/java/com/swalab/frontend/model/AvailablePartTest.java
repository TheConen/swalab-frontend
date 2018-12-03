package com.swalab.frontend.model;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AvailablePartTest {

    private AvailablePart testPart;
    private String name = "name";
    private String description = "This is a description.";
    private long id = 987654321;

    @Before
    public void init() {
        testPart = new AvailablePart(name, description);
        testPart.setId(id);
    }

    @Test
    public void getName() {
        assertThat(testPart.getName()).isEqualTo(name);
    }

    @Test
    public void setName() {
        assertThat(testPart.getName()).isEqualTo(name);
        String newName = "newName";
        testPart.setName(newName);
        assertThat(testPart.getName()).isEqualTo(newName);
    }

    @Test
    public void getDescription() {
        assertThat(testPart.getDescription()).isEqualTo(description);
    }

    @Test
    public void setDescription() {
        assertThat(testPart.getDescription()).isEqualTo(description);
        String newDescription = " This is an other description!!!";
        testPart.setDescription(newDescription);
        assertThat(testPart.getDescription()).isEqualTo(newDescription);
    }

    @Test
    public void getId() {
        assertThat(testPart.getId()).isEqualTo(id);
    }

    @Test
    public void setId() {
        assertThat(testPart.getId()).isEqualTo(id);
        long newId = 123456789;
        testPart.setId(newId);
        assertThat(testPart.getId()).isEqualTo(newId);
    }

}