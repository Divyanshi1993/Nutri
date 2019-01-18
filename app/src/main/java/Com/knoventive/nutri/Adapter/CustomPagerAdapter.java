package Com.knoventive.nutri.Adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import Com.knoventive.nutri.Util.ViewpagerLayouts;

public class CustomPagerAdapter extends PagerAdapter {

    private Context mContext;
    private  int count;

    public CustomPagerAdapter(Context context,int count) {
        mContext = context;
        this.count=count;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        ViewpagerLayouts modelObject = ViewpagerLayouts.values()[position];
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup layout = (ViewGroup) inflater.inflate(modelObject.getLayoutResId(), collection, false);
        collection.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {

        if(count==0){
            return 1;
        }
            return ViewpagerLayouts.values().length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

}
