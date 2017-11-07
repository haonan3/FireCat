package edu.illinois.firecat;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.net.URLEncoder;

/**
 * Created by zilles on 11/7/17.
 */

public class CatViewHolder extends RecyclerView.ViewHolder {

    public View itemView;
    public TextView nameTextView;
    public Button locationButton;
    public ImageView imageView;

    public CatViewHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
        this.nameTextView = (TextView) itemView.findViewById(R.id.nameTextView);
        this.locationButton = (Button) itemView.findViewById(R.id.locationTextView);
        this.imageView = (ImageView) itemView.findViewById(R.id.imageView);
    }

    public void bindCat(Cat cat) {
        nameTextView.setText(cat.getName());

        final String location = cat.getLocation();
        locationButton.setText(location);

        final String imageUrl = cat.getImageUrl();
        if (imageUrl != null) {
            final Context context = itemView.getContext();
            Picasso.with(context).load(imageUrl).into(imageView);
        }
    }
}
