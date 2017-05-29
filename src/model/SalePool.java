package model;

import java.util.ArrayList;
import java.util.List;

import method.Helper;
import method.ManageSale;
import model.constant.AdjustOperator;
import test.SampleData;

public class SalePool {
	private Helper helper = new Helper();

	private ManageSale manageSale = new ManageSale();

	public int SaleCount = 0;
	private List<Sale> saleList;
	private List<Sale> currentSaleList;

	SalePool() {

		saleList = new ArrayList<>();
		currentSaleList = new ArrayList<>();
	}

	public void addSale(Sale s) {
		this.saleList.add(s);
		this.currentSaleList.add(s);
		this.SaleCount += 1;
	}

	public List<Sale> getSaleList() {
		return saleList;
	}
	
	public List<Sale> getCurrentSaleList(){
		return currentSaleList;
	}

	/**
	 * 
	 * @param adjMessage <p>
	 * Price and Quantity will be adjusted only when the adjusted values are not contradicting the prerequisites as below.<p>
	 * Price Adjustment:<br>
	 *  Pre-adjustment price and the adjusted price should be greater than the MIN_PRICE (50). <br><br>
	 * Quantity Adjustment:<br>
	 *  When pre-adjustment quantity = 1 ; adjustment is not accepted and passed. <br>
	 *  When pre-adjustment quantity > 1 ; adjustment is accepted only when the adjusted quantity is greater than 1.<br> 
	 */

	public List<Sale> addAdjustedSales(AdjustMessage adjMessage) {

		List<Sale> adjustedSalesList = new ArrayList<>();

		String itemCode = adjMessage.getSale().getItemCode();

		AdjustOperator priceAdjustOperator = adjMessage.getPriceAdjustOperator();
		double priceAdjValue = adjMessage.getPriceAdjustValue();

		AdjustOperator quantityAdjustOperator = adjMessage.getQuantityAdjustOperator();
		double quantityAdjValue = adjMessage.getQuantityAdjustValue();
		
		// Update currentSaleList and add the element to saleList
		for (Sale sale : currentSaleList) {

			if (sale.getItemCode() == itemCode) {
				Sale adjSale = new Sale();
				adjSale.setId(sale.getId());
				adjSale.setItemCode(sale.getItemCode());
				adjSale.setSaleTimeMillis(sale.getSaleTimeMillis());
				adjSale.setPrice(sale.getPrice());
				adjSale.setQuantity(sale.getQuantity());

				double oldPrice = sale.getPrice();
				double newPrice = priceAdjustOperator == AdjustOperator.MUTIPLY ? oldPrice * priceAdjValue
						: oldPrice + priceAdjValue;
				if (oldPrice > SampleData.MIN_PRICE && newPrice >= SampleData.MIN_PRICE) {

					adjSale.setPrice(newPrice);
				}

              
				if (sale.getQuantity() > 1) {
					int oldQuantity = sale.getQuantity();
					int newQuantity = (int) helper.roundDownDouble((quantityAdjustOperator == AdjustOperator.MUTIPLY
							? oldQuantity * quantityAdjValue : oldQuantity + quantityAdjValue), 0);
					if (newQuantity > 1) {
						adjSale.setQuantity(newQuantity);
					}
				}

				adjSale.setTotalPrice(adjSale.getPrice() * adjSale.getQuantity());

				if (adjSale.getPrice() != sale.getPrice() || adjSale.getQuantity() != sale.getQuantity()
						|| adjSale.getTotalPrice() != sale.getTotalPrice()) {

					adjSale.setEffectSquence(sale.getEffectSquence() + 1);
					saleList.add(adjSale);
					currentSaleList.set(currentSaleList.indexOf(sale), adjSale);
					adjustedSalesList.add(adjSale);
				}

			}

		}
	
		return adjustedSalesList;

	}

	public List<Sale> getUpdatedSaleListByItemCode(String itemCode) {
		List<Sale> saleList = manageSale.getSalesByItemCode(currentSaleList, itemCode);
		List<Sale> notInScope = new ArrayList<>();
		for (Sale s : saleList) {
			if (s.getEffectSquence() == 1) {
				notInScope.add(s);
			}
		}
		saleList.removeAll(notInScope);
		return saleList;

	}

}
