package com.ilife.happy.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ilife.common.base.BaseSimpleActivity;
import com.ilife.dataroom.RoomDemoDatabase;
import com.ilife.dataroom.dao.NoteDao;
import com.ilife.dataroom.model.NoteModel;
import com.ilife.happy.R;
import com.ilife.happy.adapter.SpinnerAdapter;
import com.ilife.happy.utils.CalendarUtils;
import com.ilife.happy.widget.datepicker.CustomDatePicker;
import com.ilife.happy.widget.datepicker.DateFormatUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class NewNoteActivity extends BaseSimpleActivity {
    private static final String TAG = "NewNoteActivity";
    private static final String NOTE_DATE = "note_date";

    private RoomDemoDatabase roomDemoDatabase;
    private NoteDao noteDao;
    private String noteDateFromCalendar;

    private CustomDatePicker mTimerPicker;

    @BindView(R.id.type_spinner)
    Spinner typeSpinner;
    @BindView(R.id.note_content)
    EditText noteContent;
    @BindView(R.id.tv_selected_time)
    TextView mTvSelectedTime;
    @BindView(R.id.show_tv)
    TextView showTv;

    private int noteType;
    private String noteTitle;

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
        roomDemoDatabase = RoomDemoDatabase.getInstance(this);
        noteDao = roomDemoDatabase.noteDao();

        //????????????????????????????????????spinner
        typeSpinner.setAdapter(new SpinnerAdapter(CalendarUtils.image, CalendarUtils.noteTypes, this));
        //????????????
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //???item???????????????????????????
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //??????????????????????????????
                noteType = position;
                Log.d(TAG, "onItemSelected: noteType = " + noteType);
                noteTitle = CalendarUtils.noteTypes[position];
                //?????????????????????????????????????????????
                Toast.makeText(getApplicationContext(), noteTitle, Toast.LENGTH_SHORT).show();
            }

            //?????????patent???????????????????????????????????????
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void initData() {
        initTimerPicker();
    }

    @OnClick({R.id.confirm_button, R.id.set_time})
    public void onClick(View view) {
        Log.d(TAG, "onClick: ");
        switch (view.getId()) {
            case R.id.confirm_button:
                NoteModel note = new NoteModel(10001, mTvSelectedTime.getText().toString(), noteType, noteTitle, noteContent.getText().toString(), 0);
                Log.d(TAG, "onClick: noteType = " + noteType);
                noteDao.insertOneNote(note);
                show();
                break;
            case R.id.set_time:
                mTimerPicker.show(mTvSelectedTime.getText().toString());
                break;
        }
    }

    private void initTimerPicker() {
//        String beginTime = "2021-01-01 00:00";
        String beginTime = DateFormatUtils.long2Str(System.currentTimeMillis(), true);
        String endTime = "2024-12-31 23:59";

        mTvSelectedTime.setText(beginTime);

        // ??????????????????????????????????????????????????????yyyy-MM-dd HH:mm
        mTimerPicker = new CustomDatePicker(this, new CustomDatePicker.Callback() {
            @Override
            public void onTimeSelected(long timestamp) {
                mTvSelectedTime.setText(DateFormatUtils.long2Str(timestamp, true));
            }
        }, beginTime, endTime);
        // ??????????????????????????????????????????
        mTimerPicker.setCancelable(true);
        // ???????????????
        mTimerPicker.setCanShowPreciseTime(true);
        // ??????????????????
        mTimerPicker.setScrollLoop(true);
        // ??????????????????
        mTimerPicker.setCanShowAnim(true);
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
