package org.example.warehouse_managment.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "created_at")
    private Date createdAt;

    public Product(String name, Category category, Supplier supplier, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public Product(int id, String name, Category category, Supplier supplier, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
}
