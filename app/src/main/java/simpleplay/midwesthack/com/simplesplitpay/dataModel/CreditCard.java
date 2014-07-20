package simpleplay.midwesthack.com.simplesplitpay.dataModel;

/**
 * Created by <a href="mailto:marcusandreog@gmail.com">Marcus Gabilheri</a>
 *
 * @author Marcus Gabilheri
 * @version 1.0
 * @since 7/19/14.
 */
public class CreditCard {

    public static final int CREATE_CREDIT_CARD_CODE = 0;
    public static final int CARD_CREATED_SUCCESSFULL = 200;
    public static final int CARD_NOT_CREATED = 404;

    public static final String VISA = "visa";
    public static final String MASTER = "master";
    public static final String AMEX = "amex";
    public static final String DISCOVER = "discover";

    private String creditCardNumber;
    private String creditCardCSV;
    private String creditCardExpMonth;
    private String creditCardExpYear;
    private String creditCardType;
    private Address billingAddress;



    public CreditCard() {
    }

    public CreditCard(String creditCardType, String creditCardNumber) {
        this.creditCardType = creditCardType;
        this.creditCardNumber = creditCardNumber;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public CreditCard setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
        return this;
    }

    public String getCreditCardCSV() {
        return creditCardCSV;
    }

    public CreditCard setCreditCardCSV(String creditCardCSV) {
        this.creditCardCSV = creditCardCSV;
        return this;
    }

    public String getCreditCardExpMonth() {
        return creditCardExpMonth;
    }

    public CreditCard setCreditCardExpMonth(String creditCardExpMonth) {
        this.creditCardExpMonth = creditCardExpMonth;
        return this;
    }

    public String getCreditCardExpYear() {
        return creditCardExpYear;
    }

    public CreditCard setCreditCardExpYear(String creditCardExpYear) {
        this.creditCardExpYear = creditCardExpYear;
        return this;
    }

    public Address getBillingAddress() {
        return billingAddress;
    }

    public CreditCard setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
        return this;
    }

    public String getCreditCardType() {
        return creditCardType;
    }

    public CreditCard setCreditCardType(String creditCardType) {
        this.creditCardType = creditCardType;
        return this;
    }

    public boolean addCreditCard() {
        //TODO SEND INFO TO SERVER
        return false;
    }
}
