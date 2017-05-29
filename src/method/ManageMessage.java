package method;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import model.AdjustMessage;
import model.Message;
import model.constant.MessageType;

public class ManageMessage implements ManageMessageI{

	@Override
	public List<AdjustMessage> getAdjustmentMessageList(List<Message> allMessageList) {
		List<AdjustMessage> adjMessageList = new ArrayList<>();
		for(Message m: allMessageList){
		 if(m.getMessageType()==MessageType.TYPE3)adjMessageList.add((AdjustMessage) m);
		}
		return adjMessageList;
	}

	@Override
	public Set<String> getItemCodeSet(List<AdjustMessage> messageList) {
		// TODO Auto-generated method stub
		HashSet<String> itemCodeSet = new HashSet<>();
		for(Message m: messageList){
			itemCodeSet.add(m.getSale().getItemCode());
		}
		return itemCodeSet;
	}
	
	

}
