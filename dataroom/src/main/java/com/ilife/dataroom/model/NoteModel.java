package com.ilife.dataroom.model;

import android.provider.BaseColumns;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = NoteModel.NOTE_TABLE_NAME,
        indices = {@Index(value = {NoteModel.USER_ID}),
                @Index(value = {NoteModel.DATE}, unique = true),
                @Index(value = {NoteModel.TYPE}, unique = false),
                @Index(value = {NoteModel.TITLE}, unique = false),
                @Index(value = {NoteModel.CONTENT}, unique = false),
                @Index(value = {NoteModel.PLACE}, unique = false),
                @Index(value = {NoteModel.START_TIME}, unique = false),
                @Index(value = {NoteModel.END_TIME}, unique = false),
                @Index(value = {NoteModel.IS_ALL_DAY}, unique = false),
                @Index(value = {NoteModel.REMIND_BEFORE_TIME}, unique = false),
                @Index(value = {NoteModel.IS_REPEAT}, unique = false),
                @Index(value = {NoteModel.STATE}, unique = false)
        })
public class NoteModel /*implements Parcelable */ {

    public static final String NOTE_TABLE_NAME = "note";

    public static final String USER_ID = "user_id";
    public static final String TYPE = "type";
    public static final String DATE = "date";
    public static final String TITLE = "title";
    public static final String CONTENT = "content";
    public static final String PLACE = "place";
    public static final String START_TIME = "start_time";
    public static final String END_TIME = "end_time";
    public static final String IS_ALL_DAY = "is_all_day";
    public static final String REMIND_BEFORE_TIME = "remind_before_time";
    public static final String IS_REPEAT = "is_repeat";
    public static final String RING = "ring";
    public static final String STATE = "state";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = BaseColumns._ID)
    public long id;

    @NonNull
    @ColumnInfo(name = USER_ID)
    public long userId;

    @NonNull
    @ColumnInfo(name = DATE)
    public String date;

    @NonNull
    @ColumnInfo(name = TYPE)
    public int type;

    @NonNull
    @ColumnInfo(name = TITLE)
    public String title;

    @ColumnInfo(name = CONTENT)
    public String content;

    @ColumnInfo(name = PLACE)
    public String place;

    @ColumnInfo(name = START_TIME)
    public String startTime;

    @ColumnInfo(name = END_TIME)
    public String endTime;

    @ColumnInfo(name = IS_ALL_DAY)
    public boolean isAllDay;

    @ColumnInfo(name = REMIND_BEFORE_TIME)
    public String remindBeforeTime;

    @ColumnInfo(name = IS_REPEAT)
    public boolean isRepeat;

    @ColumnInfo(name = RING)
    public String ring;

    @ColumnInfo(name = STATE)
    public int state;

    @Ignore
    public NoteModel(@NonNull long userId, @NonNull String date, @NonNull int type, @NonNull String title, String content, int state) {
        this(userId, date, type, title, content, null, null, null, false, null, false, null, state);
    }

    public NoteModel(@NonNull long userId, @NonNull String date, @NonNull int type, @NonNull String title, String content, String place, String startTime,
                     String endTime, boolean isAllDay, String remindBeforeTime, boolean isRepeat, String ring, int state) {
        this.userId = userId;
        this.date = date;
        this.type = type;
        this.title = title;
        this.content = content;
        this.place = place;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isAllDay = isAllDay;
        this.remindBeforeTime = remindBeforeTime;
        this.isRepeat = isRepeat;
        this.ring = ring;
        this.state = state;
    }

    public String toString() {
        return "Note { userId: " + userId + ", date : " + date + ", type = " + type
                + ", title : " + title + ", content : " + content
                + ", state " + state + " }";
    }
}
