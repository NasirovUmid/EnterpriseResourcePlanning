package com.pm.EnterpriseResourcePlanning.entity;

import com.pm.EnterpriseResourcePlanning.utils.AuditEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "product_sales")
public class ProductSales extends AuditEntity {

    @EmbeddedId
    private ProductSalesId productSalesId = new ProductSalesId();

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private ProductsEntity products;

    @ManyToOne
    @MapsId("salesId")
    @JoinColumn(name = "sales_id")
    private SalesEntity sales;
}
