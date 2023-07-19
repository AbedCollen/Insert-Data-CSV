package com.ujisistemc.postgre;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class PostgreApplication implements CommandLineRunner{

    JdbcTemplate jdbcTemplate;
	static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
        static final String USER = "postgres";
        static final String PASS = "abed";
        
        
	public static void main(String[] args) {
		SpringApplication.run(PostgreApplication.class, args);
	}

        @Override
        public void run(String... args) throws Exception {
            CreateTable createTable = new CreateTable();
            InsertTable insertTable = new InsertTable();

            System.out.println(createTable.CreateAllTable(DB_URL,USER,PASS));

            String priceline = "C:\\ujisistemc\\Data-3\\salespriceline\\";
            String pricetable = "C:\\ujisistemc\\Data-3\\salespricetable\\";

            String tableprice = "priceline";
            String tabletable = "pricetable";

            insertTable.InsertTable(priceline, tableprice);
            insertTable.InsertTable(pricetable, tabletable);
        }
}
