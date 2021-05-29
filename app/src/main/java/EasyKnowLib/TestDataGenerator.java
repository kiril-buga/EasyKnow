package EasyKnowLib;

import android.database.Cursor;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.DatabaseHelper;

public class TestDataGenerator {
    private DatabaseHelper myDb;
    private String[] title;
    private String[] meaning;
    private String[] folders;

    public TestDataGenerator() {
        folders = new String[]{"okok", "nice", "so far, so good", "lets program"};
        title = new String[]{"hello", "world", "test", "Wissen", "super", "perfekt", "ok", "an meiner stelle", "hundert", "guten tag", "alles klar", "Uhr", "Zeit"};
        meaning = new String[]{"hallo", "welt", "testung", "knowledge", "very nice", "perfect", "in ordung", "in your situation", "zwei hundert", "good to see you", "everything is clear", "hour", "time"};
    }

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
                // insert in db
                myDb.insertNewWord(word.getTitle(), word.getMeaning(), folderID);
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
}
