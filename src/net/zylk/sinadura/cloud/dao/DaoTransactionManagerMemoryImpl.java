package net.zylk.sinadura.cloud.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import net.zylk.sinadura.cloud.dao.DaoTransactionManager;
import net.zylk.sinadura.cloud.exceptions.DaoCoreException;
import net.zylk.sinadura.cloud.model.ConfigVO;
import net.zylk.sinadura.cloud.model.DocumentVO;
import net.zylk.sinadura.cloud.model.ErrorVO;
import net.zylk.sinadura.cloud.model.TransactionVO;
import net.zylk.sinadura.cloud.model.TransactionVO.TransactionStatus;


public class DaoTransactionManagerMemoryImpl implements DaoTransactionManager {

	private static final Logger _LOG = Logger.getLogger(DaoTransactionManagerMemoryImpl.class.getName());
	
	private static Map<String, TransactionVO> transactions = new HashMap<String, TransactionVO>();

	public static String TMP_FOLDER_PATH;

	private static final String FILES_DB_SIGNATURE_SUFFIX = "signature";

	
	public DaoTransactionManagerMemoryImpl() throws DaoCoreException {
		TMP_FOLDER_PATH = new File(System.getProperty("java.io.tmpdir") + "/sinaduraCloud").getAbsolutePath();
	}
	
	private TransactionVO getTransaction(String uuid) throws DaoCoreException {
		
		if (transactions.containsKey(uuid)) {
			return transactions.get(uuid);
		} else {
			throw new DaoCoreException("unknown transaction uuid: " + uuid);
		}
		
	}

	@Override
	public String create() throws DaoCoreException {
		
		TransactionVO t = new TransactionVO();
		String uuid = UUID.randomUUID().toString();
		t.setUuid(uuid);
		t.setCreationDate(new Date());
		t.setModifiedDate(new Date());
		t.setStatus(TransactionStatus.TS_STARTED);
		transactions.put(uuid, t);
		
		return uuid;
	}

	@Override
	public void remove(String uuid) throws DaoCoreException {

		if (transactions.containsKey(uuid)) {
			// 1- borramos los ficheros asociados
			removeTsFolder(uuid);
			// 2- borramos la transaccion			
			transactions.remove(uuid);
		}
	}

	@Override
	public List<TransactionVO> getAll() throws DaoCoreException, DaoCoreException {
		
		List<TransactionVO> result = new ArrayList<TransactionVO>();
		for (String key : transactions.keySet()) {
			result.add(transactions.get(key));
		}

		// orden inverso por fecha de modificacion
		Collections.sort(result, new Comparator<TransactionVO>() {
			@Override
			public int compare(TransactionVO firstItem, TransactionVO secondItem) {
				return secondItem.getModifiedDate().compareTo(firstItem.getModifiedDate());
			}
		});

		return result;
	}

	@Override
	public void setError(String uuid, String code, String message) throws DaoCoreException {

		ErrorVO errorVO = new ErrorVO(code, message);
		getTransaction(uuid).setError(errorVO);
	}

	@Override
	public ErrorVO getError(String uuid) throws DaoCoreException {

		return getTransaction(uuid).getError();
	}
	
	@Override
	public void setConfig(String uuid, ConfigVO config) throws DaoCoreException {
		
		getTransaction(uuid).setConfig(config);
	}

	@Override
	public ConfigVO getConfig(String uuid) throws DaoCoreException {
	
		return getTransaction(uuid).getConfig();
	}

	@Override
	public void setStatus(String uuid, TransactionStatus status) throws DaoCoreException {
		
		getTransaction(uuid).setStatus(status);
	}

	@Override
	public TransactionStatus getStatus(String uuid) throws DaoCoreException {
		
		return getTransaction(uuid).getStatus();
	}
	
	@Override
	public String addDocument(String uuid, String idDocument, String type, String name, String url) throws DaoCoreException {
	
		DocumentVO documentVO = new DocumentVO();
		documentVO.setId(idDocument);
		documentVO.setType(type);
		documentVO.setName(name);
		documentVO.setUrl(url);
		getTransaction(uuid).getDocuments().put(idDocument, documentVO);
		
		return idDocument;
	}

	@Override
	public List<DocumentVO> getDocuments(String uuid) throws DaoCoreException {
		
		return new ArrayList(getTransaction(uuid).getDocuments().values());
	}

	@Override
	public void addSignatureFile(String uuid, String idDocument, InputStream data) throws DaoCoreException {

		try {
			createTsFolder(uuid);
			String path = getSignatureFilePath(uuid, idDocument);
			OutputStream os = new FileOutputStream(path);
			IOUtils.copy(data, os);

		} catch (FileNotFoundException e) {
			throw new DaoCoreException(e);
		} catch (IOException e) {
			throw new DaoCoreException(e);
		}
	}

	@Override
	public InputStream getSignatureFile(String uuid, String idDocument) throws DaoCoreException {

		try {
			String path = getSignatureFilePath(uuid, idDocument);
			InputStream is = new FileInputStream(path);
			return is;

		} catch (FileNotFoundException e) {
			throw new DaoCoreException(e);
		} catch (UnsupportedEncodingException e) {
			throw new DaoCoreException(e);
		}
	}
	
	private String getSignatureFilePath(String uuid, String idDocument) throws UnsupportedEncodingException {
		
		String idDocumentEncoded = URLEncoder.encode(idDocument, "UTF-8");
		String path = TMP_FOLDER_PATH+ File.separator + uuid + File.separator + idDocumentEncoded + "-"
				+ FILES_DB_SIGNATURE_SUFFIX;
		
		return path;
	}
	
	
	private void createTsFolder(String uuid) throws DaoCoreException {

		File tsFolderFile = new File(TMP_FOLDER_PATH + File.separator + uuid);
		if (!tsFolderFile.exists()) {
			boolean success  = tsFolderFile.mkdirs();
			if (!success) {
				throw new DaoCoreException("error creating ts directory");
			}
		}
		
	}

	private void removeTsFolder(String uuid) {

		try {
			FileUtils.deleteDirectory(new File(TMP_FOLDER_PATH + File.separator + uuid));
		} catch (IOException e) {
			_LOG.warn("could not delete ts directory: " + e.getMessage());
			// nothing
		}

	}

}
