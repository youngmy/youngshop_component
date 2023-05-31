package com.young.cn;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;


@Database(entities = {Word.class}, version = 3, exportSchema = false)
public abstract class WordDataBase extends RoomDatabase {

    private static WordDataBase INSTANCE;

    static synchronized WordDataBase getDataBase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room
                    .databaseBuilder(context.getApplicationContext(), WordDataBase.class, "word_database")
//                    .allowMainThreadQueries()
//                    .fallbackToDestructiveMigration()
                    .addMigrations(MIGRATION_1_2,MIGRATION_2_3)
                    .build();
        }
        return INSTANCE;
    }

    public abstract WordDao getWordDao();

    static final Migration MIGRATION_1_2=new Migration(1,2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE word ADD COLUMN foo_data INTEGER NOT NULL DEFAULT 1");
            database.execSQL("ALTER TABLE word ADD COLUMN bar_data INTEGER NOT NULL DEFAULT 1");
        }
    };

    static final Migration MIGRATION_2_3=new Migration(2,3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE word_temp " +
                    "(id INTEGER PRIMARY KEY NOT NULL,english_word TEXT,chinese_meaning TEXT)");
            database.execSQL("INSERT INTO word_temp (id,english_word,chinese_meaning)" +
                    " SELECT id,english_word,chinese_meaning FROM word");
            database.execSQL("DROP TABLE word");
            database.execSQL("ALTER TABLE word_temp RENAME to word");
        }
    };

}
