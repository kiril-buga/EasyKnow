package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;

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
    private Week week = new Week();

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
        swWednesday = findViewById(R.id.switchWednesday);
        swThursday = findViewById(R.id.switchThursday);
        swFriday = findViewById(R.id.switchFriday);
        swSaturday = findViewById(R.id.switchSaturday);
        swSunday = findViewById(R.id.switchSunday);
    }

    private void setDay(CompoundButton compoundButton, boolean b) {

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