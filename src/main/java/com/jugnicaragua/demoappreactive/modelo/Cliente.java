package com.jugnicaragua.demoappreactive.modelo;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table("clientes")
public class Cliente {
   
	
	@Id
    private String id;
    private String nombre;
    private int edad;
    private double salario;

}
