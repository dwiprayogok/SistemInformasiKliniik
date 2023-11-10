package com.example.sisteminformasikliniik.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.sisteminformasikliniik.Model.BaseMenu;
import com.example.sisteminformasikliniik.R;

import java.util.ArrayList;
import java.util.List;

public class BaseMenuAdapter extends ArrayAdapter<BaseMenu> {
    List<BaseMenu> items_list = new ArrayList<>();
    int custom_layout_id;
    private Context context;
    BaseMenuClicked baseMenuClicked;

    public BaseMenuAdapter(Context context,int resource, List<BaseMenu> menuList) {
        super(context, resource, menuList);
        this.context = context;
        this.items_list = menuList;
        custom_layout_id = resource;
    }

    public void setBaseMenuClicked(BaseMenuClicked baseMenuClicked) {
        this.baseMenuClicked = baseMenuClicked;
    }

    @Override
    public int getCount() {
        return items_list.size();
    }

    public interface BaseMenuClicked {
        void onMenuClicked(BaseMenu baseMenu);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(custom_layout_id, null);
        }

        ImageView imageView = v.findViewById(R.id.imgMenu);
        TextView textView = v.findViewById(R.id.tvMenu);

        BaseMenu item = items_list.get(position);
        imageView.setImageResource(item.getImgMenu());
        textView.setText(item.getNameMenu());

        v.setOnClickListener(v1 -> {
            baseMenuClicked.onMenuClicked(item);
        });
        return v;
    }
}
