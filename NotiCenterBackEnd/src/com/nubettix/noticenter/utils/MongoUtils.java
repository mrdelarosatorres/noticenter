package com.nubettix.noticenter.utils;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoUtils {
	private String host;
	private int port = 0;
	private boolean devmode = true;

	private MongoUtils() {
		if (devmode) {
			host = "localhost";
			port = 27017;
		}
	}

	/**
	 * Obtiene la conexion a la bd mongo
	 * 
	 * @return
	 */
	public MongoClient getConnection() {
		return new MongoClient(host, port);
	}

	public static void main(String[] args) {
		MongoClient mongo = new MongoClient("localhost", 27017);

		MongoDatabase db = mongo.getDatabase("noticenter");
		MongoCollection t = db.getCollection("");
	}

	/**
	 * Obtiene una instancia de MongoUtils
	 * 
	 * @return
	 */
	public static MongoUtils getInstance() {
		return new MongoUtils();
	}
}
