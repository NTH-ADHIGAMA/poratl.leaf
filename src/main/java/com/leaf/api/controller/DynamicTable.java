package com.leaf.api.controller;

import javax.persistence.*;
import java.util.Map;

@Entity
public class DynamicTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    @CollectionTable(name = "dynamic_table_data", joinColumns = @JoinColumn(name = "dynamic_table_id"))
    @MapKeyColumn(name = "header")
    @Column(name = "value")
    private Map<String, String> data;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Map<String, String> getData() {
		return data;
	}

	public void setData(Map<String, String> data) {
		this.data = data;
	}

    // Getters and setters
    
    
}
