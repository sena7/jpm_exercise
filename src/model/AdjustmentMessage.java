package model;

import model.constant.AdjustmentOperator;

/**
 * 
 * @author Sena Bak
 * 
 */
public class AdjustmentMessage extends Message {

	private AdjustmentOperator priceAdjustOperator;
	private double priceAdjustmentValue;
	private AdjustmentOperator quantityAdjustOperator;
	private double quantityAdjustmentValue;
	/**
	 * adjustmentAmount for adjustmentType MUTIPLY is limited to be a double value smaller than 1 and greater than 0
	 */
	

	public AdjustmentMessage(){}
	public AdjustmentMessage(AdjustmentOperator priceAdjustOperator, double priceAdjustmentValue,
			AdjustmentOperator quantityAdjustOperator, double quantityAdjustmentValue) {
		super();
		this.priceAdjustOperator = priceAdjustOperator;
		this.priceAdjustmentValue = priceAdjustmentValue;
		this.quantityAdjustOperator = quantityAdjustOperator;
		this.quantityAdjustmentValue = quantityAdjustmentValue;
	}
	public AdjustmentOperator getPriceAdjustOperator() {
		return priceAdjustOperator;
	}
	public double getPriceAdjustmentValue() {
		return priceAdjustmentValue;
	}
	public AdjustmentOperator getQuantityAdjustOperator() {
		return quantityAdjustOperator;
	}
	public double getQuantityAdjustmentValue() {
		return quantityAdjustmentValue;
	}
	public void setPriceAdjustOperator(AdjustmentOperator priceAdjustOperator) {
		this.priceAdjustOperator = priceAdjustOperator;
	}
	public void setPriceAdjustmentValue(double priceAdjustmentValue) {
		this.priceAdjustmentValue = priceAdjustmentValue;
	}
	public void setQuantityAdjustOperator(AdjustmentOperator quantityAdjustOperator) {
		this.quantityAdjustOperator = quantityAdjustOperator;
	}
	public void setQuantityAdjustmentValue(double quantityAdjustmentValue) {
		this.quantityAdjustmentValue = quantityAdjustmentValue;
	}
	@Override
	public String toString() {
		return "AdjustmentMessage [priceAdjustOperator=" + priceAdjustOperator + ", priceAdjustmentValue="
				+ priceAdjustmentValue + ", quantityAdjustOperator=" + quantityAdjustOperator
				+ ", quantityAdjustmentValue=" + quantityAdjustmentValue + "]";
	};
	


	

}
