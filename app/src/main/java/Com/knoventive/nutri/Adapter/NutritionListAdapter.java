package Com.knoventive.nutri.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import Com.knoventive.nutri.Activity.RegimenScreen;
import Com.knoventive.nutri.R;
import Com.knoventive.nutri.model.DataList;

public class NutritionListAdapter extends RecyclerView.Adapter<NutritionListAdapter.Holder> {
    private ArrayList<DataList> nutritionList;
    private Context mContext;
    private  String nutri_desc;

    public NutritionListAdapter(Context context, ArrayList<DataList> nutritionList) {
        this.nutritionList = nutritionList;
        this.mContext = context;
    }

    @Override
    public NutritionListAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_regimen, parent, false);
        return new Holder(itemView);
    }

    @Override
    public void onBindViewHolder(final NutritionListAdapter.Holder holder, int position) {
        if (nutritionList != null) {
              nutri_desc = nutritionList.get(position).getDesc();
             String nutrition_value = nutritionList.get(position).getNutri_value();
             String value = nutritionList.get(position).getValue();
            if(nutri_desc!=null){holder.desc.setText(nutri_desc);}
            if(nutrition_value!=null){holder.nutri_value.setText(nutrition_value);}
            if(value!=null){holder.value.setText(value);}

            if(nutri_desc.equals(mContext.getResources().getString(R.string.glucose))||nutri_desc.equals(mContext.getResources().getString(R.string.lipid))||
                    nutri_desc.equals(mContext.getResources().getString(R.string.amino_acid))){
                holder.ll_layout.setBackgroundColor(mContext.getResources().getColor(R.color.amino_acid_bg));

            }
            else if(nutri_desc.contains(mContext.getResources().getString(R.string.infusion))&& !nutrition_value.equals(" ")){
                updateProductUI(holder,Color.WHITE,mContext.getResources().getColor(R.color.colorAccent),mContext.getResources().getString(R.string.tapabble_text));
                holder.ll_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String getDesc = holder.desc.getText().toString();
                 if(getDesc.contains("Infusion time (h)\n" + " 986 mL bag")){
                        ((RegimenScreen)mContext).navigateToPdfScreen("986");}
                        else if(getDesc.contains("Infusion time (h)\n" + " 1477 mL bag")){
                            ((RegimenScreen)mContext).navigateToPdfScreen("1477");}
                       else if(getDesc.contains("Infusion time (h)\n" + " 1970 mL bag")){
                            ((RegimenScreen)mContext).navigateToPdfScreen("1970");}
                        else if(getDesc.contains("Infusion time (h)\n" + " 2463 mL bag")){
                            ((RegimenScreen)mContext).navigateToPdfScreen("2463");}

                    }
                });
            }
            else {
                updateProductUI(holder,Color.BLACK,Color.WHITE,"");
                holder.nutri_value.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
                holder.value.setTextColor(mContext.getResources().getColor(R.color.orange));
                holder.ll_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                });
            }
        }
    }

    private void updateProductUI(Holder holder, int textcolor, int bgcolor, String txt) {
        holder.desc.setTextColor(textcolor);
        holder.nutri_value.setTextColor(textcolor);
        holder.value.setTextColor(textcolor);
        holder.tappable.setTextColor(textcolor);
        holder.tappable.setText(txt);
        holder.llchild.setBackgroundColor(bgcolor);
    }

    @Override
    public int getItemCount() {

        if(nutritionList!=null){return nutritionList.size();}
        return 1;
    }
    public class Holder extends RecyclerView.ViewHolder {
        private LinearLayout llchild ;
        private TextView desc, nutri_value, value,tappable;
        private LinearLayoutCompat ll_layout;
        private Holder(View itemView) {
            super(itemView);
            desc = (TextView) itemView.findViewById(R.id.desc);
            nutri_value = (TextView) itemView.findViewById(R.id.nutrition_value);
            value = (TextView) itemView.findViewById(R.id.value);
            ll_layout = itemView.findViewById(R.id.ll_layout);
            llchild = itemView.findViewById(R.id.llchild);
            tappable = itemView.findViewById(R.id.tappable);
        }
    }
}
