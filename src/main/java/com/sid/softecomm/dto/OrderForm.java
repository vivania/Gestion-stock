package com.sid.softecomm.dto;

import java.util.ArrayList;
import java.util.List;

import com.sid.softecomm.entities.Client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
public class OrderForm {
	
	private Client client = new Client();
	private List<OrderProduct> products = new ArrayList<>();
}


