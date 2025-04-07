package org.example.warehouse_managment.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "movements")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movements {

    @Id
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "from_warehouse_id", referencedColumnName = "id", nullable = true)
    private Warehouse fromWarehouse;

    @ManyToOne
    @JoinColumn(name = "to_warehouse_id", referencedColumnName = "id")
    private Warehouse toWarehouse;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "moved_at")
    private LocalDateTime movedAt;

    @PrePersist
    @PreUpdate
    private void setTimestamp() {
        this.movedAt = LocalDateTime.now();
    }

    public Movements(int id, Product product, Warehouse fromWarehouse, Warehouse toWarehouse, int quantity) {
        this.id = id;
        this.product = product;
        this.fromWarehouse = fromWarehouse;
        this.toWarehouse = toWarehouse;
        this.quantity = quantity;
    }
}
