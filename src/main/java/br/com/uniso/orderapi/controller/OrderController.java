package br.com.uniso.orderapi.controller;

import br.com.uniso.orderapi.model.Order;
import br.com.uniso.orderapi.model.Product;
import br.com.uniso.orderapi.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/order")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping(value = "/{productsIds}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Order> create(@PathVariable("productsIds") List<Long> ids) {
        List<Product> products = new ArrayList<>();
        Order order = new Order();
        order.setAmount(BigDecimal.ZERO);
        ids.forEach(id -> {
            Product response = restTemplate.getForObject("https://catalog-api/catalog/" + id, Product.class);
            products.add(response);
            order.setAmount(order.getAmount().add(response.getAmount()));
        });
//        order.setProducts(products);
        orderRepository.save(order);
        return ResponseEntity.ok().body(order);
    }

    @GetMapping
    private ResponseEntity<List<Order>> getOrders() {
        return ResponseEntity.ok().body(orderRepository.findAll());
    }
}
