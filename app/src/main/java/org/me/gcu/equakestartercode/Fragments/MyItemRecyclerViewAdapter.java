package org.me.gcu.equakestartercode.Fragments;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.me.gcu.equakestartercode.Models.EarthQuakeModel;
import org.me.gcu.equakestartercode.R;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
/**
 * Name: Andrew Wright
 * Student ID: S1711082
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>{

    private final List<EarthQuakeModel> mValues;
    private Method getMethod;

    public MyItemRecyclerViewAdapter(List<EarthQuakeModel> items, Method getMethod) {
        mValues = items;
        this.getMethod = getMethod;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
//        holder.mIdView.setText(mValues.get(position).getTitle());
        try {
            holder.mContentView.setText(getMethod.invoke(mValues.get(position)).toString());
            if(holder.mItem.getMagnitude() <= 2.0){
                holder.mView.setBackgroundColor(Color.GREEN);
            }
            else if(holder.mItem.getMagnitude() > 2.0 && holder.mItem.getMagnitude() <= 4.0){
                holder.mView.setBackgroundColor(Color.YELLOW);
            }
            else if(holder.mItem.getMagnitude() > 4.0){
                holder.mView.setBackgroundColor(Color.RED);
            }

            holder.mContentView.setOnClickListener(view -> {
                Bundle bundle = new Bundle();
                bundle.putParcelable("data", holder.mItem);
                Navigation.findNavController(view).navigate(R.id.action_mainPage_to_dataFragment,bundle);
            });
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public EarthQuakeModel mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}