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
public class WarehousePartAndOrderTest {

    private WarehousePartAndOrder testWarehousePartAndOrder;
    private PartWithQuantity partWithQuantity;
    private AvailablePart availablePart;
    private String name = "name";
    private String description = "This is a description.";
    private long id = 987654321;
    private int quantity = 5;
    private String unit = "m";
    private int orderNumber = 56;
    private Date orderDate = new Date(System.currentTimeMillis());
    private Status status = Status.OPEN;

    @Before
    public void init() {
        availablePart = new AvailablePart(name, description);
        availablePart.setId(id - 2);
        partWithQuantity = new PartWithQuantity(availablePart, quantity, unit);
        partWithQuantity.setId(id - 1);
        testWarehousePartAndOrder = new WarehousePartAndOrder(orderNumber, description, orderDate, partWithQuantity, status);
        testWarehousePartAndOrder.setId(id);
    }

    @Test
    public void getOrderNumber() {
        assertThat(testWarehousePartAndOrder.getOrderNumber()).isEqualTo(orderNumber);
    }

    @Test
    public void setOrderNumber() {
        assertThat(testWarehousePartAndOrder.getOrderNumber()).isEqualTo(orderNumber);
        int newOrderNumber = 42;
        testWarehousePartAndOrder.setOrderNumber(newOrderNumber);
        assertThat(testWarehousePartAndOrder.getOrderNumber()).isEqualTo(newOrderNumber);
    }

    @Test
    public void getDescription() {
        assertThat(testWarehousePartAndOrder.getDescription()).isEqualTo(description);
    }

    @Test
    public void setDescription() {
        assertThat(testWarehousePartAndOrder.getDescription()).isEqualTo(description);
        String newDescription = "new Description.";
        testWarehousePartAndOrder.setDescription(newDescription);
        assertThat(testWarehousePartAndOrder.getDescription()).isEqualTo(newDescription);
    }

    @Test
    public void getOrderDate() {
        assertThat(testWarehousePartAndOrder.getOrderDate()).isEqualTo(orderDate);
    }

    @Test
    public void setOrderDate() {
        assertThat(testWarehousePartAndOrder.getOrderDate()).isEqualTo(orderDate);
        Date newDate = new Date(orderDate.getTime() + 1000);
        testWarehousePartAndOrder.setOrderDate(newDate);
        assertThat(testWarehousePartAndOrder.getOrderDate()).isEqualTo(newDate);
    }

    @Test
    public void getPart() {
        assertThat(testWarehousePartAndOrder.getPart()).isEqualTo(partWithQuantity);
    }

    @Test
    public void setPart() {
        assertThat(testWarehousePartAndOrder.getPart()).isEqualTo(partWithQuantity);
        PartWithQuantity newPartWithQuantity = new PartWithQuantity(availablePart, 7, "kg");
        testWarehousePartAndOrder.setPart(newPartWithQuantity);
        assertThat(testWarehousePartAndOrder.getPart()).isEqualTo(newPartWithQuantity);
    }

    @Test
    public void getStatus() {
        assertThat(testWarehousePartAndOrder.getStatus()).isEqualTo(status);
    }

    @Test
    public void setStatus() {
        assertThat(testWarehousePartAndOrder.getStatus()).isEqualTo(status);
        Status newStatus = Status.IN_PROGRESS;
        testWarehousePartAndOrder.setStatus(newStatus);
        assertThat(testWarehousePartAndOrder.getStatus()).isEqualTo(newStatus);
    }

}