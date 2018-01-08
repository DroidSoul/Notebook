package com.example.bearbear.notebook;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button textbtn, imgbtn, videobtn;
    private ListView lv;
    private Intent i;
    private MyAdaptor adaptor;
    private NotesDB notesDB;
    private SQLiteDatabase dbReader;
    private Cursor cursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    public void initView() {
        lv = (ListView) findViewById(R.id.list);
        textbtn = (Button) findViewById(R.id.text);
        imgbtn = (Button) findViewById(R.id.img);
        videobtn = (Button) findViewById(R.id.video);
        textbtn.setOnClickListener(this);
        imgbtn.setOnClickListener(this);
        videobtn.setOnClickListener(this);
        notesDB = new NotesDB(this);
        dbReader = notesDB.getReadableDatabase();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cursor.moveToPosition(position);
                Intent i = new Intent(MainActivity.this, SelectAct.class);
                i.putExtra(notesDB.ID, cursor.getInt(cursor.getColumnIndex(notesDB.ID)));
                i.putExtra(notesDB.CONTENT, cursor.getString(cursor.getColumnIndex(notesDB.CONTENT)));
                i.putExtra(notesDB.TIME, cursor.getString(cursor.getColumnIndex(notesDB.TIME)));
                i.putExtra(notesDB.PATH, cursor.getString(cursor.getColumnIndex(notesDB.PATH)));
                i.putExtra(notesDB.VIDEO, cursor.getString(cursor.getColumnIndex(notesDB.VIDEO)));
                startActivity(i);
            }
        });
    }

    @Override
    public void onClick(View v) {
        i = new Intent(this,AddContent.class);
        switch (v.getId()){
            case R.id.text:
                i.putExtra("flag", "1");
                break;
            case R.id.img:
                i.putExtra("flag", "2");
                break;
            case R.id.video:
                i.putExtra("flag", "3");
                break;
        }
        startActivity(i);

    }
    public void selectDB() {
        cursor = dbReader.query(notesDB.TABLE_NAME, null, null, null, null, null, null);
        adaptor = new MyAdaptor(this, cursor);
        lv.setAdapter(adaptor);
    }

    @Override
    protected void onResume() {
        super.onResume();
        selectDB();
    }
}
