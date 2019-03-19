package com.example.lab4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> target;
    private SimpleCursorAdapter adapter;
    public MySQLite db = new MySQLite( this );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        String[] values = new String[] {"Pies", "Kot", "Koń", "Gołąb", "Krók", "Dzik", "Karp",
                "Osioł", "Chomik", "Mysz", "Jeż", "Karaluch"};

        this.target=new ArrayList<String>();        //umieszczenie elenetow z tablicy
        this.target.addAll(Arrays.asList(values));

        this.adapter=new SimpleCursorAdapter(this,android.R.layout.simple_list_item_2,
                db.lista(),
                new String[] {"_id", "gatunek"},
                new int[] {android.R.id.text1, android.R.id.text2},SimpleCursorAdapter.IGNORE_ITEM_VIEW_TYPE); //inicjalizacja listy

        ListView listview = (ListView) findViewById(R.id.listView);
        listview.setAdapter(this.adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;

    }

    public void nowyWpis(MenuItem mi)
    {
        Intent intencja=new Intent(this, DodajWpis.class);
        startActivityForResult(intencja, 1);
    }

    @Override
    protected void onActivityResult(int requsetCode, int resultCode, Intent data)
    {
        if(requsetCode==1 && resultCode==RESULT_OK)
        {
            Bundle extras = data.getExtras();
            Animal nowy=(Animal) extras.getSerializable( "nowy" );

            this.db.dodaj(nowy);
            adapter.changeCursor( db.lista() );
            adapter.notifyDataSetChanged();

        }

    }
}
