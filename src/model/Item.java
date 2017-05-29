package model;

/**
 * 
 * @author Sena Bak
 *         <p>
 *         Prerequisites of the design: <br>
 *         1. The unique key of a record is code and effectTimeMillis <br>
 *         2. The only way to know that a certain item price has changed is when
 *         the message is received <br>
 *         3. There is no error with the message regarding the latest updated
 *         item price. <br>
 */
public class Item {
	/**
	 * It represents the unique id of each type of item.
	 */
	private String code;
	private double price;
	/**
	 * For simplification of the model, it substituted any fields which could
	 * represent the uniqueness of the data record in conjunction with the field
	 * 'code'.
	 * 
	 */
	private long effectTimeMillis;
	/**
	 * effectSequence indicates the version of the item. Price is updated when
	 * Adjustment Message is created.
	 */
	private int effectSequence;

	public Item(){}
	public Item(String code, double price, long effectTimeMillis, int effectSequence) {
		super();
		this.code = code;
		this.price = price;
		this.effectTimeMillis = effectTimeMillis;
		this.effectSequence = effectSequence;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public long getEffectTimeMillis() {
		return effectTimeMillis;
	}

	public void setEffectTimeMillis(long effectTimeMillis) {
		this.effectTimeMillis = effectTimeMillis;
	}

	public int getEffectSequence() {
		return effectSequence;
	}

	public void setEffectSequence(int effectSequence) {
		this.effectSequence = effectSequence;
	}

}
