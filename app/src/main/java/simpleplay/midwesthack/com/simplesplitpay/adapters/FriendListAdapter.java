package simpleplay.midwesthack.com.simplesplitpay.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by <a href="mailto:marcusandreog@gmail.com">Marcus Gabilheri</a>
 *
 * @author Marcus Gabilheri
 * @version 1.0
 * @since 7/19/14.
 */
public class FriendListAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Friend> mFriendList;

    public FriendListAdapter() {
    }

    public FriendListAdapter(Context mContext, ArrayList<Friend> mFriendList) {
        this.mContext = mContext;
        this.mFriendList = mFriendList;
    }

    @Override
    public int getCount() {
        return mFriendList.size();
    }

    @Override
    public Object getItem(int position) {
        return mFriendList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {



        return null;
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public ArrayList<Friend> getmFriendList() {
        return mFriendList;
    }

    public void setmFriendList(ArrayList<Friend> mFriendList) {
        this.mFriendList = mFriendList;
    }
}
