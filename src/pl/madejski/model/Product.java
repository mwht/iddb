package pl.madejski.model;

public class Product {
	
	private Long productId;
	private String name;
	
	
	public Product(Long productId, String name) {
		this.productId = productId;
		this.name = name;
	}
	
	public Long getProductId() {
		return productId;
	}
	
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
}
