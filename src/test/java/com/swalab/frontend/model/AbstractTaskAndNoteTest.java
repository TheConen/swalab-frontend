package com.swalab.frontend.model;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AbstractTaskAndNoteTest {

    private AbstractTaskAndNote testNote;
    private String title = "title";
    private String description = "This is a description.";
    private Date creationDate = new Date(System.currentTimeMillis());
    private long id = 987654321;

    @Before
    public void init() {
        testNote = new Note(title, description, creationDate);
        testNote.setId(id);
    }

    @Test
    public void getTitle() {
        assertThat(testNote.getTitle()).isEqualTo(title);
    }

    @Test
    public void setTitle() {
        assertThat(testNote.getTitle()).isEqualTo(title);
        String newTitle = "newTitle";
        testNote.setTitle(newTitle);
        assertThat(testNote.getTitle()).isEqualTo(newTitle);
    }

    @Test
    public void getDescription() {
        assertThat(testNote.getDescription()).isEqualTo(description);
    }

    @Test
    public void setDescription() {
        assertThat(testNote.getDescription()).isEqualTo(description);
        String newDescription = " This is an other description!!!";
        testNote.setDescription(newDescription);
        assertThat(testNote.getDescription()).isEqualTo(newDescription);
    }

    @Test
    public void getCreationDate() {
        assertThat(testNote.getCreationDate()).isEqualTo(creationDate);
    }

    @Test
    public void setCreationDate() {
        assertThat(testNote.getCreationDate()).isEqualTo(creationDate);
        Date newDate = new Date(creationDate.getTime() + 1000);
        testNote.setCreationDate(newDate);
        assertThat(testNote.getCreationDate()).isEqualTo(newDate);
    }

}