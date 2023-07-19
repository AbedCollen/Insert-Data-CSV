/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ujisistemc.postgre;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

///**
// *
// * @author Jessica
// */
public class InsertTable {


    public void InsertTable(String folderpath, String tablename) { //string folderpat menampung nama folerder dan tablename untu menampung nama table




        try {

            SingleConnectionDataSource ds = new SingleConnectionDataSource();
            ds.setDriverClassName("org.postgresql.Driver");
            ds.setUrl("jdbc:postgresql://localhost:5432/postgres");
            ds.setUsername("postgres");
            ds.setPassword("abed");
            // Establish the database connection
            FolderReader folderReader = new FolderReader();
            List<String> filename = folderReader.getFileName(folderpath);

            for (String file : filename) {
                String csvSeparator = ",";
                Map<String, Object> dataMap = new HashMap<>();

                String filepath = folderpath + file;

                String line;
                // membaca file csv
                try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
                    // membaca nama kolom
                    line = br.readLine();
                    String[] headers = line.split(csvSeparator);
                    // membaca data kolom
                    while ((line = br.readLine()) != null) {
                        String[] values = line.split(csvSeparator);
                        //menambahkan data baris csv ke map
                        for (int i = 0; i < headers.length; i++) {
                            dataMap.put(headers[i], values[i]);
                        }
                        //membuat postgre querry berdasarkan data map dan nama table
                        QueryGenerator queryGenerator = new QueryGenerator();
                        String query = queryGenerator.buildInsertQuery(tablename,dataMap);
                        // mengeksekusi querry ke database
                        JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
                        jdbcTemplate.execute(query);
                        long currentTimeMillis = System.currentTimeMillis();
                        System.out.println(currentTimeMillis);






                        // Clear the map for the next iteration
                        dataMap.clear();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            ds.destroy();

        } catch (Exception e) {
            System.out.println(e);
        }
            long currentTimeMillis = System.currentTimeMillis();
            System.out.println("Current time in milliseconds: " + currentTimeMillis);
            // Ubah milliseconds menjadi detik
            double seconds = currentTimeMillis / 1000.0;
            // Jarak yang ditempuh dalam meter
            double distanceInMeters = 1000.0; // Misalnya, 1000 meter
            // Hitung kecepatan dalam meter per detik
            double speedInMetersPerSecond = distanceInMeters / seconds;
            System.out.println("Speed in meters per second: " + speedInMetersPerSecond);
    }
}

