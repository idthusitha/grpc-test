package com.grpc.grpcclient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grpc.grpcclient.impl.CouchBaseServcie;



@RestController
@RequestMapping("/data")
public class CouchBaseController {
	
	@Autowired
	CouchBaseServcie couchBaseServcie;
	
	@RequestMapping(value = "/updateCouchBase")
    public String updateCouchBase() {
        return couchBaseServcie.insertAllDocuments();
    }

}
