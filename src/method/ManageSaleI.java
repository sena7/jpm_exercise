package method;

import java.util.List;
import java.util.Set;

import model.AdjustMessage;
import model.Sale;

public interface ManageSaleI {
  public List<Sale> addAdjustedSales(List<Sale> saleList, List<Sale> currentSaleList, AdjustMessage adjMessage);
  public List<Sale> aggregateSalesByItemCode(List<Sale> saleList);
  public List<Sale> getSalesByItemCode(List<Sale> saleList, String itemCode);
  public Set<String> getItemCodeSet(List<Sale> saleList);
  public List<Sale> getSaleAdjustList(List<Sale> saleList, String itemCode, String id);
  
}
