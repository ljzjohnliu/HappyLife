package com.ilife.happy.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.ilife.dataroom.RoomDemoDatabase;
import com.ilife.dataroom.dao.UserDao;
import com.ilife.dataroom.model.NoteModel;
import com.ilife.dataroom.model.UserModel;
import com.ilife.happy.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RoomUserActivity extends AppCompatActivity {

    private RoomDemoDatabase roomDemoDatabase;
    private UserDao userDao;

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

        roomDemoDatabase = Room.databaseBuilder(this, RoomDemoDatabase.class, "word_database").allowMainThreadQueries().build();
        userDao = roomDemoDatabase.userDao();
    }

    private void show() {
        List<UserModel> list = userDao.queryAll();
        StringBuilder sb = new StringBuilder();
        for (UserModel w : list) {
            sb.append(w.toString() + "\n");
        }
        showTv.setText(sb.toString());
    }

    private void addOne() {
        UserModel user = new UserModel("13248068695", "555555","小小兄");
        userDao.insertOneUser(user);
        show();
    }

    private void addMulti() {
        UserModel user1 = new UserModel("13248068691", "111111", "小白");
        UserModel user2 = new UserModel("13248068692", "222222", "李指");
        UserModel user3 = new UserModel("13248068693", "333333", "腿哥");
        UserModel user4 = new UserModel("13248068694", "444444", "小黑");

        userDao.insertUser(user1, user2, user3, user4);
        show();
    }

    private void update() {
        UserModel user = userDao.queryByPhone("13248068691");
        user.name = "腿哥222";
        userDao.updateUser(user);
        show();
    }

    private void deleteAll() {
        userDao.deleteAll();
        show();
    }
}
