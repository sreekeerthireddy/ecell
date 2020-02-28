package com.example.android.contact.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.contact.Interface.ItemClickListener;
import com.example.android.contact.R;

public class displayviewholder extends RecyclerView.ViewHolder implements View.OnClickListener
{
     public TextView name,des,l;
     public ItemClickListener listener;
     public ImageView image;
    public displayviewholder(View iview)
    {
        super(iview);
        image= iview.findViewById(R.id.productimage);
        name= iview.findViewById(R.id.productitem);
        des= iview.findViewById(R.id.productdes);

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
