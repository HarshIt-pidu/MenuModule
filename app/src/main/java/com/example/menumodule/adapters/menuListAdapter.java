package com.example.menumodule.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.example.menumodule.R;
import com.example.menumodule.fragments.MenuDetailsFragment;
import com.example.menumodule.listeners.IRecyclerViewClickListener;
import com.example.menumodule.models.MenuListModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class menuListAdapter extends RecyclerView.Adapter<menuListAdapter.MyMenuListViewHolder> {

    private Context context;
    private List<MenuListModel> menuListModelList;
    private sendItemData sendItemData;

    public menuListAdapter(Context context, List<MenuListModel> menuListModelList, sendItemData listener) {
        this.context = context;
        this.menuListModelList = menuListModelList;
        this.sendItemData = listener;

    }

    @NonNull
    @Override
    public MyMenuListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyMenuListViewHolder(LayoutInflater.from(context).inflate(R.layout.menu_list_single_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyMenuListViewHolder holder, @SuppressLint("RecyclerView") int position) {

        //holder.view
        holder.txtListName.setText(new StringBuilder().append(menuListModelList.get(position).getName()));
        holder.txtListMode.setText(new StringBuilder().append(ListMode(position)));
        holder.txtListTiming.setText(new StringBuilder().append(ListTime(position, holder)));

//        holder.txtFixedPrice.setText(new StringBuilder("Fixed: ").append(salaryModelList.get(position).getSalaryfixed()));
//        holder.txtPrice.setText(new StringBuilder("₹ ").append(Integer.parseInt(salaryModelList.get(position).getSalaryfixed())+ Integer.parseInt(salaryModelList.get(position).getSalarycommisions())-Integer.parseInt(salaryModelList.get(position).getSalarydeductable())));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendItemData.updateItem(menuListModelList.get(position),position);
            }
        });

    }

    public interface sendItemData{
        void updateItem(MenuListModel itemViewModel, int pos);

    }

    private String ListTime(int position, MyMenuListViewHolder holder) {
        String result = "";
        if(menuListModelList.get(position).getAllday().equals("1")){
            result = "Timing: All Day";
        }else{
            holder.txtListTiming.setHeight(0);
        }

        return result;
    }

    private String ListMode(int position) {
        String delivery = "";
        String pickup = "";
        String dinein = "";
        String result = "";
        if(menuListModelList.get(position).getDelivery().equals("1") ){
            delivery = "Delivery";
        }
        if(menuListModelList.get(position).getPickup().equals("1") ){
            pickup = " Pickup";
        }
        if(menuListModelList.get(position).getDinein().equals("1") ){
            dinein = " Dining";
        }
        return result = delivery + pickup + dinein;
    }

    @Override
    public int getItemCount() {
        return menuListModelList.size();
    }

    public class MyMenuListViewHolder extends RecyclerView.ViewHolder {

        //BindView
        @BindView(R.id.txtListName)
        TextView txtListName;

        @BindView(R.id.txtListTiming)
        TextView txtListTiming;

        @BindView(R.id.txtListMode)
        TextView txtListMode;

        @BindView(R.id.btnListSettings)
        ImageView btnListSettings;

        @BindView(R.id.btnListShare)
        ImageView btnListShare;

        IRecyclerViewClickListener listener;

        public void setListener(IRecyclerViewClickListener listener) {
            this.listener = listener;
        }


        private Unbinder unbinder;

        public MyMenuListViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onRecyclerClick(view, getAdapterPosition());
                }
            });
        }
    }
}
