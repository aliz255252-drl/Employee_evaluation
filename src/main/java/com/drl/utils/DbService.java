package com.drl.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;

@Service
public class DbService {

    @Autowired
    private DataSource dataSource;

    public void testConnection() {
        try (Connection conn = dataSource.getConnection()) {
            DatabaseMetaData metaData = conn.getMetaData();
            System.out.println("✅ Connected to DB: " + metaData.getURL());
            System.out.println("🧠 DB Product: " + metaData.getDatabaseProductName());
            System.out.println("🎯 DB Version: " + metaData.getDatabaseProductVersion());
        } catch (Exception e) {
            System.err.println("❌ Failed to connect to DB: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
