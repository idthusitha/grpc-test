package com.grpc.grpcclient.util;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.query.N1qlQuery;
import com.couchbase.client.java.query.N1qlQueryResult;
import com.couchbase.client.java.query.N1qlQueryRow;

public class CouchbaseConnector {

	private final Bucket bucket;

	private CouchbaseConnector(Bucket bucket) {
		this.bucket = bucket;
	}

	public static Builder builder() {

		return new Builder();
	}

	public JsonDocument insert(String id, String jsonString) {

		JsonObject content = JsonObject.fromJson(jsonString);
		return insert(id, content);
	}

	public JsonDocument insert(String id, JsonObject jsonObject) {

		JsonDocument document = JsonDocument.create(id, jsonObject);
		return insert(document);
	}

	public JsonDocument insert(JsonDocument document) {

		sleep();
		JsonDocument d = bucket.upsert(document);
		// bucket.remove(document.id());
		// sleep();
		// JsonDocument d = bucket.insert(document, 1, TimeUnit.MINUTES);
		sleep();

		return d;
	}

	public JsonDocument retrieve(String id) {

		sleep();
		return bucket.get(id, 1, TimeUnit.MINUTES);
	}

	public JsonObject retrieveByFieldName(String propertyPath, String fieldName, String fieldValue) {

		if (fieldName == null || fieldName.isEmpty())
			return null;

		if (propertyPath == null || propertyPath.isEmpty()) {
			propertyPath = "*";
		}

		String lastKey = propertyPath;
		if (propertyPath.contains(".")) {
			String[] keys = propertyPath.split("\\.");
			lastKey = keys[keys.length - 1].toString();
		}

		String query = "SELECT " + propertyPath + " FROM `" + bucket.name() + "` WHERE " + fieldName + "='" + fieldValue
				+ "'";
		System.out.println(query);
		sleep();
		N1qlQueryResult result = bucket.query(N1qlQuery.simple(query), 1, TimeUnit.MINUTES);

		if (result.finalSuccess() && result.allRows().size() > 0) {

			JsonObject jsonObject = result.allRows().get(0).value();

			if (!lastKey.equals("*"))
				jsonObject = jsonObject.getObject(lastKey);
			return jsonObject;
		}

		return null;
	}

	public JsonDocument upsert(String id, String jsonString) {

		JsonObject content = JsonObject.fromJson(jsonString);
		return upsert(id, content);
	}

	public JsonDocument upsert(String id, JsonObject jsonObject) {

		JsonDocument document = JsonDocument.create(id, jsonObject);
		return upsert(document);
	}

	public JsonDocument upsert(JsonDocument document) {

		bucket.upsert(document);
		sleep(500);
		return document;
	}

	public JsonObject retrieveForQuery(String query) {

		// N1qlQuery n1qlQuery = Query.simple("SELECT * FROM beer-sample LIMIT 10");

		sleep();
		N1qlQueryResult result = bucket.query(N1qlQuery.simple(query), 1, TimeUnit.MINUTES);

		if (result.finalSuccess() && result.allRows().size() > 0) {

			JsonObject jsonObject = result.allRows().get(0).value();
			return jsonObject;
		}

		return null;
	}

	public JsonDocument remove(String id) {

		sleep();
		return bucket.remove(id);
	}

	private void sleep() {

		sleep(500);
	}

	private void sleep(long millis) {

		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public boolean flush() {

		sleep(500);
		final boolean isFlushed = removeAll();
		// Boolean isFlushed = bucket.bucketManager().flush();
		// Boolean isFlushed = bucket.bucketManager().flush(1, TimeUnit.MINUTES);

		sleep(500);
		return isFlushed;
	}

	public boolean removeAll() {

		final String bucketName = "`" + bucket.name() + "`";

		String query = "SELECT " + "META(" + bucketName + ").id FROM " + bucketName;

		// query= "SELECT * FROM "+"`"+bucket.name()+"`";

		System.out.println(query);
		N1qlQueryResult result = bucket.query(N1qlQuery.simple(query), 1, TimeUnit.MINUTES);

		final boolean isSuccess = result.finalSuccess();
		if (isSuccess && result.allRows().size() > 0) {

			Iterator<N1qlQueryRow> rows = result.rows();

			while (rows.hasNext()) {
				JsonObject jsonObject = rows.next().value();
				if (jsonObject.containsKey("id")) {
					bucket.remove(jsonObject.getString("id"));
				}
			}
		}

		return isSuccess;
	}

	static class Builder {

		private static Cluster cluster;

		private String host = "localhost";
		private String bucketName = "aero-pay";
		private String userName = "Administrator";
		private String password = "password";

		private Builder() {
		}

		public Builder setHost(String host) {
			this.host = host;
			return this;
		}

		public Builder setBucketName(String bucketName) {
			this.bucketName = bucketName;
			return this;
		}

		public Builder setUserName(String userName) {
			this.userName = userName;
			return this;
		}

		public Builder setPassword(String password) {
			this.password = password;
			return this;
		}

		public CouchbaseConnector build() {

			if (cluster == null) {

				cluster = CouchbaseCluster.create(host);
				cluster.authenticate(userName, password);
			}

			return new CouchbaseConnector(cluster.openBucket(bucketName, 1, TimeUnit.MINUTES));
		}
	}
}