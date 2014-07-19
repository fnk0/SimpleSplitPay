package simpleplay.midwesthack.com.simplesplitpay.adapters;

/**
 * Created by <a href="mailto:marcusandreog@gmail.com">Marcus Gabilheri</a>
 *
 * @author Marcus Gabilheri
 * @version 1.0
 * @since 7/19/14.
 */
public class Friend {

    private String name, email, photoURl;

    public Friend() {
    }

    public Friend(String name, String photoURl) {
        this.name = name;
        this.photoURl = photoURl;
    }

    public Friend(String name, String email, String photoURl) {
        this.name = name;
        this.email = email;
        this.photoURl = photoURl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhotoURl() {
        return photoURl;
    }

    public void setPhotoURl(String photoURl) {
        this.photoURl = photoURl;
    }
}
