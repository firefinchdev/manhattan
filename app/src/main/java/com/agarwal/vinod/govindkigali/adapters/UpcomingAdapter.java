package com.agarwal.vinod.govindkigali.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.agarwal.vinod.govindkigali.R;
import com.agarwal.vinod.govindkigali.models.Upcoming;
import com.agarwal.vinod.govindkigali.utils.Util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Anirudh Gupta on 12/13/2017.
 */

public class UpcomingAdapter extends RecyclerView.Adapter<UpcomingAdapter.UpcomingViewHolder> {

    Context context;
    ArrayList<Upcoming> upcomings;
    public UpcomingAdapter(Context context, ArrayList<Upcoming> upcomings) {
        this.context = context;
        this.upcomings = upcomings;
        if(this.upcomings == null){
            this.upcomings = new ArrayList<>();
        }
    }

    public void update(@NonNull ArrayList<Upcoming> upcomings){
        this.upcomings = upcomings;
        notifyDataSetChanged();
    }

    @Override
    public UpcomingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return new UpcomingViewHolder(inflater.inflate(R.layout.layout_upcoming,parent,false));
    }

    @Override
    public void onBindViewHolder(UpcomingViewHolder holder, int position) {
        holder.bindView(position);
    }

    @Override
    public int getItemCount() {
        return upcomings.size();
    }

    class UpcomingViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate,tvVenue,tvTime;
        Button btnOptions;

        public UpcomingViewHolder(View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tv_date);
            tvVenue = itemView.findViewById(R.id.tv_venue);
            tvTime = itemView.findViewById(R.id.tv_time);
            btnOptions = itemView.findViewById(R.id.btn_options);
        }

        public void bindView(final int position){
            final String venue = upcomings.get(position).getmVenue();
            final String time = upcomings.get(position).getmTime();
            final int day = upcomings.get(position).getmDate();
            final String month = upcomings.get(position).getmMonth();
            final int year = upcomings.get(position).getmYear();
            tvDate.setText(new StringBuilder().append(month).append(" ").append(day).append("\n").append(year).toString());
            tvVenue.setText(venue);
            tvTime.setText(time);
            btnOptions.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu popup = new PopupMenu(context, view);
                    MenuInflater inflater = popup.getMenuInflater();
                    inflater.inflate(R.menu.upcoming_options, popup.getMenu());
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()){
                                case R.id.navigation_invite:
                                    Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                                    sharingIntent.setType("text/plain");
                                    sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Invitation");
                                    sharingIntent.putExtra(Intent.EXTRA_TEXT,
                                            context.getString(R.string.invite_intent_text)
                                                    + "\n\nVenue: "
                                                    + venue
                                                    + "\n\nTime: "
                                                    + time
                                                    + "\n\nDate: "
                                                    + day + " "+ month + " " + year);
                                    context.startActivity(Intent.createChooser(sharingIntent, context.getResources().getString(R.string.share_using)));
                                    break;



                                case R.id.navigation_add_to_calender:
                                    Intent intent = new Intent(Intent.ACTION_EDIT);
                                    intent.setType("vnd.android.cursor.item/event");
                                    intent.putExtra(CalendarContract.Events.TITLE, context.getString(R.string.add_to_calender_title));
                                    intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                                            Util.timeinMillis(day,month,year,time));
                                    //intent.putExtra(CalendarContract.Events.ALL_DAY, false);// periodicity
                                    intent.putExtra(CalendarContract.Events.EVENT_LOCATION, venue);

                                    intent.putExtra(CalendarContract.Events.DESCRIPTION,"Venue: " + venue + "\nTime: " + time);
                                    context.startActivity(intent);
                                    break;



                                case R.id.navigation_directions:
                                    try {
                                        String url = "https://www.google.com/maps/search/?api=1&query=" + URLEncoder.encode(venue,"utf-8");
                                        Intent intent2 = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                        context.startActivity(intent2);
                                    } catch (UnsupportedEncodingException e) {
                                        Toast.makeText(context, R.string.no_directions, Toast.LENGTH_SHORT).show();
                                        e.printStackTrace();
                                    }
                                    break;
                            }
                            return true;
                        }
                    });
                    popup.show();
                }
            });



        }
    }
}