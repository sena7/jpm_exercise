package test;

import java.io.IOException;
import java.text.ParseException;
import model.Logger;
import model.MessagePool;

public class MessageProcessor {

	private static Logger logger = new Logger();
	private static MessagePool messagePool = new MessagePool(logger);

	private static final int MAX_MESSAGE_COUNT = 50;

	private static SampleData sampleData = new SampleData();

	public static void main(String[] args) throws IOException, ParseException {

		// Generate 50 random messages
		sampleData.generateRandomMessageList(logger, 50);

		// Add the messages from sampleData.sampleMessageList to messagePool instance 
		// as if the new message was sent from an external party and the messagePool was triggered. 
		// It will log the messages to console by default
		for (int i = 0; i < SampleData.sampleMessageList.size(); i++) {
            
			messagePool.addMessage(SampleData.sampleMessageList.get(i));

			if (messagePool.getMessageCount() == MAX_MESSAGE_COUNT) {
				break;
			}
		}

	}

}
