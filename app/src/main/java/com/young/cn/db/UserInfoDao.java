package com.young.cn.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import io.reactivex.Completable;
import io.reactivex.Flowable;

import java.util.List;

@Dao
public interface UserInfoDao {


    @Query("SELECT * FROM userInfo")
    List<UserInfo> getAll();

    @Query("SELECT * FROM userInfo WHERE uid LIKE :uid LIMIT 1")
    Flowable<UserInfo> queryById(int uid);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertAll(UserInfo... userInfo);

    @Delete
    void delete(UserInfo userInfo);


//    @Insert
//    void insertWords(Word... words);
//
//    @Update
//    void updateWords(Word... words);
//
//    @Delete
//    void deleteWords(Word... words);
//
//    @Query("DELETE FROM WORD")
//    void deleteAllWords();
//
//    @Query("SELECT * FROM WORD ORDER BY ID DESC")
//        // List<Word> getAllWords();
//    LiveData<List<Word>> getAllWordsLive();


}

