package simpleplay.midwesthack.com.simplesplitpay.dataModel;

/**
 * Created by <a href="mailto:marcusandreog@gmail.com">Marcus Gabilheri</a>
 *
 * @author Marcus Gabilheri
 * @version 1.0
 * @since 7/20/14.
 */
public class Bill {

    public static final int BILL_OK = 0;
    public static final int BILL_BAD = 1;
    public static final String BILL_VALUE = "value";
    public static final String BILL_DESCRIPTION = "description";
    public static final String BILL_ACTIVE = "isActive";
    private String billDescription;
    private String billDetails;
    private int billStatus;

    public Bill() {
    }

    public Bill(String billDescription, String billDetails, int billStatus) {
        this.billDescription = billDescription;
        this.billDetails = billDetails;
        this.billStatus = billStatus;
    }

    public String getBillDescription() {
        return billDescription;
    }

    public Bill setBillDescription(String billDescription) {
        this.billDescription = billDescription;
        return this;
    }

    public String getBillDetails() {
        return billDetails;
    }

    public Bill setBillDetails(String billDetails) {
        this.billDetails = billDetails;
        return this;
    }

    public int getBillStatus() {
        return billStatus;
    }

    public Bill setBillStatus(int billStatus) {
        this.billStatus = billStatus;
        return this;
    }
}
