package method;

import java.util.List;

import model.AdjustMessage;
import model.Message;
import model.Sale;

public interface ParserI {

	public String parseMessageToString(Message message);
	
	public String parseAdjustmentMessageListToString(List<AdjustMessage> adjMessageList);

	public Sale getSaleFromMessage(Message message);

	public String parseSaleListToString(List<Sale> saleList);

	public String parseAdjustAttributesToString(AdjustMessage adjMessage);

	public String parseSaleAdjustHistoryToString(List<Sale> saleAdjustHistoryList);
	
	public String parseSaleSummaryListToString(List<Sale> saleSummaryList);
}
