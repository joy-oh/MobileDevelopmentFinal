package com.example.afinal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.afinal.R;
import com.example.afinal.activity.RecyclerViewInterface;
import com.example.afinal.model.LongWeekends;
import com.example.afinal.model.Next7daysHolidays;
import com.example.afinal.model.PublicHolidays;
import org.w3c.dom.Text;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<Object> items;
    private static final int NEXT_7DAYS = 0;
    private static final int PUBLIC_HOLIDAYS = 1;
    private static final int LONG_WEEKEND = 2;
    private Context context;
    private final RecyclerViewInterface recyclerViewInterface;
    public CustomAdapter(Context context, List<Object> list, RecyclerViewInterface recyclerViewInterface){
        this.context = context;
        this.items = list;
        this.recyclerViewInterface = recyclerViewInterface;
    }
    private static class Next7DaysViewHolder extends RecyclerView.ViewHolder{

        TextView date;
        TextView name;
        TextView country;
        public Next7DaysViewHolder(View itemView, RecyclerViewInterface recyclerViewInterface){
            super(itemView);
            View mView = itemView;

            date = mView.findViewById(R.id.date);
            name = mView.findViewById(R.id.name);
            country = mView.findViewById(R.id.country);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recyclerViewInterface != null){
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            }) ;
        }
        public void bind(Next7daysHolidays nextHoliday){
            name.setText(nextHoliday.getName());
            date.setText(nextHoliday.getDate());
            country.setText(nextHoliday.getCountry());
        }
    }
    private static class HolidaysViewHolder extends RecyclerView.ViewHolder{
        TextView date;
        TextView localName;
        TextView name;

        public HolidaysViewHolder(View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            View mView = itemView;
            date = mView.findViewById(R.id.publicHolidaysDate);
            localName = mView.findViewById(R.id.publicHolidayLocalName);
            name = mView.findViewById(R.id.publicHolidayName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recyclerViewInterface != null){
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }
        public void bind(PublicHolidays publicHoliday){
            date.setText(publicHoliday.getDate());
            localName.setText(publicHoliday.getLocalName());
            name.setText(publicHoliday.getName());
        }
    }
    private static class LongWkndsViewHolder extends RecyclerView.ViewHolder{
        TextView days;
        TextView start;
        TextView end;
        public LongWkndsViewHolder(View itemView, RecyclerViewInterface recyclerViewInterface){
            super(itemView);
            View mView = itemView;
            days = mView.findViewById(R.id.longWkndDays);
            start = mView.findViewById(R.id.longWkndStart);
            end = mView.findViewById(R.id.longWkndEnd);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recyclerViewInterface != null){
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }
        public void bind(LongWeekends longwknd){
            days.setText(longwknd.getDays());
            start.setText(longwknd.getStart());
            end.setText(longwknd.getEnd());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(items.get(position) instanceof Next7daysHolidays){
            return NEXT_7DAYS;
        }else if(items.get(position) instanceof PublicHolidays){
            return PUBLIC_HOLIDAYS;}
        else{return LONG_WEEKEND;}
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if(viewType == NEXT_7DAYS){
            View view = layoutInflater.inflate(R.layout.custom_row,parent, false);

            return new Next7DaysViewHolder(view, recyclerViewInterface);
        }else if(viewType == PUBLIC_HOLIDAYS){
            View view = layoutInflater.inflate(R.layout.custom_row_public_holidays,parent, false);

            return new HolidaysViewHolder(view, recyclerViewInterface);
        }else{
            View view = layoutInflater.inflate(R.layout.custom_row_longweekends, parent, false);

            return new LongWkndsViewHolder(view, recyclerViewInterface);}
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position){
        Object item = items.get(position);
        if(holder instanceof Next7DaysViewHolder){
            ((Next7DaysViewHolder) holder).bind((Next7daysHolidays)item);
        }else if(holder instanceof HolidaysViewHolder){
            ((HolidaysViewHolder)holder).bind((PublicHolidays)item);
        }else{
            ((LongWkndsViewHolder)holder).bind((LongWeekends)item);
        }
    }
    @Override
    public int getItemCount(){
        return items.size();
    }
}
