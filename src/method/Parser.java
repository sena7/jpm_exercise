package method;

import java.sql.Timestamp;
import java.util.List;

import model.AdjustMessage;
import model.Message;
import model.Sale;
import model.constant.MessageType;

public class Parser implements ParserI {

	private static String lineBreak = "\n";
	private static String tabLv1 = "\t";
	private static String tabLv2 = "\t\t";
	private Helper helper = new Helper();

	@Override
	public String parseMessageToString(Message message) {

		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append("Message ID: " + message.getId());
		sBuffer.append(lineBreak);
		sBuffer.append("Message Type: " + message.getMessageType());
		sBuffer.append(lineBreak);
		sBuffer.append(tabLv1);
		sBuffer.append("Sale data: ");
		sBuffer.append(lineBreak);
		sBuffer.append(tabLv2 + "Item code: " + message.getSale().getItemCode() + lineBreak);
		sBuffer.append(tabLv2 + "Sale ID: " + message.getSale().getId() + lineBreak);
		sBuffer.append(tabLv2 + "Effective Sequence: " + message.getSale().getEffectSquence() + lineBreak);
		sBuffer.append(tabLv2 + "Sale Time: " + new Timestamp(message.getSale().getSaleTimeMillis()) + lineBreak);
		sBuffer.append(tabLv2 + "Unit price: " + helper.roundDownDouble(message.getSale().getPrice(), 0) + lineBreak);
		sBuffer.append(tabLv2 + "Quantity: " + message.getSale().getQuantity() + lineBreak);
		sBuffer.append(
				tabLv2 + "Total price: " + helper.roundDownDouble(message.getSale().getTotalPrice(), 0) + lineBreak);
		sBuffer.append(tabLv2 + "Effective Sequence: " + message.getSale().getEffectSquence() + lineBreak);

		String logText = "";
		MessageType messageType = message.getMessageType();

		if (messageType == MessageType.TYPE3) {
			sBuffer.append(parseAdjustAttributesToString((AdjustMessage) message));
		}

		logText = sBuffer.toString();
		return logText;
	}

	@Override
	public Sale getSaleFromMessage(Message message) {
		return message.getSale();
	}

	@Override
	public String parseSaleListToString(List<Sale> saleList) {
		StringBuffer sBuffer = new StringBuffer();
		for (Sale s : saleList) {
			sBuffer.append("Item Code: " + s.getItemCode() + lineBreak);
			sBuffer.append("Sale ID: " + s.getId() + lineBreak);
			sBuffer.append("Effective Sequence: " + s.getEffectSquence() + lineBreak);
			sBuffer.append("Sale Time: " + new Timestamp(s.getSaleTimeMillis()) + lineBreak);
			sBuffer.append("Price: " + helper.roundDownDouble(s.getPrice(), 0) + lineBreak);
			sBuffer.append("Quantity: " + s.getQuantity() + lineBreak);
			sBuffer.append("Total Price: " + helper.roundDownDouble(s.getTotalPrice(), 0) + lineBreak);
			sBuffer.append(lineBreak);
		}
		return sBuffer.toString();
	}

	@Override
	public String parseAdjustmentMessageListToString(List<AdjustMessage> adjMessageList) {
		StringBuffer sBuffer = new StringBuffer();
		for (AdjustMessage m : adjMessageList) {
			sBuffer.append(parseAdjustAttributesToString(m));
		}

		return sBuffer.toString();
	}

	@Override
	public String parseAdjustAttributesToString(AdjustMessage message) {
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append("Item Code: " + message.getSale().getItemCode() + lineBreak);
		sBuffer.append(tabLv1 + "Price Adjustment Operation: " + message.getPriceAdjustOperator() + lineBreak);
		sBuffer.append(tabLv1 + "Price Adjustment Value: " + message.getPriceAdjustValue() + lineBreak);
		sBuffer.append(tabLv1 + "Quantity Adjustment Operation: " + message.getQuantityAdjustOperator() + lineBreak);
		sBuffer.append(tabLv1 + "Quantity Adjustment Value: " + message.getQuantityAdjustValue() + lineBreak);
		sBuffer.append(lineBreak);
		return sBuffer.toString();
	}

	@Override
	public String parseSaleAdjustHistoryToString(List<Sale> saleAdjustHistory) {
		StringBuffer sBuffer = new StringBuffer();
		for (Sale s : saleAdjustHistory) {
			sBuffer.append("Effective Squence: " + s.getEffectSquence() + lineBreak);
			sBuffer.append("Price: " + helper.roundDownDouble(s.getPrice(), 2) + lineBreak);
			sBuffer.append("Quantity: " + s.getQuantity() + lineBreak);
			sBuffer.append("Total Price: " + helper.roundDownDouble(s.getTotalPrice(), 2) + lineBreak);
			sBuffer.append(lineBreak);
		}
		return sBuffer.toString();
	}

	@Override
	public String parseSaleSummaryListToString(List<Sale> saleSummaryList) {
		StringBuffer sBuffer = new StringBuffer();
		for (Sale summary : saleSummaryList) {
			sBuffer.append("Item Code: " + summary.getItemCode() + lineBreak);
			sBuffer.append("Price: " + helper.roundDownDouble(summary.getPrice(), 0) + lineBreak);
			sBuffer.append("Total Quantity: " + summary.getQuantity() + lineBreak);
			sBuffer.append("Total Price: " + helper.roundDownDouble(summary.getTotalPrice(), 0) + lineBreak);
			sBuffer.append(lineBreak);
		}
		return sBuffer.toString();
	}

}
