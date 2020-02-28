package com.example.android.contact.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.contact.Interface.ItemClickListener;
import com.example.android.contact.R;

public class evnetdisplayviewholder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView name,des;
    public ItemClickListener listener;
    public ImageView image;
    public Button delete;
    public evnetdisplayviewholder(View iview)
    {
        super(iview);
        image= iview.findViewById(R.id.eventtimage);
        name= iview.findViewById(R.id.eventname);
        //des=(TextView)iview.findViewById(R.id.eventdes);
        delete= iview.findViewById(R.id.del);
    }

    public void setitemclicklistener(ItemClickListener listener)
    {
        this.listener=listener;
    }
    @Override
    public void onClick(View view)
    {
        listener.onClick(view,getAdapterPosition(),false);
    }
}
