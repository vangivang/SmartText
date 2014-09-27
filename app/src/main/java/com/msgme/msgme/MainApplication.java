package com.msgme.msgme;

import android.app.Application;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.msgme.msgme.utils.Tools;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DefaultConfigurationFactory;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.Locale;

/**
 * Created by alonm on 8/30/14.
 */
public class MainApplication extends Application {

    private SharedPreferences mPref;

    @Override
    public void onCreate() {
        super.onCreate();
        mPref = PreferenceManager.getDefaultSharedPreferences(this);

        // If a configuration for the UniversalImageLoader wasn't create yet
        if (!ImageLoader.getInstance().isInited()) {

            // Create default options which will be used for every
            //  displayImage(...) call if no options will be passed to this method
            // See more display options here: https://github.com/nostra13/Android-Universal-Image-Loader#display-options
            DisplayImageOptions imageLoaderOptions = new DisplayImageOptions.Builder()
                    .resetViewBeforeLoading(true)
                    .cacheInMemory(true)
                    .cacheOnDisc(true)
                    .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                    .displayer(new FadeInBitmapDisplayer(350, true, true, false))
                    .build();

            // Create global configuration and initialize ImageLoader with this configuration
            ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(this)
                    .threadPoolSize(3)
                    .memoryCacheSizePercentage(30)
                    .discCacheExtraOptions(0, 0, Bitmap.CompressFormat.JPEG, 100, null)
                    .threadPriority(Thread.MIN_PRIORITY)
                    .tasksProcessingOrder(QueueProcessingType.LIFO)
                    .defaultDisplayImageOptions(imageLoaderOptions);

            ImageLoader.getInstance().init(config.build());
        }
    }

    public SharedPreferences getPref() {
        return mPref;
    }

    /**
     * Return a String representation of the selected device language
     * @return
     * <ul>
     * <li>"EN" for English</li>
     * <li>"PT" for Portuguese</li>
     * <li>"ES" for Spanish</li>
     * <li>"AR" for Arabic</li>
     * <li>"ZH" for Chinese</li>
     * </ul>
     *
     * Or "EN" if none of these is selected
     */
    public String getLanguage() {
        return Tools.getLocale(Locale.getDefault()) != null ? Tools.getLocale(Locale.getDefault()) : "EN";
    }
}
