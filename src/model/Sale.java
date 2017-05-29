package model;

import java.util.UUID;

/**
 * 
 * @author Sena Bak
 *         <p>
 *         The unique identifier of a Sale record is itemCode and id.<br>
 *         effectSquence increments by 1 every time the sale with the same
 *         itemCode and id is updated
 */
public class Sale {
	/**
	 * id is generated using UUID.randomUUID().
	 */
	private String id;
	private String itemCode;
	private double price;
	private int quantity;
	private double totalPrice;
	/**
	 * The time that the sale was made. 
	 */
	private long saleTimeMillis;

	private int effectSquence;

	public Sale(String id, String itemCode, double price, int quantity, double totalPrice, long saleTimeMillis,
			int effectSquence) {

		this.id = id!=null ? UUID.randomUUID().toString():id;
		this.itemCode = itemCode;
		this.price = price;
		this.quantity = quantity;
		this.totalPrice = totalPrice;
		this.saleTimeMillis = saleTimeMillis;
		this.effectSquence = effectSquence;
	}
	
   
	public Sale() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public long getSaleTimeMillis() {
		return saleTimeMillis;
	}

	public void setSaleTimeMillis(long saleTimeMillis) {
		this.saleTimeMillis = saleTimeMillis;
	}

	public int getEffectSquence() {
		return effectSquence;
	}

	public void setEffectSquence(int effectSquence) {
		this.effectSquence = effectSquence;
	}
	

}
