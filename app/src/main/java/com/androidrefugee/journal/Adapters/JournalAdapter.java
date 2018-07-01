package com.androidrefugee.journal.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.androidrefugee.journal.DetailEntry;
import com.androidrefugee.journal.Model.UserModel;
import com.androidrefugee.journal.R;
import com.androidrefugee.journal.Helpers.CovertDate;

import java.util.ArrayList;


public class JournalAdapter extends RecyclerView.Adapter<JournalAdapter.SingleItemViewHolder>{
    private ArrayList<UserModel> data;

    public JournalAdapter(ArrayList<UserModel> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public  SingleItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.
                list_item,parent,false);
        SingleItemViewHolder holder = new SingleItemViewHolder(v);
        return holder;
    }
    @Override
    public void onBindViewHolder(@NonNull SingleItemViewHolder holder, int position) {
        Long timeStamp = data.get(position).getTimeStamp();
        String date = CovertDate.toDate(timeStamp);
        String title = data.get(position).getTitle();
        String message = data.get(position).getBodyMessage();
        String moods = data.get(position).getMoods();
        String[] moodsAsArray = moods.split(",");


        String moodOne = moodsAsArray[0];
        String moodTwo = moodsAsArray[1];
        String moodThree = moodsAsArray[2];

        holder.date.setText(date);
        holder.title.setText(title);
        holder.message.setText(message);
        holder.moodOne.setText(moodOne);
        holder.moodTwo.setText(moodTwo);
        holder.moodThree.setText(moodThree);
    }


    @Override
    public int getItemCount() {
        return (null != data ? data.size() : 0);
    }

    public class SingleItemViewHolder extends RecyclerView.ViewHolder{
        private TextView date;
        private TextView title;
        private TextView message;
        private TextView moodOne;
        private TextView moodTwo;
        private TextView moodThree;

        public SingleItemViewHolder(final View itemView) {
            super(itemView);

            date = (TextView)itemView.findViewById(R.id.item_text_view_date);
            title = (TextView)itemView.findViewById(R.id.item_text_view_title);
            message = (TextView)itemView.findViewById(R.id.item_text_view_message);
            moodOne = (TextView)itemView.findViewById(R.id.item_chip_one);
            moodTwo = (TextView)itemView.findViewById(R.id.item_chip_two);
            moodThree = (TextView)itemView.findViewById(R.id.item_chip_three);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    getItemsAtClickedPosition(bundle,getLayoutPosition());

                    Context context = view.getContext();
                    Intent i = new Intent(context, DetailEntry.class);
                    i.putExtras(bundle);
                    context.startActivity(i);
                }
            });
        }

    }

    private void getItemsAtClickedPosition(Bundle bundle,int position){
        long timeStamp = data.get(position).getTimeStamp();
        String date = CovertDate.toDate(timeStamp);
        String title = data.get(position).getTitle();
//        String message = data.get(position).getBodyMessage();
//        String moodOne = data.get(position).getMoods().get(0);
//        String moodTwo = data.get(position).getMoods().get(1);
//        String moodThree = data.get(position).getMoods().get(2);


        bundle.putString("date", date);
        bundle.putString("title",title);
//        bundle.putString("message", message);
//        bundle.putString("moodOne", moodOne);
//        bundle.putString("moodTwo", moodTwo);
//        bundle.putString("moodThree", moodThree);
    }
}
