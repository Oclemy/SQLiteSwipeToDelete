package com.tutorials.hp.sqliteswipetodelete.mRecycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tutorials.hp.sqliteswipetodelete.R;
import com.tutorials.hp.sqliteswipetodelete.mDataBase.DBAdapter;
import com.tutorials.hp.sqliteswipetodelete.mDataObject.Planet;

import java.util.ArrayList;

/**
 * Created by Oclemmy on 5/1/2016 for ProgrammingWizards Channel and http://www.Camposha.com.
 */
public class MyAdapter extends RecyclerView.Adapter<MyHolder> {

    Context c;
    ArrayList<Planet> planets;

    public MyAdapter(Context c, ArrayList<Planet> planets) {
        this.c = c;
        this.planets = planets;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.model,parent,false);
        MyHolder holder=new MyHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
         holder.nametxt.setText(planets.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return planets.size();
    }

    public void deletePlanet(int pos)
    {
        //GET ID
        Planet p=planets.get(pos);
        int id=p.getId();

        //DELETE FROM DB
        DBAdapter db=new DBAdapter(c);
        db.openDB();
        if(db.delete(id))
        {
            planets.remove(pos);
        }else
        {
            Toast.makeText(c,"Unable To Delete",Toast.LENGTH_SHORT).show();
        }

        db.closeDB();

        this.notifyItemRemoved(pos);


    }
}
