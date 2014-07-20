package simpleplay.midwesthack.com.simplesplitpay.dataModel;

/**
 * Created by <a href="mailto:marcusandreog@gmail.com">Marcus Gabilheri</a>
 *
 * @author Marcus Gabilheri
 * @version 1.0
 * @since 7/20/14.
 */
public class Friend  {

    private String name;
    private boolean registred;

    public Friend() {
    }

    public Friend(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isRegistred() {
        return registred;
    }

    public void setRegistred(boolean registred) {
        this.registred = registred;
    }
}
