package com.ilife.dataroom;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.ilife.dataroom.dao.FamousAphorismDao;
import com.ilife.dataroom.dao.NoteDao;
import com.ilife.dataroom.dao.UserDao;
import com.ilife.dataroom.model.FamousAphorismModel;
import com.ilife.dataroom.model.NoteModel;
import com.ilife.dataroom.model.UserModel;

@Database(entities = {
        UserModel.class, NoteModel.class, FamousAphorismModel.class
},
        version = 1, exportSchema = true)
public abstract class RoomDemoDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "room_ilife";

    private static RoomDemoDatabase sInstance;

    public abstract UserDao userDao();

    public abstract NoteDao noteDao();

    public abstract FamousAphorismDao famousAphorismDao();

    public static RoomDemoDatabase getInstance(Context context) {
        Log.d("TAG", "getInstance: sInstance = " + sInstance);
        if (sInstance == null) {
            synchronized (RoomDemoDatabase.class) {
                if (sInstance == null) {
                    sInstance = buildDatabase(context);
                }
            }
        }
        return sInstance;
    }

    /**
     * 执行 fallbackToDestructiveMigration()  Room启动时将检测version是否发生增加，如果有，
     * 那么将找到Migration去执行特定的操作。如果没有因为 fallbackToDestructiveMigration()。将会删除数据库并重建（此时的确不会crash，但所有数据丢失。）
     *
     * @param appContext
     * @return
     */
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
//                .fallbackToDestructiveMigration()
//                .addMigrations(MIGRATION_1_2)
                .build();
    }
}
