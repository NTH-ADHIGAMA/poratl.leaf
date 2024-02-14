package com.leaf.api.modal;


import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "project_tables_data_link")
public class ProjectTablesDataLink {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;	
	
	@Column(name = "table_id")
	private int table;	
	
	@Column(name = "row_index")
	private int rowIndex;	
	
	@Column(name = "column_index")
	private int columnIndex;
	
	@Column(name = "data")
	private String data;
	
	@Column(name = "status")
	private String status;
	
	
	@Column(name = "record_Status")
	private boolean recordStatus;
	
	@Column(name = "created_By")
	private int createdBy;
	
	@Column(name = "created_Date")
	private LocalDateTime createdDate;
	
	@Column(name = "last_Modified_Date")
	private LocalDateTime lastModifiedDate;
	
	@Column(name = "last_Modified_By")
	private int lastModifiedBy;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTable() {
		return table;
	}

	public void setTable(int table) {
		this.table = table;
	}

	public int getRowIndex() {
		return rowIndex;
	}

	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}

	public int getColumnIndex() {
		return columnIndex;
	}

	public void setColumnIndex(int columnIndex) {
		this.columnIndex = columnIndex;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isRecordStatus() {
		return recordStatus;
	}

	public void setRecordStatus(boolean recordStatus) {
		this.recordStatus = recordStatus;
	}

	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDateTime getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public int getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(int lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	@Override
	public String toString() {
		return "ProjectTablesDataLink [id=" + id + ", table=" + table + ", rowIndex=" + rowIndex + ", columnIndex="
				+ columnIndex + ", data=" + data + ", status=" + status + ", recordStatus=" + recordStatus
				+ ", createdBy=" + createdBy + ", createdDate=" + createdDate + ", lastModifiedDate=" + lastModifiedDate
				+ ", lastModifiedBy=" + lastModifiedBy + "]";
	}

	
	
	

}
