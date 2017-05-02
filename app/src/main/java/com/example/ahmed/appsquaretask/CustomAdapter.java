package com.example.ahmed.appsquaretask;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by ahmed on 4/25/2017.
 */

public class CustomAdapter extends ArrayAdapter<DataModel> implements View.OnClickListener {


    private ArrayList<DataModel> dataSet;
    Context mContext;
    TextView txtRepoName, txtDescription, txtOwnerUserName;
    LinearLayout linearContainer;

 /*   // View lookup cache
    private static class ViewHolder {



    }*/

    public CustomAdapter(ArrayList<DataModel> data, Context context) {
        super(context, R.layout.single_row_main_activity, data);
        this.dataSet = data;
        this.mContext = context;
    }
    //this variable for listView animation
    private int lastPosition = -1;

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        DataModel dataModel = getItem(position);


        final View result;

        LayoutInflater inflater = LayoutInflater.from(getContext());
        convertView = inflater.inflate(R.layout.single_row_main_activity, parent, false);
        txtRepoName = (TextView) convertView.findViewById(R.id.single_row_txtRepoName);
        txtDescription = (TextView) convertView.findViewById(R.id.single_row_txtDescription);
        txtOwnerUserName = (TextView) convertView.findViewById(R.id.single_row_txtOwnerUserName);
        linearContainer=(LinearLayout) convertView.findViewById(R.id.single_row_containerListItem);

        result = convertView;


        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

       /* viewHolder.txtName.setText(dataModel.getUser_name());
        viewHolder.txtPhone.setText(dataModel.getPhone());*/
        txtRepoName.setText(dataModel.getRepoName());
        txtDescription.setText(dataModel.getDescription());
        txtOwnerUserName.setText(dataModel.getOwnerUserName());

        linearContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "html"+position, Toast.LENGTH_SHORT).show();
            }
        });

        // Return the completed view to render on screen
        return convertView;
    }

    @Override
    public void onClick(View view)
    {
        int position = (Integer) view.getTag();
        Object object = getItem(position);
    }
}
