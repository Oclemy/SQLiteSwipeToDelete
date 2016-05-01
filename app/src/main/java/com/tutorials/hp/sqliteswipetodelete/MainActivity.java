package com.tutorials.hp.sqliteswipetodelete;

import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tutorials.hp.sqliteswipetodelete.mDataBase.DBAdapter;
import com.tutorials.hp.sqliteswipetodelete.mDataObject.Planet;
import com.tutorials.hp.sqliteswipetodelete.mRecycler.MyAdapter;
import com.tutorials.hp.sqliteswipetodelete.mSwiper.SwipeHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView rv;
    MyAdapter adapter;
    EditText nameEditText;
    Button saveBtn,retrieveBtn;
    ArrayList<Planet> planets=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        rv= (RecyclerView) findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));

        adapter=new MyAdapter(this,planets);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayDialog();
            }
        });

        ItemTouchHelper.Callback callback=new SwipeHelper(adapter);
        ItemTouchHelper helper=new ItemTouchHelper(callback);
        helper.attachToRecyclerView(rv);

    }

    private void displayDialog()
    {
        Dialog d=new Dialog(this);
        d.setTitle("SQLite database");
        d.setContentView(R.layout.dialog_layout);

        nameEditText= (EditText) d.findViewById(R.id.nameEditTxt);
        saveBtn= (Button) d.findViewById(R.id.saveBtn);
        retrieveBtn= (Button) d.findViewById(R.id.retrieveBtn);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 save(nameEditText.getText().toString());
            }
        });

        retrieveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPlanets();
            }
        });

        //SHOW DIALOG
        d.show();

    }

    //SAVE
    private void save(String name)
    {
        DBAdapter db=new DBAdapter(this);
        db.openDB();
        if(db.add(name))
        {
            nameEditText.setText("");
        }else
        {
            Toast.makeText(this,"Unable to save",Toast.LENGTH_SHORT).show();
        }

        db.closeDB();
    }

    //RETRIEVE
    private void getPlanets()
    {
        planets.clear();

        DBAdapter db=new DBAdapter(this);
        db.openDB();
        Cursor c=db.retrieve();

        while (c.moveToNext())
        {
            int id=c.getInt(0);
            String name=c.getString(1);

            Planet p=new Planet();
            p.setName(name);
            p.setId(id);

            planets.add(p);
        }
        db.closeDB();

        if(planets.size()>0)
        {
            rv.setAdapter(adapter);
        }
    }

}










