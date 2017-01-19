package com.wyuyc.shoppingmall.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wyuyc.shoppingmall.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yc on 2017/1/16.
 */

public class ExpandableListViewAdapter extends BaseExpandableListAdapter {
    private final Context mContext;
    private final ArrayList<String> group;
    private final ArrayList<List<String>> child;
    private int childP;
    private int groupP;

    public ExpandableListViewAdapter(Context context, ArrayList<String> group, ArrayList<List<String>> child) {
        this.mContext = context;
        this.group = group;
        this.child = child;
    }

    @Override
    public int getGroupCount() {
        return group.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return child.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return group.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return child.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.group_list_item, null);
            holder = new ViewHolder();
            holder.textView = (TextView) convertView.findViewById(R.id.textView);
            holder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView.setText(group.get(groupPosition));
        holder.textView.setPadding(0, 10, 0, 10);
        if (isExpanded) {
            holder.imageView.setImageResource(R.drawable.filter_list_selected);
        } else {
            holder.imageView.setImageResource(R.drawable.filter_list_unselected);
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.child_list_item, null);
            holder = new ViewHolder();
            holder.textView = (TextView) convertView.findViewById(R.id.textView);
            holder.childImageView = (ImageView) convertView.findViewById(R.id.childImageView);
            holder.ll_child_root = (LinearLayout) convertView.findViewById(R.id.ll_child_root);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (groupPosition != 0) {
            holder.textView.setText(child.get(groupPosition).get(childPosition));
        }
        if (childP == childPosition && groupP == groupPosition) {
            holder.childImageView.setVisibility(View.VISIBLE);
            notifyDataSetChanged();
        } else {
            holder.childImageView.setVisibility(View.GONE);
            notifyDataSetChanged();
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        groupP = groupPosition;
        childP = childPosition;
        return true;
    }

    class ViewHolder {
        TextView textView;
        ImageView imageView;
        ImageView childImageView;
        LinearLayout ll_child_root;
    }
}

