package net.zylk.sinadura.cloud.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.zylk.sinadura.cloud.model.ConfigVO;
import net.zylk.sinadura.cloud.model.DocumentVO;
import net.zylk.sinadura.cloud.model.ErrorVO;

public class TransactionVO {

	public enum TransactionStatus {
		TS_STARTED, CONFIG_READY, SIGN_STARTED, SIGN_EXECUTED, TS_ERROR;
	}

	private String uuid;
	private Date creationDate;
	private Date modifiedDate;
	private TransactionStatus status;
	private ErrorVO error;
	private ConfigVO config;
	private Map<String, DocumentVO> documents = new HashMap<String, DocumentVO>();

	
	public TransactionVO() {	
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public TransactionStatus getStatus() {
		return status;
	}

	public void setStatus(TransactionStatus status) {
		this.status = status;
	}

	public ErrorVO getError() {
		return error;
	}

	public void setError(ErrorVO error) {
		this.error = error;
	}

	public ConfigVO getConfig() {
		return config;
	}

	public void setConfig(ConfigVO config) {
		this.config = config;
	}

	@Override
	public String toString() {
		
		try {
			ObjectMapper mapper = new ObjectMapper();
//			return mapper.writeValueAsString(this);
			 return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(this);
			
		} catch (JsonProcessingException e) {
			return super.toString();
		}
		
	}

	public Map<String, DocumentVO> getDocuments() {
		return documents;
	}

	public void setDocuments(Map<String, DocumentVO> documents) {
		this.documents = documents;
	}
	
}
