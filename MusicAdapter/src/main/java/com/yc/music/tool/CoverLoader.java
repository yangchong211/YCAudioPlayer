package com.yc.music.tool;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.LruCache;
import android.view.WindowManager;

import com.yc.music.R;
import com.yc.music.model.AudioBean;
import com.yc.music.utils.ImageUtils;

import java.io.FileNotFoundException;
import java.io.InputStream;


/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2017/03/22
 *     desc  : 专辑封面图片加载器
 *     revise:
 * </pre>
 */
public final class CoverLoader {

    private static final String KEY_NULL = "null";

    /**
     * 封面缓存
     * 使用LruCache作为缓冲集合
     */
    private final LruCache<String, Bitmap> mCoverCache;

    private enum Type {
        THUMBNAIL(""),
        BLUR("#BLUR"),
        ROUND("#ROUND");

        private final String value;
        Type(String value) {
            this.value = value;
        }
    }

    /**
     * 使用单利模式获取对象
     * @return              CoverLoader对象
     */
    public static CoverLoader getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        static CoverLoader instance = new CoverLoader();
    }

    private CoverLoader() {
        // 获取当前进程的可用内存（单位KB）
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        // 缓存大小为当前进程可用内存的1/8
        int cacheSize = maxMemory / 8;
        mCoverCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                //API19
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    return bitmap.getAllocationByteCount() / 1024;
                } else {
                    return bitmap.getByteCount() / 1024;
                }
            }
        };
    }

    /**
     * 获取小图标
     * @param music             music
     * @return                  bitmap对象
     */
    public Bitmap loadThumbnail(AudioBean music) {
        return loadCover(music, Type.THUMBNAIL);
    }

    /**
     * 获取蒙层透明背景bitmap
     * @param music             music
     * @return                  bitmap对象
     */
    public Bitmap loadBlur(AudioBean music) {
        return loadCover(music, Type.BLUR);
    }

    /**
     * 获取蒙层透明背景bitmap
     * @param music             music
     * @return                  bitmap对象
     */
    public Bitmap loadRound(AudioBean music) {
        return loadCover(music, Type.ROUND);
    }


    private Bitmap loadCover(AudioBean music, Type type) {
        Bitmap bitmap;
        String key = getKey(music, type);
        if (TextUtils.isEmpty(key)) {
            bitmap = mCoverCache.get(KEY_NULL.concat(type.value));
            if (bitmap != null) {
                return bitmap;
            }
            bitmap = getDefaultCover(type);
            mCoverCache.put(KEY_NULL.concat(type.value), bitmap);
            return bitmap;
        }
        bitmap = mCoverCache.get(key);
        if (bitmap != null) {
            return bitmap;
        }
        bitmap = loadCoverByType(music, type);
        if (bitmap != null) {
            mCoverCache.put(key, bitmap);
            return bitmap;
        }
        return loadCover(null, type);
    }


    private String getKey(AudioBean music, Type type) {
        if (music == null) {
            return null;
        }
        if ( music.getAlbumId() > 0) {
            return String.valueOf(music.getAlbumId()).concat(type.value);
        } else {
            return null;
        }
    }


    /**
     * 获取默认的bitmap视图
     * @param type          类型
     * @return              bitmap对象
     */
    private Bitmap getDefaultCover(Type type) {
        Context context = BaseAppHelper.get().getContext();
        switch (type) {
            case ROUND:
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.default_cover);
                bitmap = ImageUtils.resizeImage(bitmap,
                        getScreenWidth() / 2, getScreenWidth() / 2);
                return bitmap;
            default:
                return BitmapFactory.decodeResource(context.getResources(), R.drawable.default_cover);
        }
    }


    private Bitmap loadCoverByType(AudioBean music, Type type) {
        Bitmap bitmap = loadCoverFromMediaStore(music.getAlbumId());
        switch (type) {
            case BLUR:
                return ImageUtils.blur(bitmap);
            case ROUND:
                bitmap = ImageUtils.resizeImage(bitmap,
                        getScreenWidth() / 2, getScreenWidth() / 2);
                return ImageUtils.createCircleImage(bitmap);
            default:
                return bitmap;
        }
    }


    /**
     * 从媒体库加载封面<br>
     * 本地音乐
     */
    private Bitmap loadCoverFromMediaStore(long albumId) {
        Context context = BaseAppHelper.get().getContext();
        ContentResolver resolver = context.getContentResolver();
        Uri uri = getMediaStoreAlbumCoverUri(albumId);
        InputStream is;
        try {
            is = resolver.openInputStream(uri);
        } catch (FileNotFoundException ignored) {
            return null;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        return BitmapFactory.decodeStream(is, null, options);
    }

    /**
     * 从下载的图片加载封面<br>
     * 网络音乐
     */
    private Bitmap loadCoverFromFile(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        return BitmapFactory.decodeFile(path, options);
    }

    private Uri getMediaStoreAlbumCoverUri(long albumId) {
        Uri artworkUri = Uri.parse("content://media/external/audio/albumart");
        return ContentUris.withAppendedId(artworkUri, albumId);
    }


    private int getScreenWidth() {
        Context context = BaseAppHelper.get().getContext();
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        if (wm == null) {
            return context.getResources().getDisplayMetrics().widthPixels;
        } else {
            Point point = new Point();
            if (Build.VERSION.SDK_INT >= 17) {
                wm.getDefaultDisplay().getRealSize(point);
            } else {
                wm.getDefaultDisplay().getSize(point);
            }

            return point.x;
        }
    }

    private int getScreenHeight() {
        Context context = BaseAppHelper.get().getContext();
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        if (wm == null) {
            return context.getResources().getDisplayMetrics().heightPixels;
        } else {
            Point point = new Point();
            if (Build.VERSION.SDK_INT >= 17) {
                wm.getDefaultDisplay().getRealSize(point);
            } else {
                wm.getDefaultDisplay().getSize(point);
            }

            return point.y;
        }
    }
}
