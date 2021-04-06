package com.ilife.happy.contract;

import android.content.Context;

import com.ilife.common.model.BaseEntity;
import com.ilife.dataroom.model.NoteModel;

import java.util.List;

public interface IHomeContract {
    interface Model {
        List<NoteModel> queryAllNote(Context context);
    }

    interface View<T extends BaseEntity> {
        void onNoteResult(List<NoteModel> notes);
    }

    interface Presenter {
        void getAllNotes(Context context);
    }
}
