package net.zylk.sinadura.cloud.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class PropertiesReader {
	
	private static final Logger _LOG = Logger.getLogger(PropertiesReader.class.getName());
	
	private static Properties properties = null;
	private static final String PATH = "net/zylk/sinadura/cloud/resources/sinadura-cloud.properties";

	public enum PROPERTY {
		DAO_TRANSACTION_IMPL_CLASS,
		FILES_DB_BASE_PATH,
	};
	
	private static Properties getProps() {
		
		if (properties == null) {
			properties = new Properties();
			
			InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(PATH);
			try {
				properties.load(is);
			} catch (IOException e) {
				_LOG.error(e);
			}
		}
		
		return properties;
	}

	public static String getProperty(PROPERTY key) {
		
		String parsedKey = PropertiesReader.getKeyName(key);
		String value = getProps().getProperty(parsedKey);
		return value.trim();
	}

	private static String getKeyName(PROPERTY key) {
		
		String keyStr = key.toString();
		String lowerCaseKeyStr = StringUtils.lowerCase(keyStr);
		return lowerCaseKeyStr.replaceAll("_", ".");
	}
	
}
