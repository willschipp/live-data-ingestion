package com.emc.data.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/*
			"pointId":"13",
			"pointName":"SysMod",
			"customTag":"SysMod",
			"className":"null",
			"systemTag":"null",
			"physicalQuantity":"generic_quantity",
			"unit":"/0",
			"data":"0.0",
			"status":"OVERRIDEN_IN1",
			"extensions":[
				{
					"extensionName":"WRITEABLE",
					"extensionType":"ACCESS TYPE"
				},
				{
					"extensionName":"TIMEBASED ACQUISITION, EVERY 1 MINUTES",
					"extensionType":"Acquisition Mode"
				},
				{
					"extensionName":"OVERRIDEN_IN1",
					"extensionType":"STATUS"
				}
			]
 */
@Entity
public class DataPoint {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column
	private int pointId;

	@Column	
	private String pointName;
	
	@Column
	private String customTag;
	
	@Column
	private String className;
	
	@Column
	private String systemTag;
	
	@Column
	private String physicalQuantity;
	
	@Column
	private String unit;
	
	@Column
	private String data;
	
	@Column
	private String status;
	
	@ElementCollection
	private List<Extension> extensions = new ArrayList<Extension>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getPointId() {
		return pointId;
	}

	public void setPointId(int pointId) {
		this.pointId = pointId;
	}

	public String getPointName() {
		return pointName;
	}

	public void setPointName(String pointName) {
		this.pointName = pointName;
	}

	public String getCustomTag() {
		return customTag;
	}

	public void setCustomTag(String customTag) {
		this.customTag = customTag;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getSystemTag() {
		return systemTag;
	}

	public void setSystemTag(String systemTag) {
		this.systemTag = systemTag;
	}

	public String getPhysicalQuantity() {
		return physicalQuantity;
	}

	public void setPhysicalQuantity(String physicalQuantity) {
		this.physicalQuantity = physicalQuantity;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Extension> getExtensions() {
		return extensions;
	}

	public void setExtensions(List<Extension> extensions) {
		this.extensions = extensions;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
}
