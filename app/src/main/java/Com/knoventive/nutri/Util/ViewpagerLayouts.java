package Com.knoventive.nutri.Util;

import Com.knoventive.nutri.R;


public enum ViewpagerLayouts {

    _1(R.layout.activity_calculator_instructions),
    _2( R.layout.activity_calculator_instructions_1),
    _3( R.layout.activity_calculator_instructions_2),
    _4( R.layout.activity_calculator_instructions_3),
    _5( R.layout.activity_calculator_instructions_4);

    private int mLayoutResId;

    ViewpagerLayouts(int layoutResId) {
        mLayoutResId = layoutResId;
    }

    /*
    get Resource id here
     */
    public int getLayoutResId() {
        return mLayoutResId;
    }
    public int setLayoutResId(int mLayoutResId) {
        this.mLayoutResId = mLayoutResId;
        return mLayoutResId;
    }

}