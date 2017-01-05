package com.db.mongod.core.config;

import com.mongodb.annotations.Immutable;

@Immutable
public class Configuration {
	/*
	 * configure the mongo t here.
	 */
	public final String host = "127.0.0.1";
	public final int port = 27017;
	public final String[] servers = {};//eg.localhost:20000.if it has element,the host and port is useless.
	public final String gridfsDbName = "fs";
	public final String defaultDbName = "test";
}
