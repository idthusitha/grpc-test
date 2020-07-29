package com.grpc.grpcclient.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.soap.*;

@RestController
@RequestMapping("/soapmessge")
public class SoapTestController {

	@RequestMapping(value = "/call")
	public String payredirect() {

		String soapEndpointUrl = "https://www.fatourati.ma/cmifat/services/RefFatouratiWS.asmx";
		String soapAction = "https://www.fatourati.ma/cmifat/services/RefFatouratiWS";

		callSoapWebService(soapEndpointUrl, soapAction);

		return "Test2";
	}

	private static void createSoapEnvelope(SOAPMessage soapMessage) throws SOAPException {
		SOAPPart soapPart = soapMessage.getSOAPPart();

		String myNamespace = "myNamespace";
		String myNamespaceURI = "https://www.w3schools.com/xml/";

		// SOAP Envelope
		SOAPEnvelope envelope = soapPart.getEnvelope();
		envelope.addNamespaceDeclaration(myNamespace, myNamespaceURI);

		/*
		 * Constructed SOAP Request Message: <SOAP-ENV:Envelope
		 * xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/"
		 * xmlns:myNamespace="https://www.w3schools.com/xml/"> <SOAP-ENV:Header/>
		 * <SOAP-ENV:Body> <myNamespace:CelsiusToFahrenheit>
		 * <myNamespace:Celsius>100</myNamespace:Celsius>
		 * </myNamespace:CelsiusToFahrenheit> </SOAP-ENV:Body> </SOAP-ENV:Envelope>
		 */

		// SOAP Body
		SOAPBody soapBody = envelope.getBody();
		SOAPElement soapBodyElem = soapBody.addChildElement("genRefFatourati", myNamespace);
		SOAPElement soapin = soapBodyElem.addChildElement("in", myNamespace);
		
		soapin.addChildElement("creancierId", myNamespace).addTextNode("1234");
		soapin.addChildElement("dateServeurCreancier", myNamespace).addTextNode("20200729171010");
		soapin.addChildElement("action", myNamespace).addTextNode("0");
		soapin.addChildElement("nbrTotalArticles", myNamespace).addTextNode("1");
		soapin.addChildElement("montantTotalArticles", myNamespace).addTextNode("12500");
		soapin.addChildElement("MAC", myNamespace).addTextNode("1bcb16879de413de5d93d13ba5047d03");		
		
	}

	private static String callSoapWebService(String soapEndpointUrl, String soapAction) {
		String response = "";
		try {
			// Create SOAP Connection
			SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
			SOAPConnection soapConnection = soapConnectionFactory.createConnection();

			// Send SOAP Message to SOAP Server
			SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(soapAction), soapEndpointUrl);

			// Print the SOAP Response
			System.out.println("Response SOAP Message:");
			soapResponse.writeTo(System.out);
			response = soapResponse.toString();
			System.out.println();

			soapConnection.close();
		} catch (Exception e) {
			System.err.println(
					"\nError occurred while sending SOAP Request to Server!\nMake sure you have the correct endpoint URL and SOAPAction!\n");
			e.printStackTrace();
		}
		return response;
	}

	private static SOAPMessage createSOAPRequest(String soapAction) throws Exception {
		MessageFactory messageFactory = MessageFactory.newInstance();
		SOAPMessage soapMessage = messageFactory.createMessage();

		createSoapEnvelope(soapMessage);

		MimeHeaders headers = soapMessage.getMimeHeaders();
		headers.addHeader("SOAPAction", soapAction);

		soapMessage.saveChanges();

		/* Print the request message, just for debugging purposes */
		System.out.println("Request SOAP Message:");
		soapMessage.writeTo(System.out);
		System.out.println("\n");

		return soapMessage;
	}

}
