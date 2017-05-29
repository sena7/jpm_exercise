package test;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import model.AdjustMessage;
import model.Item;
import model.Logger;
import model.Message;
import model.Sale;
import model.constant.AdjustOperator;
import model.constant.MessageType;

/**
 * 
 * @author Sena Bak
 *         <p>
 *         When SampleData is instantiated, it generates the controlled initial
 *         itemList as defined in the method initialItemList.<br>
 */
public class SampleData {

	Random random = new Random();
	/**
	 * itemList will hold all of the historical records of each item.
	 */
	private List<Item> itemList = new ArrayList<>();
	/**
	 * The items with the latest effectSequence number of each item type.<br>
	 * When an adjustment message is generated and saved in the
	 * sampleMessageList,<br>
	 * the item with the item code identical to that of the adjustment message
	 * <br>
	 * will be updated with the adjusted price by replacing the old record.
	 * 
	 */
	public static List<Item> currentItemList = new ArrayList<>();

	public static List<Message> sampleMessageList = new ArrayList<>();

	public static final double MAX_PRICE = 1000000;
	public static final double MIN_PRICE = 50;

	private static final int MAX_QUANTITY = 200;
	private static final int MIN_QUANTITY = 1;
	private static final double MAX_ADJUST_PRICE_ABSOLUTE = 100;
	private static final double MIN_ADJUST_PRICE_ABSOLUTE = 10;
	private static final int MAX_ADJUST_QUANTITY_ABSOLUTE = 50;
	private static final int MIN_ADJUST_QUANTITY_ABSOLUTE = 1;
	private static final double MAX_ADJUST_MULTIPLY = 2.0d;
	private static final double MIN_ADJUST_MULTIPLY = 1.0d;

	/**
	 * 
	 */
	public SampleData() {
		initialItemList();
	}

	private void initialItemList() {
		long currentTimeMillis = System.currentTimeMillis();

		itemList.add(new Item("CPU", 10000, currentTimeMillis, 1));
		itemList.add(new Item("RAM", 1000, currentTimeMillis, 1));
		itemList.add(new Item("HDD", 3000, currentTimeMillis, 1));
		itemList.add(new Item("SDD", 5000, currentTimeMillis, 1));

		currentItemList.add(new Item("CPU", 10000, currentTimeMillis, 1));
		currentItemList.add(new Item("RAM", 1000, currentTimeMillis, 1));
		currentItemList.add(new Item("HDD", 3000, currentTimeMillis, 1));
		currentItemList.add(new Item("SDD", 5000, currentTimeMillis, 1));

	}

	public void generateRandomMessageList(Logger logger, int messageCount) throws IOException, ParseException {

		int currentItemListSize = currentItemList.size();

		for (int i = 1; i <= messageCount; i++) {

			String messageId = UUID.randomUUID().toString();
			MessageType messageType = MessageType.values()[random.nextInt(MessageType.values().length)];

			int randomQuantity = 1;
			switch (messageType) {
			case TYPE1:
				randomQuantity = 1;
				break;
			case TYPE2:
				randomQuantity = getRandomInt(MAX_QUANTITY, 2);
				break;
			case TYPE3:
				randomQuantity = getRandomInt(MAX_QUANTITY, MIN_QUANTITY);
				break;
			}

			// ***** Choose a random Item
			Item randomItem = currentItemList.get(random.nextInt(currentItemListSize));

			Sale sale = new Sale(UUID.randomUUID().toString(), randomItem.getCode(), randomItem.getPrice(),
					randomQuantity, randomQuantity * randomItem.getPrice(), System.currentTimeMillis(), 1);

			// ********** Message **********
			// if Adjustment message, set the operation determinants
			if (messageType == MessageType.TYPE3) {
				AdjustMessage adjMessage = new AdjustMessage();

				AdjustOperator randomPriceAdjOperator = AdjustOperator.values()[random
						.nextInt(AdjustOperator.values().length)];

				double randomPriceAdjValue = 0d;
				switch (randomPriceAdjOperator) {
				case MUTIPLY:
					randomPriceAdjValue = roundDownDouble(getRandomDouble(MAX_ADJUST_MULTIPLY, MIN_ADJUST_MULTIPLY), 1);
					break;
				case ADD:
					randomPriceAdjValue = roundDownDouble(
							getRandomDouble(MAX_ADJUST_PRICE_ABSOLUTE, MIN_ADJUST_PRICE_ABSOLUTE), 1);
					break;
				case SUBSTRACT:
					randomPriceAdjValue = -roundDownDouble(
							getRandomDouble(MAX_ADJUST_PRICE_ABSOLUTE, MIN_ADJUST_PRICE_ABSOLUTE), 1);
					break;
				default:
					
					break;

				}
				adjMessage.setPriceAdjustOperator(randomPriceAdjOperator);
				adjMessage.setPriceAdjustValue(randomPriceAdjValue);

				// Item price should be updated every time an adjustment message
				// is generated for the later sales
				randomItem.setPrice(randomPriceAdjOperator == AdjustOperator.MUTIPLY
						? randomItem.getPrice() * randomPriceAdjValue : randomItem.getPrice() + randomPriceAdjValue);
				randomItem.setEffectSequence(randomItem.getEffectSequence() + 1);
				itemList.add(randomItem);
				// Assign random adjustment operator and values for the quantity
				// of the items of a Sale
				AdjustOperator randomQuantityAdjOperator = AdjustOperator.values()[random
						.nextInt(AdjustOperator.values().length)];

				double randomQuantityAdjValue  = 0;
				switch (randomQuantityAdjOperator) {
				case MUTIPLY:
					randomQuantityAdjValue = roundDownDouble(getRandomDouble(MAX_ADJUST_MULTIPLY, MIN_ADJUST_MULTIPLY),
							1);
					break;
				case ADD:
					randomQuantityAdjValue = getRandomInt(MAX_ADJUST_QUANTITY_ABSOLUTE, MIN_ADJUST_QUANTITY_ABSOLUTE);
					break;
				case SUBSTRACT:
					randomQuantityAdjValue = -getRandomInt(MAX_ADJUST_QUANTITY_ABSOLUTE, MIN_ADJUST_QUANTITY_ABSOLUTE);
					break;
				default:
					break;
				}

				adjMessage.setQuantityAdjustOperator(randomQuantityAdjOperator);
				adjMessage.setQuantityAdjustValue(randomQuantityAdjValue);

				adjMessage.setMessageType(messageType);
				adjMessage.setSale(sale);
				adjMessage.setId(messageId);
				sampleMessageList.add(adjMessage);
			} else {
				Message message = new Message();

				message.setMessageType(messageType);
				message.setSale(sale);
				message.setId(messageId);
				sampleMessageList.add(message);
			}

		}

	}

	private int getRandomInt(int max, int min) {
		return random.nextInt((max - min) + 1) + min;
	}

	private double roundDownDouble(double d, int decimalPosition) {
		double operand = Math.pow(10, decimalPosition);
		return Math.round(d * operand) / operand;
	}

	private double getRandomDouble(double max, double min) {
		return random.nextDouble() * (max - min) + min;
	}

	public Item getItemByItemCode(String itemCode) {
		Item item = new Item();
		for (Item i : currentItemList) {
			if (i.getCode() == itemCode) {
				item = i;
				break;
			}
		}
		return item;
	}
}
