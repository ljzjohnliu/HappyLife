package com.ilife.common.utils;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.util.Size;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

public class FileUtils {
    private final static String TAG = "FileUtils";

    /** The default buffer size ({@value}) to use for */
    private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;

    /** Represents the end-of-file (or stream).*/
    public static final int EOF = -1;

    public static Uri getImageContentUri(Context context, File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
            cursor.close();
            return Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "" + id);
        } else {
            if (imageFile.exists()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    ContentResolver resolver = context.getContentResolver();
                    Uri picCollection = MediaStore.Images.Media
                            .getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
                    ContentValues picDetail = new ContentValues();
                    picDetail.put(MediaStore.Images.Media.DISPLAY_NAME, imageFile.getName());
                    picDetail.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg");
                    picDetail.put(MediaStore.Images.Media.RELATIVE_PATH,"DCIM/" + UUID.randomUUID().toString());
                    picDetail.put(MediaStore.Images.Media.IS_PENDING,1);
                    Uri uri = resolver.insert(picCollection, picDetail);
                    picDetail.clear();
                    picDetail.put(MediaStore.Images.Media.IS_PENDING, 0);
                    resolver.update(picCollection, picDetail, null, null);
                    return uri;
                }else {
                    ContentValues values = new ContentValues();
                    values.put(MediaStore.Images.Media.DATA, filePath);
                    return context.getContentResolver().insert(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                }

            } else {
                return null;
            }
        }
    }

    public static Uri getImageContentUri(Context context, String path) {
        if (TextUtils.isEmpty(path)) return null;
        if (path.startsWith("content://")) return Uri.parse(path);
        String name = path.contains("/") && path.lastIndexOf("/") < path.length()
                ? path.substring(path.lastIndexOf("/") + 1)
                : UUID.randomUUID().toString();

        String filePath = path;
        Cursor cursor = context.getContentResolver().query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    new String[]{MediaStore.Images.Media._ID},
                    MediaStore.Images.Media.DATA + "=? ",
                    new String[]{filePath}, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
            cursor.close();
            return Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "" + id);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                ContentResolver resolver = context.getContentResolver();
                Uri picCollection = MediaStore.Images.Media
                        .getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
                ContentValues picDetail = new ContentValues();
                picDetail.put(MediaStore.Images.Media.DISPLAY_NAME, name);
                picDetail.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg");
                picDetail.put(MediaStore.Images.Media.RELATIVE_PATH,"DCIM/" + UUID.randomUUID().toString());
                picDetail.put(MediaStore.Images.Media.IS_PENDING,1);
                Uri uri = resolver.insert(picCollection, picDetail);
                picDetail.clear();
                picDetail.put(MediaStore.Images.Media.IS_PENDING, 0);
                resolver.update(picCollection, picDetail, null, null);
                return uri;
            }else {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            }
        }
    }

    public static MediaInfo getMediaInfo(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] projections = {MediaStore.Images.Media.DISPLAY_NAME,
                    MediaStore.Images.Media.DATA,
                    MediaStore.Images.Media.MIME_TYPE,
                    MediaStore.Images.Media.WIDTH,
                    MediaStore.Images.Media.HEIGHT,
                    MediaStore.Images.Media.DURATION,
                    MediaStore.Images.Media.SIZE};
            cursor = context.getContentResolver().query(contentUri,  projections, null, null, null);
            int name_column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME);
            int data_column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            int type_column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.MIME_TYPE);
            int width_column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.WIDTH);
            int height_column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.HEIGHT);
            int duration_column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DURATION);
            int size_column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE);
            cursor.moveToFirst();
            String name =  cursor.getString(name_column_index);
            String path =  cursor.getString(data_column_index);
            String type =  cursor.getString(type_column_index);

            int width =  cursor.getInt(width_column_index);
            int height =  cursor.getInt(height_column_index);
            int size =  cursor.getInt(size_column_index);

            Integer duration = null;
            String thumbnailPath = null;
            if (isVideo(type)) {
                duration =  Integer.valueOf(cursor.getInt(duration_column_index));
                thumbnailPath = getVideoThumbnailPath(context, contentUri, path,width/2, height/2);
            }

            MediaInfo mediaInfo = new MediaInfo(name, path, type, width, height, size, duration);
            if (thumbnailPath != null) {
                mediaInfo.setThumbnailPath(thumbnailPath);
            }

            return mediaInfo;
        } catch (Exception e) {
            Log.e(TAG, "getRealPathFromURI Exception : " + e.toString());
            return null;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private static String getVideoThumbnailPath(Context context,
                                               Uri videoUri, String videoPath, int width, int height) {
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            String thubmnailPath;
            String where = MediaStore.Video.Thumbnails.VIDEO_ID
                    + " In ( select _id from video where _data =?)";
            final String[] VIDEO_THUMBNAIL_TABLE = new String[] {
                    MediaStore.Video.Media._ID, // 0
                    MediaStore.Video.Media.DATA, // 1
            };

            try {
                Uri systemVideoUri = MediaStore.Video.Thumbnails.getContentUri("external");
                Cursor c = context.getContentResolver().query(systemVideoUri,
                        VIDEO_THUMBNAIL_TABLE, where, new String[] { videoPath }, null);

                if ((c != null) && c.moveToFirst()) {
                    thubmnailPath = c.getString(1);
                    c.close();
                    Log.i(TAG, "thumb path: " + thubmnailPath);
                    return thubmnailPath;
                } else {
                    c.close();
                    Log.i(TAG, "thumb path is null");
                    return null;
                }
            } catch (Exception e) {
                Log.d(TAG, "Get thumbnail path failed, cause = " + e.toString());
                return null;
            }
        } else {
            String thumbnailDirectory = context.getExternalFilesDir("thumbnails").getAbsolutePath();
            String thumbnailPath = thumbnailDirectory + "/" + videoUri.getLastPathSegment() +".jpg";

            try{
                Bitmap thumbnail = context.getContentResolver().loadThumbnail(videoUri,
                        new Size(width, height), null);
                if (thumbnail != null) {
                    File directory = new File(thumbnailDirectory);
                    if(!directory.exists()) {
                        directory.mkdirs();
                    }
                    File thumbnailFile = new File(thumbnailPath);
                    if (!thumbnailFile.exists()){
                        thumbnailFile.createNewFile();
                    }
                    FileOutputStream out = new FileOutputStream(thumbnailFile);
                    thumbnail.compress(Bitmap.CompressFormat.JPEG, 70, out);
                    out.flush();
                    out.close();
                    Log.d(TAG, String.format("save video thumbnail to private storage: %s",
                            videoUri.getPath() +".jpg"));
                    return thumbnailPath;
                }
            } catch (Exception e) {
                Log.d(TAG, "Get thumbnail path failed, cause = " + e.toString());
            }
        }

        return null;
    }

    private static boolean isVideo(String mimeType) {
       return !mimeType.isEmpty() && mimeType.contains("video");
    }

    public static File getAppDir(Context context) {
        return getStorageDir(context, "ILifeAudio");
    }

    public static File getStorageDir(Context context, String dirName) {
        if (dirName != null && !dirName.isEmpty()) {
            boolean isWriteable = isExternalStorageWritable();
            File file = new File(context.getExternalFilesDir(""), dirName);

            if (file.exists() && file.isDirectory()) {
                return file;
            } else {
                createDir(file);
            }
            return file;
        } else {
            return null;
        }
    }

    /**
     * Checks if external storage is available for read and write.
     */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    /**
     * Checks if external storage is available to at least read.
     */
    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state));
    }


    public static File createDir(File dir) {
        Log.e(TAG, "create directory " + dir.getAbsolutePath());

        if (dir != null) {
            if (!dir.exists()) {
                try {
                    if (dir.mkdirs()) {
                        Log.d(TAG, "Dirs are successfully created");
                        return dir;
                    } else {
                        Log.e(TAG, "Dirs are NOT created! Please check permission write to external storage!");
                    }
                } catch (Exception e) {
                    Log.e(TAG, "create Directory failed, detail = " + e);
                }
            } else {
                Log.d(TAG, "Dir already exists");
                return dir;
            }
        }
        Log.e(TAG, "File is null or unable to create dirs");
        return null;
    }

}
