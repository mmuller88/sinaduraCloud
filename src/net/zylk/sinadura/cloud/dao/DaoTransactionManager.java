package net.zylk.sinadura.cloud.dao;

import java.io.InputStream;
import java.util.List;

import net.zylk.sinadura.cloud.exceptions.DaoCoreException;
import net.zylk.sinadura.cloud.model.ConfigVO;
import net.zylk.sinadura.cloud.model.DocumentVO;
import net.zylk.sinadura.cloud.model.ErrorVO;
import net.zylk.sinadura.cloud.model.TransactionVO;
import net.zylk.sinadura.cloud.model.TransactionVO.TransactionStatus;

public interface DaoTransactionManager {
	
	public String create() throws DaoCoreException;
	public void remove(String uuid) throws DaoCoreException;
	
	public void setConfig(String uuid, ConfigVO config) throws DaoCoreException;
	public ConfigVO getConfig(String uuid) throws DaoCoreException;
	
	public void setStatus(String uuid, TransactionStatus status) throws DaoCoreException;
	public TransactionStatus getStatus(String uuid) throws DaoCoreException;
	
	public void setError(String uuid, String code, String message) throws DaoCoreException;
	public ErrorVO getError(String uuid) throws DaoCoreException;
	
	public String addDocument(String uuid, String idDocument, String type, String name, String url) throws DaoCoreException;
	public List<DocumentVO> getDocuments(String uuid) throws DaoCoreException;
	
	public void addSignatureFile(String uuid, String idDocument, InputStream data) throws DaoCoreException;
	public InputStream getSignatureFile(String uuid, String idDocument) throws DaoCoreException;
	
	// TODO solo para development (para el DaoChecker)
	public List<TransactionVO> getAll() throws DaoCoreException;

}
