package Notifications;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import EasyKnowLib.Day;
import EasyKnowLib.NotificationStatus;

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

    private int currentNotificationNumber;
    private NotificationStatus notificationStatus = new NotificationStatus();
    DatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_manager);

        // Spinner
        Spinner spinner = (Spinner) findViewById(R.id.spinnerNotificationAmount);
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

        // Initialize DB Helper
        myDB = new DatabaseHelper(this);

        // Check for DB content
        setNotificationInfo();
    }

    private void setDay(CompoundButton compoundButton, boolean b) {
        int currentSwitch = compoundButton.getId();
        Week week = this.notificationStatus.getWeek();
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
        Cursor res = myDB.getAllFolders();
        if(res.getCount()==0){
            // show message
            showMessage("Set your Notifications Perferences!", "Here, you can tell the app at which days in the week and how many times you want to be notified. At the moment, all notifications are disabled.");
            myDB.insertNewNotificationStatus(notificationStatus.getNotificationNumber());
            return;
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
        int currentNotificationNumber = Integer.parseInt(parent.getItemAtPosition(position).toString());
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

    public int getCurrentNotificationNumber() {
        return currentNotificationNumber;
    }
}