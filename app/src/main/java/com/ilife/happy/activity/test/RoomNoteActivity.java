package com.ilife.happy.activity.test;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.ilife.dataroom.RoomDemoDatabase;
import com.ilife.dataroom.dao.NoteDao;
import com.ilife.dataroom.model.NoteModel;
import com.ilife.happy.R;
import com.ilife.happy.utils.CalendarUtils;
import com.ilife.networkapi.api.ApisManager;
import com.ilife.networkapi.http.WeatherInterface;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RoomNoteActivity extends AppCompatActivity {

    private RoomDemoDatabase roomDemoDatabase;
    private NoteDao noteDao;

    @BindView(R.id.show_tv)
    TextView showTv;

    @OnClick({R.id.add, R.id.add_multi, R.id.query, R.id.update, R.id.delete, R.id.delete_all})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add:
                addOne();
                break;
            case R.id.add_multi:
                addMulti();
                break;
            case R.id.query:
                show();
                break;
            case R.id.update:
                update();
                break;
            case R.id.delete:
                deleteOne();
                break;
            case R.id.delete_all:
                deleteAll();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        ButterKnife.bind(this);

        roomDemoDatabase = RoomDemoDatabase.getInstance(this);
        noteDao = roomDemoDatabase.noteDao();
    }

    private void testNet() {
        ApisManager.getInstance().getApi(WeatherInterface.class).getWeaterUseRxjavaAsJson()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    Log.d("TAG", "gotoAuctionDetailActivity: response = " + response);
                }, throwable -> {
                    Log.d("TAG", "gotoAuctionDetailActivity: throwable = " + throwable.getMessage());
                });
    }

    private void show() {
        List<NoteModel> list = noteDao.queryAll();
        StringBuilder sb = new StringBuilder();
        for (NoteModel w : list) {
            sb.append(w.toString() + "\n");
        }
        showTv.setText(sb.toString());
    }

    private void addOne() {
        NoteModel note = new NoteModel(10001, "2021-04-09 10:33", CalendarUtils.NOTE_TYPE_STUDY, "学习", "考试科目三", 0);
        noteDao.insertOneNote(note);
        show();
    }

    private void addMulti() {
        NoteModel note1 = new NoteModel(10001, "2021-04-05 10:33", CalendarUtils.NOTE_TYPE_SPORT, "健身", "跑步五公里", 1);
        NoteModel note2 = new NoteModel(10001, "2021-04-06 11:01", CalendarUtils.NOTE_TYPE_STUDY, "学习", "学习Java编程思想", 0);
        NoteModel note3 = new NoteModel(10002, "2021-04-07 08:05", CalendarUtils.NOTE_TYPE_RELAX, "娱乐", "万达广场吃好吃得", 1);
        NoteModel note4 = new NoteModel(10002, "2021-04-08 10:15", CalendarUtils.NOTE_TYPE_BABY, "亲子", "去公园赏花", 1);
        NoteModel note5 = new NoteModel(10002, "2021-04-10 11:15", CalendarUtils.NOTE_TYPE_SPORT, "健身", "虐福训练20分钟", 1);
        NoteModel note6 = new NoteModel(10002, "2021-04-11 12:15", CalendarUtils.NOTE_TYPE_SPORT, "健身", "瑜伽半小时", 1);
        NoteModel note7 = new NoteModel(10002, "2021-04-12 14:15", CalendarUtils.NOTE_TYPE_SPORT, "健身", "游泳90分钟", 0);

        noteDao.insertNote(note1, note2, note3, note4, note5, note6, note7);
        show();
    }

    private void update() {
        List<NoteModel> notes = noteDao.queryNotesByDate("2021-04-01 10:33");
        Log.d("TAG", "update: notes size = " + notes.size());

        for (NoteModel note : notes) {
            note.title = "你好222";
            noteDao.updateNotes(note);
        }
        show();
    }

    private void deleteOne() {
        List<NoteModel> notes = noteDao.queryNotesByDate("2021-04-01-10:33");
        for (NoteModel note : notes) {
            noteDao.delete(note);
        }
        show();
    }

    private void deleteAll() {
        noteDao.deleteAll();
        show();
    }
}
