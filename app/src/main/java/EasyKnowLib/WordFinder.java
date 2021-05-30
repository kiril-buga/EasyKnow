package EasyKnowLib;

import android.database.Cursor;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.myapplication.DatabaseHelper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class WordFinder {
    private ArrayList<Word> words;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public WordFinder(DatabaseHelper databaseHelper) {
        words = new ArrayList<>();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Word getWord(DatabaseHelper myDB) {
        Word word = null;

        Cursor res = myDB.getWordsWithNoNotificationDate();
        if (res.getCount() != 0) {
            loadWordsFromDB(res);
            word = words.get((int) (Math.random()) * (words.size() - 1));
            return word;
        }

        res = myDB.getWordsWithMinNotificationDate();
        loadWordsFromDB(res);
        if (words.size() < 2) {
            word = words.get(0);
        } else {
            for (int i = 1; i < words.size(); i++) {
                word = words.get(0);
                int lastWordLearnStatus = words.get(i - 1).getLearnStatus();
                int currentWordLearnStatus = words.get(i).getLearnStatus();

                if (currentWordLearnStatus < lastWordLearnStatus)
                    word = words.get(i);
            }
        }
        return word;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void loadWordsFromDB(Cursor res) {
        words.clear();
        LocalDateTime lastNotificationTime;
        while(res.moveToNext()) {
            int wordId = Integer.parseInt(res.getString(0));
            String title = res.getString(1);
            String meaning = res.getString(2);
            String lastTestSuccessfulString = res.getString(3);
            // Tries to parse String into LocalDateTime
            try {
                lastNotificationTime = LocalDateTime.parse(res.getString(4));
            } catch (Exception e) {
                lastNotificationTime = null;
            }
            int learnStatus = Integer.parseInt(res.getString(5));
            int folderId = Integer.parseInt(res.getString(6));
            boolean lastTestSuccessFul = false;
            // translates DB value into boolean
            if (lastTestSuccessfulString.equals("1"))
                lastTestSuccessFul = true;

            Word word = new Word(wordId, title, meaning, lastNotificationTime, lastTestSuccessFul, learnStatus, folderId);
            words.add(word);
        }
    }

}
