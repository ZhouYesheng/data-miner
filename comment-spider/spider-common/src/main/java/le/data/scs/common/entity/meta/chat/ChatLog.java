package le.data.scs.common.entity.meta.chat;

import org.apache.commons.collections.CollectionUtils;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangyong3 on 2017/3/2.
 */
public class ChatLog implements Serializable {

    private String id;

    @JsonIgnore
    private String dataTime;

    private String waiter;

    private String customer;

    private List<ChatDetail> chatDetails = new ArrayList<ChatDetail>();
    @JsonIgnore
    private List<ChatDetail> waiterTalks;
    @JsonIgnore
    private List<ChatDetail> customerTalks;

    public ChatLog(String waiter, String customer, List<ChatDetail> chatDetails) {
        this.waiter = waiter;
        this.customer = customer;
        this.chatDetails = chatDetails;
    }

    public ChatLog() {
    }

    public String getDataTime() {
        return dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public void addChatDetail(ChatDetail detail) {
        chatDetails.add(detail);
    }

    public List<ChatDetail> getWaiterTalks() {
        if (CollectionUtils.isEmpty(waiterTalks)) {
            waiterTalks = findTalkByRole(ChatRole.WATIER);
        }
        return waiterTalks;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private List<ChatDetail> findTalkByRole(ChatRole role) {
        List<ChatDetail> details = new ArrayList<ChatDetail>();
        for (ChatDetail detail : chatDetails) {
            if (role.toString() == detail.getRole())
                details.add(detail);
        }
        return details;
    }

    public List<ChatDetail> getCustomerTalks() {
        if (CollectionUtils.isEmpty(customerTalks)) {
            customerTalks = findTalkByRole(ChatRole.CUSTOMER);
        }
        return customerTalks;
    }

    public List<ChatDetail> getChatDetails() {
        return chatDetails;
    }

    public void setChatDetails(List<ChatDetail> chatDetails) {
        this.chatDetails = chatDetails;
    }

    public String getWaiter() {
        return waiter;
    }

    public void setWaiter(String waiter) {
        this.waiter = waiter;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }


}
