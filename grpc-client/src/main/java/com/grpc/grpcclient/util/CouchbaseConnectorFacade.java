package com.grpc.grpcclient.util;

public class CouchbaseConnectorFacade {

	private static CouchbaseConnector aeroPayBucketConnector;
	private static CouchbaseConnector aeroAgentBucketConnector;
	private static CouchbaseConnector ifgBucketConnector;
	private static CouchbaseConnector aeroPayUIBucketConnector;
	private static CouchbaseConnector aeroPayMasterBucketConnector;
	private static CouchbaseConnector aeroPayMasterMockBucketConnector;

	public static CouchbaseConnector getAeroPayBucketConnector() {

		if (aeroPayBucketConnector == null) {

			aeroPayBucketConnector = CouchbaseConnector.builder().setBucketName("aero-pay").build();
		}

		return aeroPayBucketConnector;
	}

	public static CouchbaseConnector getAeroAgentBucketConnector() {

		if (aeroAgentBucketConnector == null) {

			aeroAgentBucketConnector = CouchbaseConnector.builder().setBucketName("aero-agent-mock").build();
		}

		return aeroAgentBucketConnector;
	}

	public static CouchbaseConnector getIFGBucketConnector() {

		if (ifgBucketConnector == null) {

			ifgBucketConnector = CouchbaseConnector.builder().setBucketName("ifg-mock").build();
		}

		return ifgBucketConnector;
	}

	public static CouchbaseConnector getAeroPayUIBucketConnector() {

		if (aeroPayUIBucketConnector == null) {

			aeroPayUIBucketConnector = CouchbaseConnector.builder().setBucketName("aero-pay-ui").build();
		}

		return aeroPayUIBucketConnector;
	}

	public static CouchbaseConnector getAeroPayMasterBucketConnector() {

		if (aeroPayMasterBucketConnector == null) {

			aeroPayMasterBucketConnector = CouchbaseConnector.builder().setBucketName("aero-pay-master").build();
		}

		return aeroPayMasterBucketConnector;
	}

	public static CouchbaseConnector getAeroPayMasterMockBucketConnector() {

		if (aeroPayMasterMockBucketConnector == null) {

			aeroPayMasterMockBucketConnector = CouchbaseConnector.builder().setBucketName("aero-master-mock").build();
		}

		return aeroPayMasterMockBucketConnector;
	}
}
