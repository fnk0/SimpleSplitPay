package simpleplay.midwesthack.com.simplesplitpay.dataModel;

import java.util.List;

/**
 * Created by <a href="mailto:marcusandreog@gmail.com">Marcus Gabilheri</a>
 *
 * @author Marcus Gabilheri
 * @version 1.0
 * @since 7/19/14.
 */
public class User {

    private String userID;
    private String userName;
    private String userEmail;
    private String userBirthday;
    private String userProfilePic;
    private List<CreditCard> userCreditCards;

    public User() {

    }


    public String getUserID() {
        return userID;
    }

    public User setUserID(String userID) {
        this.userID = userID;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public User setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public User setUserEmail(String userEmail) {
        this.userEmail = userEmail;
        return this;
    }

    public String getUserBirthday() {
        return userBirthday;
    }

    public User setUserBirthday(String userBirthday) {
        this.userBirthday = userBirthday;
        return this;
    }

    public String getUserProfilePic() {
        return userProfilePic;
    }

    public User setUserProfilePic(String userProfilePic) {
        this.userProfilePic = userProfilePic;
        return this;
    }

    public List<CreditCard> getUserCreditCards() {
        return userCreditCards;
    }

    public User setUserCreditCards(List<CreditCard> userCreditCards) {
        this.userCreditCards = userCreditCards;
        return this;
    }
}
