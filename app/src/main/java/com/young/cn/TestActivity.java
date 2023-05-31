package com.young.cn;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.room.Room;

import java.util.List;

public class TestActivity extends AppCompatActivity {

    public TextView contentTextView, insertTextView, updateTextView, deleteTextView, clearTextView;
//    WordDataBase wordDataBase;
//    WordDao wordDao;
//    LiveData<List<Word>> allWordsLive;
    WordViewModel wordViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_2);
//        wordViewModel= ViewModelProviders.of(this)
//                .get(WordViewModel.class);
//        contentTextView = findViewById(R.id.contentTextView);
//        insertTextView = findViewById(R.id.insertTextView);
//        updateTextView = findViewById(R.id.updateTextView);
//        deleteTextView = findViewById(R.id.deleteTextView);
//        clearTextView = findViewById(R.id.clearTextView);
//        insertTextView.setOnClickListener(view -> {
//            Word word1 = new Word("Hello","你好！");
//            Word word2 = new Word("World","世界！");
////            wordDao.insertWords(word1,word2);
////            new InsertAsyncTask(wordDao).execute(word1,word2);
////            updateView();
//            wordViewModel.insertWords(word1,word2);
//        });
//        updateTextView.setOnClickListener(view -> {
//            Word word = new Word("Hi","你好啊！");
//            word.setId(20);
////            wordDao.updateWords(word);
////            new UpdateAsyncTask(wordDao).execute(word);
////            updateView();
//            wordViewModel.updateWords(word);
//        });
//        deleteTextView.setOnClickListener(view -> {
//            Word word = new Word("Hi","你好啊！");
//            word.setId(17);
////            wordDao.deleteWords(word);
////            new DeleteAsyncTask(wordDao).execute(word);
////            updateView();
//            wordViewModel.deleteWords(word);
//        });
//        clearTextView.setOnClickListener(view -> {
////            wordDao.deleteAllWords();
////            new DeleteAllAsyncTask(wordDao).execute();
////            updateView();
//            wordViewModel.deleteAllWords();
//        });
////        wordDataBase = Room
////                .databaseBuilder(this, WordDataBase.class,
////                        "word_database")
////                .allowMainThreadQueries()
////                .build();
////
////        wordDao = wordDataBase.getWordDao();
////        allWordsLive=wordDao.getAllWordsLive();
//        updateView();
    }



    private void updateView() {
        wordViewModel.getAllWordsLive().observe(this, words -> {
            StringBuilder text = new StringBuilder();
            for (int i = 0; i < words.size(); i++) {
                Word word = words.get(i);
                text.append(word.getId()).append(":").append(word.getWord()).append("=").append(word.getChineseMeaning()).append("\n");
            }
            contentTextView.setText(text.toString());
        });
//        List<Word> list = wordDao.getAllWords();
//        String text = "";
//        for (int i = 0; i < list.size(); i++) {
//            Word word = list.get(i);
//            text += word.getId() + ":" + word.getWord() + "=" + word.getChineseMeaning()+"\n";
//        }
//        contentTextView.setText(text);
    }



}
