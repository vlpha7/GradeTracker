package teco.gradetracker;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.design.widget.Snackbar;

import java.util.List;

import teco.gradetracker.Database.UnitValues;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    List<UnitValues> units;

    public RecyclerAdapter(List<UnitValues> units){
        this.units = units;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview_unitoutline, viewGroup, false); //CardView inflated as RecyclerView list item
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        String temp = units.get(position).getName();
        viewHolder.unitTitle.setText(temp);
    }

    @Override
    public int getItemCount() {
        return units.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View itemView;
        public TextView unitTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;

            unitTitle = (TextView)itemView.findViewById(R.id.unit_title);
        }
    }

}
