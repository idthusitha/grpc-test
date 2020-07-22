package com.grpc.grpcclient.impl;

import java.util.Map;
import java.util.Properties;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.grpc.grpcclient.util.CommonUtils;
import com.grpc.grpcclient.util.CouchbaseConnector;
import com.grpc.grpcclient.util.CouchbaseConnectorFacade;

@Service
public class CouchBaseServcie {

	CouchbaseConnector aeroPayCouchbaseConnector = CouchbaseConnectorFacade.getAeroPayMasterBucketConnector();

	public String insertAllDocuments() {
		Properties prop = CommonUtils.getInstance().getProperties();
		try {

			System.out.println("-----------------DATA LOADING STARTING----------------------");
			aeroPayCouchbaseConnector.flush();

			Map<String, String> fileMap = CommonUtils.getInstance().getFileData(prop.getProperty("json.path.location"));

			for (String file : fileMap.keySet()) {

				String referenceId = "";
				String mockJson = fileMap.get(file);

				if (file.startsWith("TemplateConfig-PAY")) {
					referenceId = "Payment";

				} else if (file.startsWith("Dynamic")) {
					referenceId = "Dynamic-f9ff-4804-b325-4c449c809c76";
				
				} else if (file.startsWith("paymentGate")) {
					
					JSONObject json = new JSONObject (mockJson);
					referenceId =json.getJSONObject("paymentOption").getString("paymentOptionId");
				}
				if(!"".equals(referenceId)) {
					System.out.println("referenceId==>"+referenceId);
					aeroPayCouchbaseConnector.insert(referenceId, mockJson);
				}
			}
			System.out.println("-----------------DATA LOADING DONE----------------------");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Done";
	}
}
