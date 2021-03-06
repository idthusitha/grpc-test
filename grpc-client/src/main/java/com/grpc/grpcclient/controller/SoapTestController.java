package com.grpc.grpcclient.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.soap.*;

import org.apache.commons.codec.binary.Base64;

@RestController
@RequestMapping("/soapmessge")
public class SoapTestController {

	@RequestMapping(value = "/call", produces = "application/xml")
	public String payredirect() {

//		String soapEndpointUrl = "https://www.fatourati.ma/cmifat/services/RefFatouratiWS";
//		String soapAction = "https://www.fatourati.ma/cmifat/services/RefFatouratiWS";
//		
		String soapEndpointUrl = "http://194.204.248.119/cmifat/services/RefFatouratiWS";
		String soapAction = "http://194.204.248.119/cmifat/services/RefFatouratiWS";
		
		

		return callSoapWebService(soapEndpointUrl, soapAction);
	}

	private static void createSoapEnvelope(SOAPMessage soapMessage) throws SOAPException {
		SOAPPart soapPart = soapMessage.getSOAPPart();

		String myNamespace = "";
		String myNamespaceURI = "http://schemas.xmlsoap.org/soap/encoding/";
		String myNameSpaceURITemp = "";

		// SOAP Envelope
		SOAPEnvelope envelope = soapPart.getEnvelope();
		envelope.addNamespaceDeclaration(myNamespace, myNamespaceURI);
		//envelope.addNamespaceDeclaration("xmlns:sk", "http://skeleton.services.fawatir.fatouratibo.mtc.com");
		//envelope.addAttribute("xmlns:sk", "http://skeleton.services.fawatir.fatouratibo.mtc.com");

		/*
		 * Constructed SOAP Request Message: <SOAP-ENV:Envelope
		 * xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/"
		 * xmlns:myNamespace="https://www.w3schools.com/xml/"> <SOAP-ENV:Header/>
		 * <SOAP-ENV:Body> <myNamespace:CelsiusToFahrenheit>
		 * <myNamespace:Celsius>100</myNamespace:Celsius>
		 * </myNamespace:CelsiusToFahrenheit> </SOAP-ENV:Body> </SOAP-ENV:Envelope>
		 */

		String creancierId = "1023";
		String creanceId = "01";
		String dateServeurCreancier = getCurrentDateStr();
		String action = "0";
		String nbrTotalArticles = "1";
		String montantTotalArticles = "12500";
		String idArticle = "abcd";
		String MAC = getMACValue(creancierId, dateServeurCreancier, action, nbrTotalArticles, montantTotalArticles,
				idArticle);
		String description = "Enregistrement,Immatriculation";
		String refClient = "210259_1514477225815";

		// SOAP Body
		SOAPBody soapBody = envelope.getBody();
		SOAPElement soapBodyElem = soapBody.addChildElement("genRefFatourati", "sk",
				"http://skeleton.services.fawatir.fatouratibo.mtc.com");
		SOAPElement soapin = soapBodyElem.addChildElement("in0", myNamespace, myNameSpaceURITemp);

		soapin.addChildElement("MAC", myNamespace, "").addTextNode(MAC);
		soapin.addChildElement("action", myNamespace, "").addTextNode(action);
		soapin.addChildElement("creancierId", myNamespace, "").addTextNode(creancierId);
		soapin.addChildElement("dateServeurCreancier", myNamespace, "").addTextNode(dateServeurCreancier);
		
		soapin.addChildElement("globalParams", myNamespace, "").addTextNode("");
		soapin.addChildElement("ident2ClientSysPmt", myNamespace, "").addTextNode("");
		soapin.addChildElement("identClientSysPmt", myNamespace, "").addTextNode("");
		
		soapin.addChildElement("montantTotalArticles", myNamespace, "").addTextNode(montantTotalArticles);
		soapin.addChildElement("nbrTotalArticles", myNamespace, "").addTextNode(nbrTotalArticles);

		SOAPElement panierClient = soapin.addChildElement("panierClient", myNamespace, "");
		SOAPElement PanierClient_Type = panierClient.addChildElement("PanierClient_Type", myNamespace, "");

		PanierClient_Type.addChildElement("idArticle", myNamespace, "").addTextNode(idArticle);
		PanierClient_Type.addChildElement("creanceId", myNamespace, "").addTextNode(creanceId);
		PanierClient_Type.addChildElement("refClient", myNamespace, "").addTextNode(refClient);
		PanierClient_Type.addChildElement("description", myNamespace, "").addTextNode(description);
		PanierClient_Type.addChildElement("montant", myNamespace, "").addTextNode(montantTotalArticles);
		PanierClient_Type.addChildElement("Etat", myNamespace, "").addTextNode("0");
		PanierClient_Type.addChildElement("codeRetour", myNamespace, "").addTextNode("000");
		
		soapin.addChildElement("refFatourati", myNamespace, "").addTextNode("");
		soapin.addChildElement("sysPmtCible", myNamespace, "").addTextNode("");

	}

	private static String getMACValue(String creancierId, String dateServeurCreancier, String action,
			String nbrTotalArticles, String montantTotalArticles, String idArticle) {
		String response = "";
		// creancierId + dateServeurCreancier + action + nbrTotalArticles
		// + montantTotalArticles +
		// la concatenation des IdArticle de la liste des panierClient
		// the concatenation of ItemIDs from the Customer cart list
		// + Secret Key
		// => hash MD5
		
		try {
			String plusOperator = "";
			String Secret_Key = "D97C7F423D7A89F7592C708FEFA03901";
			String str = creancierId + plusOperator + dateServeurCreancier + plusOperator + action + plusOperator + nbrTotalArticles + plusOperator
					+ montantTotalArticles + idArticle + plusOperator + Secret_Key;
			// Encode data on your side using BASE64
			byte[] bytesEncoded = Base64.encodeBase64(str.getBytes());
			System.out.println("encoded value is " + new String(bytesEncoded));
			response = new String(bytesEncoded);

			// Decode data on other side, by processing encoded data
			byte[] valueDecoded = Base64.decodeBase64(bytesEncoded);
			System.out.println("Decoded value is " + new String(valueDecoded));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}

	private static String getCurrentDateStr() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYYMMDDhhmmSS");
		return simpleDateFormat.format(new Date());
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

			ByteArrayOutputStream out = new ByteArrayOutputStream();
			soapResponse.writeTo(out);
			response = new String(out.toByteArray());
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
