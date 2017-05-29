package method;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import model.AdjustMessage;
import model.Sale;
import model.constant.AdjustOperator;

public class ManageSale implements ManageSaleI {

	@Override
	public List<Sale> addAdjustedSales(List<Sale> saleList, List<Sale> currentSaleList, AdjustMessage adjMessage) {

		String itemCode = adjMessage.getSale().getItemCode();

		AdjustOperator priceAdjustOperator = adjMessage.getPriceAdjustOperator();
		double priceAdjValue = adjMessage.getPriceAdjustValue();

		AdjustOperator quantityAdjustOperator = adjMessage.getPriceAdjustOperator();
		double quantityAdjValue = adjMessage.getPriceAdjustValue();

		for (Sale sale : currentSaleList) {
			
			if (sale.getItemCode() == itemCode) {
				Sale adjSale = sale;
				adjSale.setEffectSquence(sale.getEffectSquence() + 1);
				
				double oldPrice = sale.getPrice();
				
				double newPrice = 0d;
				
					newPrice = priceAdjustOperator == AdjustOperator.MUTIPLY ? oldPrice * priceAdjValue
						: oldPrice + priceAdjValue;
				adjSale.setPrice(newPrice);
				sale.setPrice(newPrice);
				// only modify the quantity when the quantity is not 1.
				if (sale.getQuantity() != 1) {
					int oldQuantity = sale.getQuantity();
					int newQuantity = (int) (quantityAdjustOperator == AdjustOperator.MUTIPLY
							? oldQuantity * quantityAdjValue : oldQuantity + quantityAdjValue);
					adjSale.setQuantity(newQuantity);
					sale.setQuantity(newQuantity);
				}

				adjSale.setTotalPrice(adjSale.getPrice() * adjSale.getQuantity());
				saleList.add(adjSale);

			}

		}

		return saleList;
	}

	@Override
	public List<Sale> aggregateSalesByItemCode(List<Sale> saleList) {
		ArrayList<Sale> aggregatedSaleList = new ArrayList<>();

		HashSet<String> itemCodeSet = (HashSet<String>) getItemCodeSet(saleList);
		Sale aggregatedSale;
		for (String itemCode : itemCodeSet) {
			aggregatedSale = new Sale();
			int quantity = 0;
			double totalPrice = 0d;

			List<Sale> saleListOfItem = getSalesByItemCode(saleList, itemCode);
			// TODO
			// update price of an item when the adjustment message generated
			// instead?
			// The unit price of all the sales for an item is identical.
			double price = saleListOfItem.get(0).getPrice();

			for (Sale s : saleListOfItem) {
				quantity += s.getQuantity();
				totalPrice += s.getTotalPrice();
			}

			aggregatedSale.setItemCode(itemCode);
			aggregatedSale.setPrice(price);
			aggregatedSale.setQuantity(quantity);
			aggregatedSale.setTotalPrice(totalPrice);
			aggregatedSaleList.add(aggregatedSale);

		}

		return aggregatedSaleList;
	}

	@Override
	public List<Sale> getSalesByItemCode(List<Sale> saleList, String itemCode) {
		List<Sale> itemSaleList = new ArrayList<>();
		for (Sale s : saleList) {
			if (s.getItemCode() == itemCode) {
				itemSaleList.add(s);
			}
		}
		return itemSaleList;
	}

	@Override
	public Set<String> getItemCodeSet(List<Sale> saleList) {
		HashSet<String> set = new HashSet<>();
		for (Sale sale : saleList) {
			set.add(sale.getItemCode());
		}
		return set;
	}

	@Override
	public List<Sale> getSaleAdjustList(List<Sale> saleList, String itemCode, String id) {
		List<Sale> saleAdjustList = new ArrayList<>();
		for(Sale s: saleList){
			if(s.getItemCode() == itemCode && s.getId() == id){
				saleAdjustList.add(s);
			}
		}
		return saleAdjustList;
	}

	
}
