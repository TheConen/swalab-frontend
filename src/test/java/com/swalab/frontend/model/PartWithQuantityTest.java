package com.swalab.frontend.model;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PartWithQuantityTest {

    private PartWithQuantity testPartWithQuantity;
    private AvailablePart availablePart;
    private String name = "name";
    private String description = "This is a description.";
    private long id = 987654321;
    private int quantity = 5;
    private String unit = "m";

    @Before
    public void init() {
        availablePart = new AvailablePart(name, description);
        availablePart.setId(id - 1);
        testPartWithQuantity = new PartWithQuantity(availablePart, quantity, unit);
        testPartWithQuantity.setId(id);
    }

    @Test
    public void getAvailablePart() {
        assertThat(testPartWithQuantity.getAvailablePart()).isEqualTo(availablePart);
    }

    @Test
    public void setAvailablePart() {
        assertThat(testPartWithQuantity.getAvailablePart()).isEqualTo(availablePart);
        AvailablePart newAvailablePart = new AvailablePart("newName", description);
        testPartWithQuantity.setAvailablePart(newAvailablePart);
        assertThat(testPartWithQuantity.getAvailablePart()).isEqualTo(newAvailablePart);
    }

    @Test
    public void getQuantity() {
        assertThat(testPartWithQuantity.getQuantity()).isEqualTo(quantity);
    }

    @Test
    public void setQuantity() {
        assertThat(testPartWithQuantity.getQuantity()).isEqualTo(quantity);
        int newQuantity = 10;
        testPartWithQuantity.setQuantity(newQuantity);
        assertThat(testPartWithQuantity.getQuantity()).isEqualTo(newQuantity);
    }

    @Test
    public void getUnit() {
        assertThat(testPartWithQuantity.getUnit()).isEqualTo(unit);
    }

    @Test
    public void setUnit() {
        assertThat(testPartWithQuantity.getUnit()).isEqualTo(unit);
        String newUnit = "mm";
        testPartWithQuantity.setUnit(newUnit);
        assertThat(testPartWithQuantity.getUnit()).isEqualTo(newUnit);
    }

    @Test
    public void getId() {
        assertThat(testPartWithQuantity.getId()).isEqualTo(id);
    }

    @Test
    public void setId() {
        assertThat(testPartWithQuantity.getId()).isEqualTo(id);
        long newId = 123456789;
        testPartWithQuantity.setId(newId);
        assertThat(testPartWithQuantity.getId()).isEqualTo(newId);
    }
}