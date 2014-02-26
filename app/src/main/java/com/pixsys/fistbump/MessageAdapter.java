package com.pixsys.fistbump;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseObject;

import org.json.JSONArray;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
* Created by shaun on 23/02/2014.
*/
public class MessageAdapter extends ArrayAdapter<ParseObject> {

    protected Context mContext;
    protected List<ParseObject> mMessages;

       public MessageAdapter(Context context, List<ParseObject> messages) {
           super(context, R.layout.message_item, messages);
           mContext = context;
           mMessages = messages;
       }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.message_item, null);
            holder = new ViewHolder();
            holder.iconImageView = (ImageView)convertView.findViewById(R.id.messageIcon);
            holder.nameLabel = (TextView)convertView.findViewById(R.id.senderLabel);
            holder.dateData = (TextView)convertView.findViewById(R.id.textDate);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        ParseObject message = mMessages.get(position);

        if(message.getString(ParseConstants.KEY_FILE_TYPE).equals(ParseConstants.TYPE_IMAGE)) {
            holder.iconImageView.setImageResource(R.drawable.ic_action_picture);
        } else {
            holder.iconImageView.setImageResource(R.drawable.ic_action_play_over_video);
        }

        DateFormat parser = null;
        DateFormat formatter = null;
        Date convertedDate = null;
        String formattedDate = null;

        // Creating SimpleDateFormat
        Log.d("LOG", message.getCreatedAt().toString());
        String dateString = message.getCreatedAt().toString();
        parser = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzzzzzzzz yyyy");
        formatter = new SimpleDateFormat("EEE dd HH:mm");

        try {
            convertedDate = (Date) parser.parse(dateString);
            formattedDate = (String) formatter.format(convertedDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        Log.d("LOG", "Date from yyyyMMdd String in Java : " + convertedDate);
        Log.d("LOG", "Date from yyyyMMdd String in Java : " + formattedDate);

        String recipientIds = message.get("recipientIds").toString();

        Log.d("RECIPIENTS", recipientIds);

        holder.nameLabel.setText(message.getString(ParseConstants.KEY_SENDER_NAME));
        holder.dateData.setText(formattedDate);

        return convertView;
    }

    private static class ViewHolder {
        ImageView iconImageView;
        TextView nameLabel;
        TextView dateData;

    }

    public void refill(List<ParseObject> messages) {
        mMessages.clear();
        mMessages.addAll(messages);
        notifyDataSetChanged();
    }
}
