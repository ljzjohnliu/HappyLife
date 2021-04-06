package com.ilife.happy.model;

import android.content.Context;

import androidx.room.Room;

import com.ilife.common.basemvp.BaseModel;
import com.ilife.dataroom.RoomDemoDatabase;
import com.ilife.dataroom.dao.NoteDao;
import com.ilife.dataroom.model.NoteModel;
import com.ilife.happy.contract.IHomeContract;
import com.ilife.happy.presenter.HomePresenter;

import java.util.List;

public class HomeModel extends BaseModel<HomePresenter, IHomeContract.Model> {

    private RoomDemoDatabase roomDemoDatabase;
    private NoteDao noteDao;

    public HomeModel(HomePresenter presenter) {
        super(presenter);
    }

    @Override
    public IHomeContract.Model getContract() {
        return new IHomeContract.Model() {
            @Override
            public List<NoteModel> queryAllNote(Context context) {
                roomDemoDatabase = Room.databaseBuilder(context, RoomDemoDatabase.class, "word_database").allowMainThreadQueries().build();
                noteDao = roomDemoDatabase.noteDao();
                return noteDao.queryAll();
            }
        };
    }
}
