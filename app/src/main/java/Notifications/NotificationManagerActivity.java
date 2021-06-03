package Notifications;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import EasyKnowLib.Day;
import EasyKnowLib.NotificationSettings;
import EasyKnowLib.NotificationStatus;

import com.example.myapplication.AddWordActivity;
import com.example.myapplication.DatabaseHelper;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

import EasyKnowLib.Week;

public class NotificationManagerActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    // Controls
    private Spinner spinner;
    private Switch swMonday;
    private Switch swTuesday;
    private Switch swWednesday;
    private Switch swThursday;
    private Switch swFriday;
    private Switch swSaturday;
    private Switch swSunday;
    private Button btnSaveNotificationSettings;

    private NotificationSettings notificationSettings = new NotificationSettings();
    private DatabaseHelper myDB;
    private boolean userSettingsExistence = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_manager);

        //Controls
        // Spinner
        spinner = (Spinner) findViewById(R.id.spinnerNotificationAmount);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.numberOfNotifications, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        // Toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbarNotificationManager);
        myToolbar.setTitle("Notification Settings");
        setSupportActionBar(myToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        // Switches
        swMonday = findViewById(R.id.switchMonday);
        swMonday.setOnCheckedChangeListener(this::setDay);
        swTuesday = findViewById(R.id.switchTuesday);
        swTuesday.setOnCheckedChangeListener(this::setDay);
        swWednesday = findViewById(R.id.switchWednesday);
        swWednesday.setOnCheckedChangeListener(this::setDay);
        swThursday = findViewById(R.id.switchThursday);
        swThursday.setOnCheckedChangeListener(this::setDay);
        swFriday = findViewById(R.id.switchFriday);
        swFriday.setOnCheckedChangeListener(this::setDay);
        swSaturday = findViewById(R.id.switchSaturday);
        swSaturday.setOnCheckedChangeListener(this::setDay);
        swSunday = findViewById(R.id.switchSunday);
        swSunday.setOnCheckedChangeListener(this::setDay);
        // Button
        btnSaveNotificationSettings = findViewById(R.id.btnSaveNotificationSettings);
        btnSaveNotificationSettings.setOnClickListener(this::saveNotificationSettings);
        // Initialize DB Helper
        myDB = new DatabaseHelper(this);

        // Check for DB content
        readNotificationSettingsFromDB();
        // Display Content in UI
        setNotificationInfo();
        // set current notificationNumber
        notificationSettings.setNotificationNumber(Integer.parseInt(spinner.getSelectedItem().toString()));
    }

    private void saveNotificationSettings(View view) {
        myDB = new DatabaseHelper(this);
        Week week = notificationSettings.getWeek();
        int numberOfNotifications = notificationSettings.getNotificationNumber();
        int monday = setDayInDBFormat(week.isMonday());
        int tuesday = setDayInDBFormat(week.isTuesday());
        int wednesday = setDayInDBFormat(week.isWednesday());
        int thursday = setDayInDBFormat(week.isThursday());
        int friday = setDayInDBFormat(week.isFriday());
        int saturday = setDayInDBFormat(week.isSaturday());
        int sunday = setDayInDBFormat(week.isSunday());
        myDB.updateNotificationSettings(numberOfNotifications, monday, tuesday, wednesday, thursday, friday, saturday, sunday);
        Toast.makeText(NotificationManagerActivity.this, "Notification Settings Saved", Toast.LENGTH_LONG).show();
    }

    private void setDay(CompoundButton compoundButton, boolean b) {
        int currentSwitch = compoundButton.getId();
        Week week = this.notificationSettings.getWeek();
        // sets day in the week object
        if (currentSwitch == swMonday.getId()) {
            week.setDay(Day.MONDAY, b);
        } else if (currentSwitch == swTuesday.getId()) {
            week.setDay(Day.TUESDAY, b);
        } else if (currentSwitch == swWednesday.getId()) {
            week.setDay(Day.WEDNESDAY, b);
        } else if (currentSwitch == swThursday.getId()) {
            week.setDay(Day.THURSDAY, b);
        } else if (currentSwitch == swFriday.getId()) {
            week.setDay(Day.FRIDAY, b);
        } else if (currentSwitch == swSaturday.getId()) {
            week.setDay(Day.SATURDAY, b);
        } else if (currentSwitch == swSunday.getId()) {
            week.setDay(Day.SUNDAY, b);
        }
    }

    private void setNotificationInfo() {
        if(userSettingsExistence == false){
            // show message
            showMessage("Set your Notifications Preferences!",
                    "Here, you can set days of the week and how many times you want to be notified per day.");
            return;
        } else {
            Week week = notificationSettings.getWeek();
            // set status of switches
            swMonday.setChecked(week.isMonday());
            swTuesday.setChecked(week.isTuesday());
            swWednesday.setChecked(week.isWednesday());
            swThursday.setChecked(week.isThursday());
            swFriday.setChecked(week.isFriday());
            swSaturday.setChecked(week.isSaturday());
            swSunday.setChecked(week.isSunday());
            // set spinner value
            spinner.setSelection(notificationSettings.getNotificationNumber());
        }
    }

    private boolean readNotificationSettingsFromDB() {
        userSettingsExistence = false;
        Cursor res = myDB.getNotificationSettings();

        if(res.getCount()==0){
            // creates default entry if userSettings don´t exist
            myDB.insertNewNotificationSettings(notificationSettings.getNotificationNumber());
        } else {
            res.moveToNext();
            // reads notificationSettings from DB and stores them in NotificationSettings object
            notificationSettings.setNotificationNumber(Integer.parseInt(res.getString(1)));
            setDay(Day.MONDAY, Integer.parseInt((res.getString(2))));
            setDay(Day.TUESDAY, Integer.parseInt((res.getString(3))));
            setDay(Day.WEDNESDAY, Integer.parseInt((res.getString(4))));
            setDay(Day.THURSDAY, Integer.parseInt((res.getString(5))));
            setDay(Day.FRIDAY, Integer.parseInt((res.getString(6))));
            setDay(Day.SATURDAY, Integer.parseInt((res.getString(7))));
            setDay(Day.SUNDAY, Integer.parseInt((res.getString(8))));
            userSettingsExistence = true;
        }

        return userSettingsExistence;
    }

    private void setDay(Day day, int booleanAsInt) {
        Week week = this.notificationSettings.getWeek();
        if (booleanAsInt == 0) {
            week.setDay(day, false);
        } else {
            week.setDay(day, true);
        }
    }

    private int setDayInDBFormat(boolean day) {
        if (day == false) {
            return 0;
        } else {
            return 1;
        }
    }

    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        notificationSettings.setNotificationNumber(Integer.parseInt(parent.getItemAtPosition(position).toString()));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(NotificationManagerActivity.this, MainActivity.class );
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}