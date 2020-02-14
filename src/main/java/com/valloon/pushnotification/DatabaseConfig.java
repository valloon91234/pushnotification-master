package com.valloon.pushnotification;

/**
 * @author Valloon Project
 * @version 1.0 @2019-10-11
 */
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.net.URI;
import java.net.URISyntaxException;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

	private static final String DB_URL = "jdbc:mysql://localhost:3306/pushnotification?autoReconnect=true&useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true&useSSL=false";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "root";

	@Value("${JAWSDB_MARIA_URL:#{null}}")
	private String dbUrl;

	@Bean
	public DataSource dataSource() throws URISyntaxException {
		if (dbUrl == null) {
			HikariConfig config = new HikariConfig();
			config.setJdbcUrl(DB_URL);
			config.setUsername(USERNAME);
			config.setPassword(PASSWORD);
			return new HikariDataSource(config);
		} else {
			URI jdbUri = new URI(dbUrl);
			String username = jdbUri.getUserInfo().split(":")[0];
			String password = jdbUri.getUserInfo().split(":")[1];
			String port = String.valueOf(jdbUri.getPort());
			String jdbUrl = "jdbc:mysql://" + jdbUri.getHost() + ":" + port + jdbUri.getPath();
			HikariConfig config = new HikariConfig();
			config.setJdbcUrl(jdbUrl);
			config.setUsername(username);
			config.setPassword(password);
			return new HikariDataSource(config);
		}
	}
}