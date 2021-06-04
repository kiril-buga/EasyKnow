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

    //@RequiresApi(api = Build.VERSION_CODES.O)
    public WordFinder() {
        words = new ArrayList<>();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Word getWord(DatabaseHelper myDB) {
        Word word = null;
        boolean wordFound = false;

        Cursor res = myDB.getWordsWithNoNotificationDate();
        if (res.getCount() != 0) {
            loadWordsFromDB(res);
            word = words.get(0);
            return word;
        }

        res = myDB.getWordsWithMinNotificationDate();
        loadWordsFromDB(res);
        if (words.size() < 2 && words.size() > 0) {
            word = words.get(0);
        } else if (words.size() >= 2) {
            word = words.get(0);
            int lowestLearnStatus = words.get(0).getLearnStatus();

            if (lowestLearnStatus == 0) {
                return word;
            }

            for (int i = 1; i < words.size() && wordFound == false; i++) {
                int currentWordLearnStatus = words.get(i).getLearnStatus();

                if (currentWordLearnStatus == 0) {
                    word = words.get(i);
                    wordFound = true;
                } else if (currentWordLearnStatus < lowestLearnStatus) {
                    word = words.get(i);
                    lowestLearnStatus = currentWordLearnStatus;
                }
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
