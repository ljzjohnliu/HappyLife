package com.ilife.happy.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.room.Room;

import com.ilife.common.base.BaseSimpleActivity;
import com.ilife.dataroom.RoomDemoDatabase;
import com.ilife.dataroom.dao.NoteDao;
import com.ilife.dataroom.model.NoteModel;
import com.ilife.happy.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewNoteActivity extends BaseSimpleActivity {
    private static final String TAG = "NewNoteActivity";
    private static final String NOTE_DATE = "note_date";

    private RoomDemoDatabase roomDemoDatabase;
    private NoteDao noteDao;
    private String noteDateFromCalendar;

    @BindView(R.id.note_title)
    EditText noteTitle;
    @BindView(R.id.note_content)
    EditText noteContent;
    @BindView(R.id.note_date)
    EditText noteDate;
    @BindView(R.id.show_tv)
    TextView showTv;

    public static void gotoNewNoteActivity(Context context, String noteDate) {
        if (TextUtils.isEmpty(noteDate)) {
            Toast.makeText(context, "noteDate must be not null!!!", Toast.LENGTH_SHORT).show();
        }
        Intent intent = new Intent(context, NewNoteActivity.class);
        intent.putExtra(NOTE_DATE, noteDate);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_note_layout;
    }

    @Override
    protected void initView() {
        if (getIntent() != null) {
            noteDateFromCalendar = getIntent().getStringExtra(NOTE_DATE);
        }
        Log.d(TAG, "initView: noteDateFromCalendar = " + noteDateFromCalendar);
        roomDemoDatabase = Room.databaseBuilder(this, RoomDemoDatabase.class, "word_database").allowMainThreadQueries().build();
        noteDao = roomDemoDatabase.noteDao();
    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.confirm_button)
    public void onClick() {
        Log.d(TAG, "onClick: ");
        NoteModel note = new NoteModel(10001, noteDate.getText().toString(), noteTitle.getText().toString(), noteContent.getText().toString());
        noteDao.insertOneNote(note);
        show();
    }

    private void show() {
        List<NoteModel> list = noteDao.queryAll();
        StringBuilder sb = new StringBuilder();
        for (NoteModel w : list) {
            sb.append(w.toString() + "\n");
        }
        showTv.setText(sb.toString());
    }
}
