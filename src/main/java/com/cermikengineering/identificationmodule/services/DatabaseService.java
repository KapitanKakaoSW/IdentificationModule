/*
package com.cermikengineering.identificationmodule.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

@Service
public class DatabaseService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DataSource dataSource;

    public void saveData(MyEntity entity) {
        try {

            jdbcTemplate.update("INSERT INTO my_table (column) VALUES (?)", entity.getColumn());
        } catch (Exception e) {

            DataSource h2DataSource = createH2DataSource();
            JdbcTemplate h2Template = new JdbcTemplate(h2DataSource);
            h2Template.update("INSERT INTO my_table (column) VALUES (?)", entity.getColumn());
        }
    }

    private DataSource createH2DataSource() {
        DataSourceProperties properties = new DataSourceProperties();
        properties.setUrl("jdbc:h2:file:./data/db;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
        properties.setDriverClassName("org.h2.Driver");
        properties.setUsername("sa");
        return properties.initializeDataSourceBuilder().build();
    }
}

*/
