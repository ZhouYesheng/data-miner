package le.data.scs.common.entity.meta.tmall;

import java.io.Serializable;

/**
 * Created by yangyong3 on 2017/2/27.
 */
public class TMallCustomerQueryEntity implements Serializable {

    private String employeeNick;

    private String customerNick;

    private String start;

    private String end;

    private String beginKey = null;

    private String endKey = null;

    private Boolean employeeAll = false;

    private Boolean customerAll = false;

    public TMallCustomerQueryEntity(){}

    public TMallCustomerQueryEntity(String employeeNick, String customerNick, String start, String end) {
        this.employeeNick = employeeNick;
        this.customerNick = customerNick;
        this.start = start;
        this.end = end;
    }

    public String getEmployeeNick() {
        return employeeNick;
    }

    public void setEmployeeNick(String employeeNick) {
        this.employeeNick = employeeNick;
    }

    public String getCustomerNick() {
        return customerNick;
    }

    public void setCustomerNick(String customerNick) {
        this.customerNick = customerNick;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getBeginKey() {
        return beginKey;
    }

    public void setBeginKey(String beginKey) {
        this.beginKey = beginKey;
    }

    public String getEndKey() {
        return endKey;
    }

    public void setEndKey(String endKey) {
        this.endKey = endKey;
    }

    public Boolean getEmployeeAll() {
        return employeeAll;
    }

    public void setEmployeeAll(Boolean employeeAll) {
        this.employeeAll = employeeAll;
    }

    public Boolean getCustomerAll() {
        return customerAll;
    }

    public void setCustomerAll(Boolean customerAll) {
        this.customerAll = customerAll;
    }
}
