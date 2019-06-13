package pl.madejski.dao;

import pl.madejski.model.*;
import java.util.List;
import java.util.stream.Collectors;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ProductDao {

	private List<Product> products;
	
	public ProductDao() {
		products = new ArrayList<>();
		init();
	}
	
	public void init() {
		try {
			InitialContext ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("jdbc/SQLServer");
			
			Connection conn = ds.getConnection();
			Statement stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery("SELECT productid, productname FROM northwind.dbo.products");
			while (rs.next()) {
				products.add(new Product(
						Long.parseLong(rs.getString("productid")),
						rs.getString("productname")
				));
			}
			
			stmt.close();
			conn.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<Product> findAll() {
		return products;
	}
	
	public List<Product> findByName(String name) {
		return products.stream()
				.filter(p -> p.getName().toLowerCase().contains(name.toLowerCase()))
				.collect(Collectors.toList());
	}
	
	public void save(Product product) {
		// TODO: init JDBC connection once/move create somewhere else
		try {
			InitialContext ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("jdbc/SQLServer");
			
			Connection conn = ds.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO northwind.dbo.products (productname) VALUES (?)");
			
			
			pstmt.setString(1, product.getName());
			
			if(pstmt.executeUpdate() != 1) { // shouldn't insert more than 1 row
				pstmt.close();
				conn.close();
				throw new Exception("Wyst¹pi³ b³¹d podczas dodawania produktu!");
			}
			
			pstmt.close();
			conn.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
