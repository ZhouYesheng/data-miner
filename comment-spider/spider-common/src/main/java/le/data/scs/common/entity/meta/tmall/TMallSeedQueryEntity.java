package le.data.scs.common.entity.meta.tmall;

import java.io.Serializable;

/**
 * Created by yangyong3 on 2017/2/28.
 */
public class TMallSeedQueryEntity implements Serializable {

    private String[] employeeUserNick;

    private String[] customerUserNick;

    private String start;

    private String end;

    private String beginKey = null;

    private String endKey = null;

    private Boolean employeeAll = false;

    private Boolean customerAll = false;


    public TMallSeedQueryEntity(String[] employeeUserNick, String[] customerUserNick, String start, String end) {
        this.employeeUserNick = employeeUserNick;
        this.customerUserNick = customerUserNick;
        this.start = start;
        this.end = end;
    }

    public String[] getEmployeeUserNick() {
        return employeeUserNick;
    }

    public void setEmployeeUserNick(String[] employeeUserNick) {
        this.employeeUserNick = employeeUserNick;
    }

    public String[] getCustomerUserNick() {
        return customerUserNick;
    }

    public void setCustomerUserNick(String[] customerUserNick) {
        this.customerUserNick = customerUserNick;
    }

    public TMallSeedQueryEntity() {
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
