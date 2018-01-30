package com.aliagushutapea.convertion.dependencyinjection.module_mpv;

import android.content.Context;

import com.aliagushutapea.convertion.database_helper.DatabaseHelper;

/**
 * Created by ali on 26/01/18.
 */
/*@Module*/
public class ContextModule {
    private final Context mContext;

    public ContextModule(Context mContext) {
        this.mContext = mContext;
    }

    //@Provides
    Context provideContext() {
        return mContext;
    }

    //@Provides
    DatabaseHelper provideDatabaseHelper() {
        return new DatabaseHelper(mContext);
    }
}
