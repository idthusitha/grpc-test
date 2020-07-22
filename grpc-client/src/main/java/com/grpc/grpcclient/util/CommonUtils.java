package com.grpc.grpcclient.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Thusitha Dissanayaka
 * 
 */
public class CommonUtils {
	final static Logger logger = LoggerFactory.getLogger(CommonUtils.class);

	private CommonUtils() {
	}

	public static Properties properties = null;
	private static CommonUtils instance = null;

	/**
	 * @return CommonUtils
	 */
	public static CommonUtils getInstance() {
		if (instance == null) {
			instance = new CommonUtils();
		}
		return instance;
	}

	public String printTime(long startTime, String lable) {
		String uuid = UUID.randomUUID().toString();
		String time = "";
		try {
			long endTime = System.currentTimeMillis();
			long totalTime = endTime - startTime;
			time = ((totalTime / 60000) % 60) + "m " + ((totalTime / 1000) % 60) + "s " + totalTime % 1000 + "ms";
			logger.warn("Total time for " + lable + ":" + ((totalTime / 60000) % 60) + "m " + ((totalTime / 1000) % 60)
					+ "s " + totalTime % 1000 + "ms\n\n");
		} catch (Exception e) {
			logger.error("ELKERROR<~>" + uuid + "<~>error <~>", e);
			logger.error("ELKERROREND");
		}
		return time;
	}

	public Properties getProperties() {
		String uuid = UUID.randomUUID().toString();
		try {
			java.util.Locale myLocale = java.util.Locale.getDefault();
			PropertyResourceBundle resource = (PropertyResourceBundle) PropertyResourceBundle.getBundle("application",
					myLocale);
			properties = convertResourceBundleToProperties(resource);
		} catch (Exception e) {
			logger.error("ELKERROR<~>" + uuid + "<~>error <~>", e);
			logger.error("ELKERROREND");
		}

		return properties;

	}

	private Properties convertResourceBundleToProperties(ResourceBundle resource) {
		Properties properties = new Properties();

		Enumeration<String> keys = resource.getKeys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			properties.put(key, resource.getString(key));
		}

		return properties;
	}

	public String getFile(String fileName) {
		String uuid = UUID.randomUUID().toString();
		StringBuilder result = new StringBuilder("");

		// Get file from resources folder
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(fileName).getFile());

		try (Scanner scanner = new Scanner(file)) {

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				result.append(line).append("\n");
			}
			scanner.close();

		} catch (IOException e) {
			logger.error("ELKERROR<~>" + uuid + "<~>error <~>", e);
			logger.error("ELKERROREND");
		}

		return result.toString();
	}

	public ArrayList<String> getFileList(String filePath) {
		ArrayList<String> list = new ArrayList<>();
		try {
			File folder = new File(filePath);
			File[] listOfFiles = folder.listFiles();

			for (int i = 0; i < listOfFiles.length; i++) {
				if (listOfFiles[i].isFile()) {
					System.out.println("File " + listOfFiles[i].getName());
					list.add(listOfFiles[i].getAbsolutePath());
				} else if (listOfFiles[i].isDirectory()) {
					System.out.println("Directory " + listOfFiles[i].getName());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public Map<String, String> getFileData(String filePath) {
		Map<String, String> map = new HashMap<>();
		try {
			File folder = new File(filePath);
			File[] listOfFiles = folder.listFiles();

			for (int i = 0; i < listOfFiles.length; i++) {
				if (listOfFiles[i].isFile()) {
					System.out.println("File " + listOfFiles[i].getName());
					map.put(listOfFiles[i].getName(), getFile(listOfFiles[i].getAbsolutePath()));
				} else if (listOfFiles[i].isDirectory()) {
					System.out.println("Directory " + listOfFiles[i].getName());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	public int getNumber(String number) {
		int numeric = 0;
		try {
			numeric = Integer.parseInt(number);
		} catch (Exception e) {
			numeric = 0;
		}
		return numeric;
	}

}
