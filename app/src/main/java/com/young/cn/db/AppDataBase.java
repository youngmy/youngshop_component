package com.young.cn.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {UserInfo.class}, version = 1, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {


    private static volatile AppDataBase mInstance;

    public static AppDataBase getInstance(Context context) {
        if (mInstance == null) {
            synchronized (AppDataBase.class) {
                mInstance = Room.databaseBuilder(context, AppDataBase.class, "user_database").build();
//                    .allowMainThreadQueries()
//                    .fallbackToDestructiveMigration()
//                    .addMigrations(MIGRATION_1_2,MIGRATION_2_3)

            }
        }
        return mInstance;
    }

    public abstract UserInfoDao getUserInfoDao();

//    static final Migration MIGRATION_1_2=new Migration(1,2) {
//        @Override
//        public void migrate(@NonNull SupportSQLiteDatabase database) {
//            database.execSQL("ALTER TABLE word ADD COLUMN foo_data INTEGER NOT NULL DEFAULT 1");
//            database.execSQL("ALTER TABLE word ADD COLUMN bar_data INTEGER NOT NULL DEFAULT 1");
//        }
//    };
//
//    static final Migration MIGRATION_2_3=new Migration(2,3) {
//        @Override
//        public void migrate(@NonNull SupportSQLiteDatabase database) {
//            database.execSQL("CREATE TABLE word_temp " +
//                    "(id INTEGER PRIMARY KEY NOT NULL,english_word TEXT,chinese_meaning TEXT)");
//            database.execSQL("INSERT INTO word_temp (id,english_word,chinese_meaning)" +
//                    " SELECT id,english_word,chinese_meaning FROM word");
//            database.execSQL("DROP TABLE word");
//            database.execSQL("ALTER TABLE word_temp RENAME to word");
//        }
//    };

}
