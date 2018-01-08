package com.example.bearbear.notebook;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

/**
 * Created by bear&bear on 6/25/2016.
 */
public class SelectAct extends Activity implements View.OnClickListener{

    private Button s_delete, s_return;
    private ImageView s_img;
    private VideoView s_video;
    private TextView s_tv;
    private NotesDB notesDB;
    private SQLiteDatabase dbWriter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selectact);
//        System.out.println(getIntent().getIntExtra(NotesDB.ID, 0));
        s_delete = (Button) findViewById(R.id.s_delete);
        s_return = (Button) findViewById(R.id.s_return);
        s_img = (ImageView) findViewById(R.id.s_img);
        s_video = (VideoView) findViewById(R.id.s_video);
        s_tv = (TextView) findViewById(R.id.s_tv);
        notesDB = new NotesDB(this);
        dbWriter = notesDB.getWritableDatabase();
        s_delete.setOnClickListener(this);
        s_return.setOnClickListener(this);
        if (getIntent().getStringExtra(notesDB.PATH).equals("null")) {
            s_img.setVisibility(View.GONE);
        }else {
            s_img.setVisibility(View.VISIBLE);
        }
        if (getIntent().getStringExtra(notesDB.VIDEO).equals("null")) {
            s_video.setVisibility(View.GONE);
        }else {
            s_video.setVisibility(View.VISIBLE);
        }
        s_tv.setText(getIntent().getStringExtra(notesDB.CONTENT));
        s_img.setImageBitmap(BitmapFactory.decodeFile(getIntent().getStringExtra(notesDB.PATH)));
        s_video.setVideoURI(Uri.parse(getIntent().getStringExtra(notesDB.VIDEO)));
        s_video.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.s_delete:
                deleteData();

                break;
            case R.id.s_return:

                break;
        }
        finish();

    }

    public void deleteData() {
        dbWriter.delete(notesDB.TABLE_NAME, "_id=" + getIntent().getIntExtra(notesDB.ID, 0), null);

    }
}
