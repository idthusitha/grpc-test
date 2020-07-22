package com.grpc.grpcclient.loader;

import org.springframework.stereotype.Component;

import com.grpc.grpcclient.impl.CouchBaseServcie;

@Component
public class InitialLoader {

	public InitialLoader() {
		loadCouchBaseData();
	}

	private void loadCouchBaseData() {
		new CouchBaseServcie().insertAllDocuments();
	}
}
