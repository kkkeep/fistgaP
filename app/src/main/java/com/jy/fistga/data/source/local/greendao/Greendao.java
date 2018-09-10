package com.jy.fistga.data.source.local.greendao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/*
 * created by taofu on 2018/8/30
 **/
public class Greendao {

    private Greendao(){}


    private static DaoSession mDaoSession;

    public static DaoSession getDaoSession(Context context){

        if(mDaoSession == null){

            synchronized (Greendao.class){
                if(mDaoSession == null){
                    DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "fistga-db", null);
                    SQLiteDatabase db = helper.getWritableDatabase();

                    mDaoSession = new DaoMaster(db).newSession();
                }
            }
        }

        return mDaoSession;
    }

}
