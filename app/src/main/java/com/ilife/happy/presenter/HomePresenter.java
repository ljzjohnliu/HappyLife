package com.ilife.happy.presenter;

import android.content.Context;

import com.ilife.common.basemvp.BasePresenter;
import com.ilife.dataroom.model.NoteModel;
import com.ilife.happy.contract.IHomeContract;
import com.ilife.happy.fragment.HomeFragment;
import com.ilife.happy.model.HomeModel;

import java.util.List;

public class HomePresenter extends BasePresenter<HomeFragment, HomeModel, IHomeContract.Presenter> {

    private final static String TAG = "HomePresenter";

    public HomePresenter() {
        super();
    }

    @Override
    public IHomeContract.Presenter getContract() {
        return new IHomeContract.Presenter() {
            @Override
            public void getAllNotes(Context context) {
                List<NoteModel> notes = getModel().getContract().queryAllNote(context);
                getView().getContract().onNoteResult(notes);
            }
        };
    }

    @Override
    public HomeModel getModel() {
        return new HomeModel(this);
    }
}
