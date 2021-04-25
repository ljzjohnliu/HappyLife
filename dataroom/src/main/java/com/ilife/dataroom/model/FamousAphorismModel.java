package com.ilife.dataroom.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = FamousAphorismModel.FAMOUS_APHORISM_TABLE_NAME,
        indices = {@Index(value = FamousAphorismModel.CONTENT_ID,unique = true),
                @Index(value = FamousAphorismModel.CONTENT,unique = false),
                @Index(value = FamousAphorismModel.MRNAME,unique = false)})
public class FamousAphorismModel {

    public static final String FAMOUS_APHORISM_TABLE_NAME = "famous_aphorism_table";

    public static final String CONTENT_ID = "contentId";

    public static final String CONTENT = "content";

    public static final String MRNAME = "mrName";

    public static final String SHOW_TAG = "showTag";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = CONTENT_ID)
    public long contentId;

    @ColumnInfo(name = CONTENT)
    public String content;

    @ColumnInfo(name = MRNAME)
    public String mrName;

    @ColumnInfo(name = SHOW_TAG)
    public boolean showTag;

    public FamousAphorismModel(String content, String mrName, boolean showTag) {
        this.content = content;
        this.mrName = mrName;
        this.showTag = showTag;
    }
}
