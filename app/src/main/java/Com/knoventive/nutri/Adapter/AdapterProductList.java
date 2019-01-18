package Com.knoventive.nutri.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import Com.knoventive.nutri.R;
import Com.knoventive.nutri.Util.CustomTextView_Air_Semibold;
import Com.knoventive.nutri.Util.CustomTextView_Air_light;
import Com.knoventive.nutri.model.ProductListItem;


public class AdapterProductList extends RecyclerView.Adapter<AdapterProductList.Holder> {
    private ArrayList<ProductListItem> productList;
    private Context mContext;
    private boolean isClickable = true;

    public AdapterProductList(Context context, ArrayList<ProductListItem> productList) {
        this.productList = productList;
        this.mContext = context;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_list_item, parent, false);
        return new Holder(itemView);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        if (productList != null) {
            final String nutri_desc = productList.get(position).getDesc();
            if (nutri_desc != null) {
                holder.desc.setText(nutri_desc);
            }
            final String heading = productList.get(position).getHeading();
            if (heading != null) {
                holder.header_label.setText(heading);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (productList != null) {
            return productList.size();
        }
        return 1;

    }

    /*
    check is view clickable?
     */
    public void isClickable(boolean clickable) {
        this.isClickable = clickable;
    }

    public class Holder extends RecyclerView.ViewHolder {
        public CustomTextView_Air_Semibold header_label;
        public CustomTextView_Air_light desc;
        public CardView card_view;
        ImageView fwd_green;

        public Holder(View itemView) {
            super(itemView);
            desc = itemView.findViewById(R.id.desc);
            header_label = itemView.findViewById(R.id.header_label);
            card_view = itemView.findViewById(R.id.card_view);
            fwd_green = itemView.findViewById(R.id.fwd_green);

        }
    }
}
