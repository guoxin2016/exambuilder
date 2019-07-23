package com.jason.exam.dao.config;

import java.sql.SQLException;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.stereotype.Component;

import com.alibaba.druid.pool.DruidDataSource;

@Configuration
public class DruidConfig {
	private static final String DB_PREFIX = "spring.datasource";

	@Component
	@ConfigurationProperties(prefix = DB_PREFIX)
	class IDataSourceProperties {
		private String url;
		private String username;
		private String password;
		private String driverClassName;
		private int initialSize;
		private int minIdle;
		private int maxActive;
		private int maxWait;
		private int timeBetweenEvictionRunsMillis;
		private String validationQuery;
		private boolean testWhileIdle;
		private boolean testOnBorrow;
		private boolean testOnReturn;
		private boolean poolPreparedStatements;
		private int maxPoolPreparedStatementPerConnectionSize;
		private String filters;
		private String connectionProperties;
		private String maxEvictableIdleTimeMillis;

		@Bean // 声明其为Bean实例
		@Primary // 在同样的DataSource中，首先使用被标注的DataSource
		public DataSource dataSource() {
			DruidDataSource datasource = new DruidDataSource();
			datasource.setUrl(url);
			datasource.setUsername(username);
			datasource.setPassword(password);
			datasource.setDriverClassName(driverClassName);
			// configuration
			datasource.setInitialSize(initialSize);
			datasource.setMinIdle(minIdle);
			datasource.setMaxActive(maxActive);
			datasource.setMaxWait(maxWait);
			datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
			datasource.setValidationQuery(validationQuery);
			datasource.setTestWhileIdle(testWhileIdle);
			datasource.setTestOnBorrow(testOnBorrow);
			datasource.setTestOnReturn(testOnReturn);
			datasource.setPoolPreparedStatements(poolPreparedStatements);
			datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
			/**setMinEvictableIdleTimeMillis需要先设置*/

			datasource.setMinEvictableIdleTimeMillis(31000);
			datasource.setMaxEvictableIdleTimeMillis(62000);
		 
			
			try {
				datasource.setFilters(filters);
			} catch (SQLException e) {
				System.err.println("druid configuration initialization filter: " + e);
			}
			datasource.setConnectionProperties(connectionProperties);
			return datasource;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getDriverClassName() {
			return driverClassName;
		}

		public void setDriverClassName(String driverClassName) {
			this.driverClassName = driverClassName;
		}

		public int getInitialSize() {
			return initialSize;
		}

		public void setInitialSize(int initialSize) {
			this.initialSize = initialSize;
		}

		public int getMinIdle() {
			return minIdle;
		}

		public void setMinIdle(int minIdle) {
			this.minIdle = minIdle;
		}

		public int getMaxActive() {
			return maxActive;
		}

		public void setMaxActive(int maxActive) {
			this.maxActive = maxActive;
		}

		public int getMaxWait() {
			return maxWait;
		}

		public void setMaxWait(int maxWait) {
			this.maxWait = maxWait;
		}

		public int getTimeBetweenEvictionRunsMillis() {
			return timeBetweenEvictionRunsMillis;
		}

		public void setTimeBetweenEvictionRunsMillis(int timeBetweenEvictionRunsMillis) {
			this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
		}

		public String getValidationQuery() {
			return validationQuery;
		}

		public void setValidationQuery(String validationQuery) {
			this.validationQuery = validationQuery;
		}

		public boolean isTestWhileIdle() {
			return testWhileIdle;
		}

		public void setTestWhileIdle(boolean testWhileIdle) {
			this.testWhileIdle = testWhileIdle;
		}

		public boolean isTestOnBorrow() {
			return testOnBorrow;
		}

		public void setTestOnBorrow(boolean testOnBorrow) {
			this.testOnBorrow = testOnBorrow;
		}

		public boolean isTestOnReturn() {
			return testOnReturn;
		}

		public void setTestOnReturn(boolean testOnReturn) {
			this.testOnReturn = testOnReturn;
		}

		public boolean isPoolPreparedStatements() {
			return poolPreparedStatements;
		}

		public void setPoolPreparedStatements(boolean poolPreparedStatements) {
			this.poolPreparedStatements = poolPreparedStatements;
		}

		public int getMaxPoolPreparedStatementPerConnectionSize() {
			return maxPoolPreparedStatementPerConnectionSize;
		}

		public void setMaxPoolPreparedStatementPerConnectionSize(int maxPoolPreparedStatementPerConnectionSize) {
			this.maxPoolPreparedStatementPerConnectionSize = maxPoolPreparedStatementPerConnectionSize;
		}

		public String getFilters() {
			return filters;
		}

		public void setFilters(String filters) {
			this.filters = filters;
		}

		public String getConnectionProperties() {
			return connectionProperties;
		}

		public void setConnectionProperties(String connectionProperties) {
			this.connectionProperties = connectionProperties;
		}
	}

	private void getPropertiesFromSource(PropertySource<?> propertySource, Map<String, Object> map) {
		if (propertySource instanceof MapPropertySource) {
			for (String key : ((MapPropertySource) propertySource).getPropertyNames()) {
				if (key.startsWith(DB_PREFIX))
					map.put(key.replaceFirst(DB_PREFIX + ".", ""), propertySource.getProperty(key));
				else if (key.startsWith(DB_PREFIX))
					map.put(key.replaceFirst(DB_PREFIX + ".", ""), propertySource.getProperty(key));
			}
		}

		if (propertySource instanceof CompositePropertySource) {
			for (PropertySource<?> s : ((CompositePropertySource) propertySource).getPropertySources()) {
				getPropertiesFromSource(s, map);
			}
		}
	}

}
