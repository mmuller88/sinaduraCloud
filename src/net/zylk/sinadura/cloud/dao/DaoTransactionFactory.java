package net.zylk.sinadura.cloud.dao;

import org.apache.log4j.Logger;

import net.zylk.sinadura.cloud.exceptions.DaoCoreException;
import net.zylk.sinadura.cloud.util.PropertiesReader;

public class DaoTransactionFactory {

	private static final Logger _LOG = Logger.getLogger(DaoTransactionFactory.class.getName());
	
	private static DaoTransactionManager manager = null;

	public synchronized static DaoTransactionManager getInstance() throws DaoCoreException {
		
		if (manager == null) {
			
			try {
				String implClassName = PropertiesReader.getProperty(PropertiesReader.PROPERTY.DAO_TRANSACTION_IMPL_CLASS);
				Class<DaoTransactionManager> cls = (Class<DaoTransactionManager>) Class.forName(implClassName);
				manager = cls.newInstance();
				
			} catch (InstantiationException e) {
				throw new DaoCoreException(e);
			} catch (IllegalAccessException e) {
				throw new DaoCoreException(e);
			} catch (ClassNotFoundException e) {
				throw new DaoCoreException(e);
			}
		}
		
		return manager;
	}

}
