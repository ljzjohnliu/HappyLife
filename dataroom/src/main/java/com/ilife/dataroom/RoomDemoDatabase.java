package com.ilife.dataroom;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.ilife.dataroom.dao.NoteDao;
import com.ilife.dataroom.dao.UserDao;
import com.ilife.dataroom.model.NoteModel;
import com.ilife.dataroom.model.UserModel;

@Database(entities = {
        UserModel.class, NoteModel.class
},
        version = 1, exportSchema = true)
public abstract class RoomDemoDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "room_ilife";

    private static RoomDemoDatabase sInstance;

    public abstract UserDao userDao();

    public abstract NoteDao noteDao();

    public static RoomDemoDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (RoomDemoDatabase.class) {
                if (sInstance == null) {
                    sInstance = buildDatabase(context);
                }
            }
        }
        return sInstance;
    }

    private static RoomDemoDatabase buildDatabase(final Context appContext) {
        return Room.databaseBuilder(appContext, RoomDemoDatabase.class, DATABASE_NAME)
                .allowMainThreadQueries()
//                .openHelperFactory(new SafeHelperFactory("123456".toCharArray()))
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);

                    }

                    @Override
                    public void onOpen(@NonNull SupportSQLiteDatabase db) {
                        super.onOpen(db);
                    }

                })
                .build();
    }
}
