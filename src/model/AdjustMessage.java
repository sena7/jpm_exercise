package model;

import model.constant.AdjustOperator;

/**
 * 
 * @author Sena Bak
 * 
 */
public class AdjustMessage extends Message {

	private AdjustOperator priceAdjustOperator;
	private double priceAdjustValue;
	private AdjustOperator quantityAdjustOperator;
	private double quantityAdjustValue;
	/**
	 * adjustmentAmount for adjustmentType MUTIPLY is limited to be a double value smaller than 1 and greater than 0
	 */
	

	public AdjustMessage(){}
	public AdjustMessage(AdjustOperator priceAdjustOperator, double priceAdjustValue,
			AdjustOperator quantityAdjustOperator, double quantityAdjustValue) {
		super();
		this.priceAdjustOperator = priceAdjustOperator;
		this.priceAdjustValue = priceAdjustValue;
		this.quantityAdjustOperator = quantityAdjustOperator;
		this.quantityAdjustValue = quantityAdjustValue;
	}
	public AdjustOperator getPriceAdjustOperator() {
		return priceAdjustOperator;
	}
	public void setPriceAdjustOperator(AdjustOperator priceAdjustOperator) {
		this.priceAdjustOperator = priceAdjustOperator;
	}
	public double getPriceAdjustValue() {
		return priceAdjustValue;
	}
	public void setPriceAdjustValue(double priceAdjustValue) {
		this.priceAdjustValue = priceAdjustValue;
	}
	public AdjustOperator getQuantityAdjustOperator() {
		return quantityAdjustOperator;
	}
	public void setQuantityAdjustOperator(AdjustOperator quantityAdjustOperator) {
		this.quantityAdjustOperator = quantityAdjustOperator;
	}
	public double getQuantityAdjustValue() {
		return quantityAdjustValue;
	}
	public void setQuantityAdjustValue(double quantityAdjustValue) {
		this.quantityAdjustValue = quantityAdjustValue;
	}
	@Override
	public String toString() {
		return "AdjustMessage [priceAdjustOperator=" + priceAdjustOperator + ", priceAdjustValue=" + priceAdjustValue
				+ ", quantityAdjustOperator=" + quantityAdjustOperator + ", quantityAdjustValue=" + quantityAdjustValue
				+ "]";
	}

	
	


	

}
