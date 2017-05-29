package method;

import java.util.List;
import java.util.Set;

import model.AdjustMessage;
import model.Message;

public interface ManageMessageI {

	public List<AdjustMessage> getAdjustmentMessageList(List<Message> allMessageList);

	public Set<String> getItemCodeSet(List<AdjustMessage> messageList);
}
