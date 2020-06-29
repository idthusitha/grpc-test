package com.grpc.grpcclient.controller;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
public class DemoController {

	@RequestMapping(value = "/payredirect")
    public String payredirect(@RequestParam("id") String id) {
        return "Test2";
    }
	
	@RequestMapping(value = "/payredirectparam", method = RequestMethod.POST,produces = "application/json")
	public String payredirectparam(HttpServletRequest request) {
		JSONObject json = new JSONObject();
		try {
			System.out.println("Inside payredirectparam-->");			
			
			json.accumulate("PaRes", request.getParameter("PaRes"));
			json.accumulate("referenceId", request.getParameter("referenceId"));
			json.accumulate("MD", request.getParameter("MD"));
			json.accumulate("gatewayName", request.getParameter("gatewayName"));			
			
		} catch (Exception e) {
			System.out.println("Error in payredirectparam-->"+e);
		}	
		System.out.println(json.toString());
		
		return json.toString();
	}

}
