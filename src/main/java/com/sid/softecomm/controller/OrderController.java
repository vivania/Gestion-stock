package com.sid.softecomm.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sid.softecomm.dao.ClientRepository;
import com.sid.softecomm.dao.OrderItemRepository;
import com.sid.softecomm.dao.OrderRepository;
import com.sid.softecomm.dao.ProductRepository;
import com.sid.softecomm.dto.OrderForm;
import com.sid.softecomm.dto.OrderProduct;
import com.sid.softecomm.entities.Client;
import com.sid.softecomm.entities.Order;
import com.sid.softecomm.entities.OrderItem;
import com.sid.softecomm.entities.Product;

@CrossOrigin("*")
@RestController
public class OrderController {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ClientRepository clientRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	
	@GetMapping("allOrder")
	public List<Order> getAllOrder(){
		List<Order> order = new ArrayList<>();
		orderRepository.findAll().forEach(order::add);
		return order;
	}
	
	
	@PostMapping("/orders")
	public Order saveOrder(@RequestBody OrderForm orderForm) {
		Client client = new Client();
		client.setId(orderForm.getClient().getId());
		client.setName(orderForm.getClient().getName());
		client.setUsername(orderForm.getClient().getUsername());
		client.setAddress(orderForm.getClient().getAddress());
		client.setEmail(orderForm.getClient().getEmail());
		client.setPhone(orderForm.getClient().getPhone());
		client =clientRepository.save(client);
		
		System.out.println(client.getId());
		
		Order order = new Order();
		order.setClient(client);
		order.setDate(new Date());
		order = orderRepository.save(order);
		
		double total = 0;
		
		for(OrderProduct p : orderForm.getProducts()) {
			
			OrderItem orderItem = new OrderItem();
			Product product = productRepository.findById(p.getId()).get();
			orderItem.setOrder(order);
			orderItem.setProduct(product);
			orderItem.setPrice(product.getPrice());
			orderItem.setQuantity(p.getQuantity());
			orderItemRepository.save(orderItem);
			total += p.getQuantity() * product.getPrice();
		}
		order.setTotalAmount(total);
		
	return orderRepository.save(order);
	}

}
