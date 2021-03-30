package com.example.musiccloud.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.musiccloud.Model.Playlist;
import com.example.musiccloud.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PlaylistAdapter extends ArrayAdapter<Playlist> {
    public PlaylistAdapter(@NonNull Context context, int resource, @NonNull List<Playlist> objects) {
        super(context, resource, objects);
    }
    class ViewHoleder {
        TextView txttenplaylist;
        ImageView imgbackground, imgplaylist;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHoleder viewHoleder = null;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.dong_playlist, null);
            viewHoleder = new ViewHoleder();
            viewHoleder.txttenplaylist = convertView.findViewById(R.id.textviewtenplaylist);
            viewHoleder.imgplaylist = convertView.findViewById(R.id.imageviewplaylist);
            viewHoleder.imgbackground = convertView.findViewById(R.id.imagebackgroundplaylist);
            convertView.setTag(viewHoleder);
        }else {
            viewHoleder = (ViewHoleder) convertView.getTag();
        }

        Playlist playlist = getItem(position);
        Picasso.get().load(playlist.getBackground()).into(viewHoleder.imgbackground);
        Picasso.get().load(playlist.getIcon()).into(viewHoleder.imgplaylist);
        viewHoleder.txttenplaylist.setText(playlist.getTen());

        return convertView;
    }
}
