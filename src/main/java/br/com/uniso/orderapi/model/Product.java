package br.com.uniso.orderapi.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class Product {
    private Long id;
    private String name;
    private BigDecimal amount;
}
