package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import method.ManageMessage;
import method.ManageSale;
import method.Parser;
import model.constant.MessageType;

public class MessagePool {
	private Parser parser = new Parser();
	private ManageSale manageSale = new ManageSale();
	private ManageMessage manageMessage = new ManageMessage();

	private static final String LOG_HEADER_MESSAGE_LIST_10TH = "Summarising the sales. Message count reached ";
	private static final String LOG_HEADER_MESSAGE_LIST_50 = "Message count reached the maximum 50. Pausing the program.";
	private static String lineBreak = "\n\n";
	private static String tabLv1 = "\t";
	private int messageCount = 0;
	private boolean isRunning = false;

	private List<Message> messageList;
	private Logger logger;
	private SalePool salePool;

	public MessagePool(Logger logger) {
		this.messageList = new ArrayList<>();
		this.logger = logger;
		this.salePool = new SalePool();
		this.isRunning = true;
	}

	public int getMessageCount() {
		return messageCount;
	}

	public void addMessage(Message message) {
		if (isRunning) {
			this.messageList.add(message);
			this.messageCount += 1;

			this.salePool.addSale(this.parser.getSaleFromMessage(message));

			try {
				this.logger.log(parser.parseMessageToString(message));
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			if (message.getMessageType() == MessageType.TYPE3) {
				List<Sale> adjustedSaleList = this.salePool.addAdjustedSales((AdjustMessage) message);
				StringBuffer sBuffer = new StringBuffer();
				sBuffer.append(tabLv1 +
						adjustedSaleList.size() + " sales of Item Code " + message.getSale().getItemCode() + " have been adjusted." + lineBreak);
				sBuffer.append(tabLv1 + "Adjustment result:" + lineBreak );
				sBuffer.append(this.parser.parseSaleListToString(adjustedSaleList));
				try {
					this.logger.log(sBuffer.toString());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			boolean multipleOf10 = this.messageCount / 10 >= 1 && this.messageCount % 10 == 0;
			if (multipleOf10) {
				List<Sale> saleList = this.manageSale.aggregateSalesByItemCode(this.salePool.getSaleList());
				StringBuffer sBuffer = new StringBuffer();
				sBuffer.append(LOG_HEADER_MESSAGE_LIST_10TH + messageCount + " \n");
				sBuffer.append(this.parser.parseSaleSummaryListToString(saleList));

				try {
					this.logger.log(sBuffer.toString());

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			;
			if (this.messageCount == 50) {
				List<AdjustMessage> adjMessageList = this.manageMessage.getAdjustmentMessageList(this.messageList);
				Set<String> adjustedItemCodeSet = manageMessage.getItemCodeSet(adjMessageList);
				
				StringBuffer sBuffer = new StringBuffer();
				sBuffer.append(LOG_HEADER_MESSAGE_LIST_50 + lineBreak);
				sBuffer.append("Reporting the adjustments:"+ lineBreak);
				sBuffer.append(adjMessageList.size() + " adjustment message"
						+ (adjMessageList.size() == 1 ? "" : "s") + " received:" + lineBreak);
				sBuffer.append(this.parser.parseAdjustmentMessageListToString(adjMessageList) + lineBreak);
				sBuffer.append("Adjusted sales history:" + lineBreak);
				sBuffer.append("The sales of the following items were adjusted; " + adjustedItemCodeSet.toString() + lineBreak);
				
				for (String itemCode : adjustedItemCodeSet) {
					sBuffer.append("Adjusted sales of item " + itemCode + lineBreak);
				
					List<Sale> updatedCurrentSaleList = this.salePool.getUpdatedSaleListByItemCode(itemCode);
					for (Sale updatedSale : updatedCurrentSaleList) {
						sBuffer.append("Item: " + itemCode + ", " + "Sale ID: " + updatedSale.getId()
								+ " adjustment history." + lineBreak);
						
						List<Sale> history = manageSale.getSaleAdjustList(this.salePool.getSaleList(),
								updatedSale.getItemCode(), updatedSale.getId());
						
						sBuffer.append(parser.parseSaleAdjustHistoryToString(history));
						
						
					}
				}
				
				try {
					
					logger.log(sBuffer.toString());
		
				} catch (IOException e) {
					e.printStackTrace();
				}
				this.isRunning = false;
			}
			;
		}
	}

	public List<Message> getMessageList() {
		return this.messageList;
	}

}
