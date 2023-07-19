/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ujisistemc.postgre;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

/**
 *
 * @author Jessica
 */
public class CreateTable {
    final static String lineMapPath = "map/priceline.txt";
    final static String tableMapPath = "map/pricetable.txt";
    private JdbcTemplate jdbcTemplate;
    
    
    public String CreateAllTable(String url, String user, String pass){
        String status =null;
        //Convert txt to Hashmap
        Map<String, String> line_map = HashMapFromTextFile(lineMapPath);
        Map<String, String> table_map = HashMapFromTextFile(tableMapPath);
        try {
            CreateTable(line_map,"PRICELINE",url,user,pass);
            CreateTable(table_map,"PRICETABLE",url,user,pass);
            status = "Table Created Succesfully";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }
    
    public static Map<String, String> HashMapFromTextFile(String filePath)
    {
        Map<String, String> map = new HashMap<String, String>();
        BufferedReader br = null;
        try {
            // create file object
            File file = new ClassPathResource(filePath).getFile();
            // create BufferedReader object from the File
            br = new BufferedReader(new FileReader(file));
            String line = null;
            // read file line by line
            while ((line = br.readLine()) != null) {
                // split the line by :
                String[] parts = line.split(";");
                // first part is name, second is number
                String name = parts[0].trim();
                String number = parts[1].trim();
                // put name, number in HashMap if they are
                // not empty
                if (!name.equals("") && !number.equals(""))
                    map.put(name, number);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            // Always close the BufferedReader
            if (br != null) {
                try {
                    br.close();
                }
                catch (Exception e) {
                };
            }
        }
        return map;
    }
    
    public void CreateTable(Map<String,String> map,String tablename, String url, String user, String pass){
        try {
            SingleConnectionDataSource ds = new SingleConnectionDataSource();
            ds.setDriverClassName("org.postgresql.Driver");
            ds.setUrl(url);
            ds.setUsername(user);
            ds.setPassword(pass);
            
            jdbcTemplate = new JdbcTemplate( ds);
            //jdbcTemplate.execute("DROP TABLE IF EXIST"+ tablename);            
            jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS "+ tablename +"(id bigint)");

            for (Map.Entry<String, String> entry : map.entrySet()) {
                if(entry.getValue().equals("Varchar")){
                    jdbcTemplate.execute("ALTER TABLE " + tablename + " ADD if not exists " + entry.getKey() + " "+entry.getValue()+"(255)");
                }else{                
                    jdbcTemplate.execute("ALTER TABLE " + tablename + " ADD if not exists " + entry.getKey() + " "+entry.getValue());
                }
            }
            ds.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }   
}
