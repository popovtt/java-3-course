package com.ecommerce.model;

import ua.util.Utils;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class Shipment extends Entity {

    private Order order;
    private LocalDate shipmentDate;
    private String trackingNumber;

    public Shipment(Order order, LocalDate shipmentDate, String trackingNumber) {
        super(shipmentDate);
        validateOrder(order);
        this.order = order;
        this.shipmentDate = shipmentDate != null ? shipmentDate : LocalDate.now();
        setTrackingNumber(trackingNumber);
    }

    public Shipment(Order order, LocalDate shipmentDate) {
        this(order, shipmentDate, generateTrackingNumber());
    }

    public Shipment(Order order) {
        this(order, LocalDate.now(), generateTrackingNumber());
    }

    private Shipment() {
        super();
    }

    // Factory methods
    public static Shipment createShipment(Order order) {
        return new Shipment(order);
    }

    public static Shipment createShipmentWithDate(Order order, LocalDate date) {
        return new Shipment(order, date);
    }

    public static Shipment createShipmentWithTracking(Order order, LocalDate date, String trackingNumber) {
        return new Shipment(order, date, trackingNumber);
    }

    public static Shipment createPendingShipment(Order order) {
        Shipment shipment = new Shipment();
        shipment.order = order;
        shipment.shipmentDate = null;
        shipment.trackingNumber = "PENDING-" + UUID.randomUUID().toString().substring(0, 8);
        return shipment;
    }

    private void validateOrder(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Замовлення не може бути null");
        }
    }

    private static String generateTrackingNumber() {
        return "TRK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    // Getters and Setters
    public Order getOrder() {
        return order;
    }

    public LocalDate getShipmentDate() {
        return shipmentDate;
    }

    protected void setShipmentDate(LocalDate shipmentDate) {
        if (shipmentDate != null) {
            Utils.validateDate(shipmentDate, "Дата відправки");
        }
        this.shipmentDate = shipmentDate;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        Utils.validateString(trackingNumber, "Трекінг номер");
        this.trackingNumber = trackingNumber;
    }

    public boolean isShipped() {
        return shipmentDate != null;
    }

    public void markAsShipped() {
        if (this.shipmentDate == null) {
            this.shipmentDate = LocalDate.now();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Shipment shipment = (Shipment) obj;
        return Objects.equals(order, shipment.order) &&
                Objects.equals(shipmentDate, shipment.shipmentDate) &&
                Objects.equals(trackingNumber, shipment.trackingNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(order, shipmentDate, trackingNumber);
    }

    @Override
    public String toString() {
        return String.format("Shipment{order=%s, shipmentDate=%s, trackingNumber='%s', shipped=%s}",
                order.getCustomer().getFullName(),
                shipmentDate != null ? Utils.formatDate(shipmentDate) : "Не відправлено",
                trackingNumber, isShipped());
    }
}
