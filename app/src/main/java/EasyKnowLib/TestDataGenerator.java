package EasyKnowLib;

import android.database.Cursor;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.DatabaseHelper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class TestDataGenerator {
    private DatabaseHelper myDb;
    private String[] title;
    private String[] meaning;
    private String[] folders;
    private LocalDate startD;
    private LocalDate endD;
    private LocalTime startT;
    private LocalTime endT;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public TestDataGenerator() {
        folders = new String[]{"okok", "nice", "so far, so good", "lets program"};
        title = new String[]{"hello", "world", "test", "Wissen", "super", "perfekt", "ok", "an meiner stelle", "hundert", "guten tag", "alles klar", "Uhr", "Zeit"};
        meaning = new String[]{"hallo", "welt", "testung", "knowledge", "very nice", "perfect", "in ordung", "in your situation", "zwei hundert", "good to see you", "everything is clear", "hour", "time"};
        startD = LocalDate.of(2021, 2, 1);
        endD = LocalDate.now();
        startT = LocalTime.of(0,0);
        endT = LocalTime.of(23, 59);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createNewTestData(DatabaseHelper myDb) {
        for (int i = 0; i < folders.length; i++){
            String folderName = folders[i].toString();
            // insert in db
            myDb.insertNewFolder(folderName);
            Cursor res = myDb.getFolderId(folderName);
            res.moveToFirst();
            String folderID = res.getString(0);
            for (int j = 0; j < 200; j++) {
                Word word = this.createNewWord();
                LocalDate date = randomDate(startD, endD);
                LocalTime time = randomTime(startT, endT);
                int lastTestSuccessful = (int) (Math.random() * 2);
                int learnStatus = (int) (Math.random()*6);
                LocalDateTime timeStamp = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), time.getHour(), time.getMinute());
                // insert in db
                /*myDb.insertNewWord(word.getTitle(), word.getMeaning(), folderID);*/
                myDb.insertNewWord(word.getTitle(), word.getMeaning(), lastTestSuccessful, timeStamp.toString(), learnStatus, folderID);
            }
        }
    }

    public Word createNewWord() {
        Word word = new Word(title[(int) (Math.random() * 12)], meaning[(int) (Math.random() * 12)]);
        return word;
    }

    private LearnFolder createNewFolder() {
        LearnFolder folder = new LearnFolder(folders[(int) (Math.random() * (folders.length - 1))]);
        return folder;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static LocalDate randomDate(LocalDate startInclusive, LocalDate endExclusive) {
        long startEpochDay = startInclusive.toEpochDay();
        long endEpochDay = endExclusive.toEpochDay();
        long randomDay = ThreadLocalRandom
                .current()
                .nextLong(startEpochDay, endEpochDay);

        return LocalDate.ofEpochDay(randomDay);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static LocalTime randomTime(LocalTime startTime, LocalTime endTime) {
        int startSeconds = startTime.toSecondOfDay();
        int endSeconds = endTime.toSecondOfDay();
        int randomTime = ThreadLocalRandom
                .current()
                .nextInt(startSeconds, endSeconds);

        return LocalTime.ofSecondOfDay(randomTime);
    }
}
