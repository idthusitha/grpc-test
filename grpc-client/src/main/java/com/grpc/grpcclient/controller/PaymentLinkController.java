package com.grpc.grpcclient.controller;

import java.util.Enumeration;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/paymentlink")
public class PaymentLinkController {

	@RequestMapping(value = "/statusCheck")
    public String payredirect(@RequestParam("id") String id) {
        return "Test2";//user
    }
	
	
	@RequestMapping(value = "/statusUpdate", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public ResponseEntity<String> checkjson(@RequestBody String payload, HttpServletRequest request) {
		String uuid = UUID.randomUUID().toString();
		try {
			System.out.println("ELK<~>" + uuid + "<~>S<~>" + "payload==><~>" + payload);

			Enumeration<String> headerNames = request.getHeaderNames();
			while (headerNames.hasMoreElements()) {
				String headerName = headerNames.nextElement();
				System.out.println("ELK<~>" + uuid + "<~>S<~>" + "Header Name - <~>" + headerName + ", Value - " + request.getHeader(headerName));
			}
			System.out.println("ELK<~>" + uuid + "<~>S<~>" + "\n\n");

			Enumeration<String> params = request.getParameterNames();
			while (params.hasMoreElements()) {
				String paramName = params.nextElement();
				System.out.println("ELK<~>" + uuid + "<~>S<~>" + "Parameter Name - <~>" + paramName + ", Value - " + request.getParameter(paramName));
			}

		} catch (Exception e) {
			e.printStackTrace();			
		}
		return ResponseEntity.ok(payload.toString());
	}

}
