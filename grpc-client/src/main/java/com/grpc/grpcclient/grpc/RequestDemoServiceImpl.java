package com.grpc.grpcclient.grpc;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.google.api.HttpBody;
import com.google.protobuf.ByteString;
import com.google.protobuf.Descriptors;
import com.grpcserver.grpc.DemoRequest;
import com.grpcserver.grpc.DemoResponse;
import com.grpcserver.grpc.RequestDemoServiceGrpc.RequestDemoServiceImplBase;

import io.grpc.stub.StreamObserver;

@Service
public class RequestDemoServiceImpl extends RequestDemoServiceImplBase {

	@Override
	public void getRequestDetails(DemoRequest request, StreamObserver<DemoResponse> responseObserver) {

		StringBuilder str = new StringBuilder();

		str.append("Request Parameter: ");
		str.append(request.getReqParam1());
		str.append(request.getReqParam2());
		str.append(" ").toString();

		DemoResponse response = DemoResponse.newBuilder().setResParam1(str.toString()).build();

		responseObserver.onNext(response);
		responseObserver.onCompleted();
	}

	@Override
	public void getRequestBodyDetails(HttpBody body, StreamObserver<DemoResponse> responseObserver) {

		try {
			JSONObject json = new JSONObject();
			StringBuilder str = new StringBuilder();

			str.append("Request Parameter: ");

			Map<Descriptors.FieldDescriptor, Object> allFields = body.getAllFields();
			for (Descriptors.FieldDescriptor fd : allFields.keySet()) {
				System.out.println(">>>>>>>>>>>>>>> FIELD >>>>>>>>>>>>>  " + fd.getName());

				Object o = allFields.get(fd);
				System.out.println(o);

				if (o instanceof ByteString) {
					ByteString bs = (ByteString) allFields.get(fd);
					System.out.println(bs.toStringUtf8());

					String queryString = bs.toStringUtf8();
					String[] pairs = queryString.split("&");

					for (String pair : pairs) {
						int idx = pair.indexOf("=");

						json.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"),URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
					}
				}
			}

			str.append(" ").toString();

			DemoResponse response = DemoResponse.newBuilder().setResParam1(json.toString()).build();

			responseObserver.onNext(response);
			responseObserver.onCompleted();

		} catch (UnsupportedEncodingException e) {			
			e.printStackTrace();
		} catch (JSONException e) {			
			e.printStackTrace();
		}
	}

}