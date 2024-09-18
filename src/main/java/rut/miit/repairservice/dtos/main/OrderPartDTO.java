package rut.miit.repairservice.dtos.main;

import rut.miit.repairservice.dtos.base.BaseDTO;

public class OrderPartDTO extends BaseDTO {
    private String order;
    private String part;
    private Integer quantity;

    public OrderPartDTO(String order, String part, Integer quantity) {
        this.order = order;
        this.part = part;
        this.quantity = quantity;
    }

    public OrderPartDTO() {
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderPartDTO{" +
                "order='" + order + '\'' +
                ", part='" + part + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
