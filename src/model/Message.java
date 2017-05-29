package model;

import java.util.UUID;

import model.constant.MessageType;

/**
 * 
 * @author Sena Bak
 *         <p>
 * 
 *
 */
public class Message {
	/**
	 * id is generated using UUID.randomUUID().
	 */
	private String id;
	private MessageType messageType;
	private Sale sale;

	
	
	public Message(MessageType messageType, Sale sale) {
		this.id = UUID.randomUUID().toString();
		this.messageType = messageType;
		this.sale = sale;
	}
	

	public Message(String id, MessageType messageType, Sale sale) {
		super();
		this.id = id;
		this.messageType = messageType;
		this.sale = sale;
	}


	public Message() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public MessageType getMessageType() {
		return messageType;
	}

	public void setMessageType(MessageType messageType) {
		this.messageType = messageType;
	}

	public Sale getSale() {
		return sale;
	}

	public void setSale(Sale sale) {
		this.sale = sale;
	}

	@Override
	public String toString() {
		return "Message [id=" + id + ", messageType=" + messageType + ", sale=" + sale + "]";
	}

	

}
