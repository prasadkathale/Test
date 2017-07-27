package prasadkathale.leaveapp.activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import prasadkathale.leaveapp.R;
import prasadkathale.leaveapp.sql.DatabaseHelper;

/**
 * Created by PrasadKathale on 7/25/2017.
 */

public class ApplyLeaveActivity extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = ApplyLeaveActivity.this;
    Calendar calendar = Calendar.getInstance();
    private TextInputLayout textInputLayoutStartDate;
    private TextInputEditText textInputEditTextStartDate;

    private TextInputLayout textInputLayoutEndDate;
    private TextInputEditText textInputEditTextEndDate;


    private AppCompatButton setStartDate;
    private AppCompatButton setEndDate;
    private AppCompatButton confirmLeave;
    private DatabaseHelper databaseHelper;

    private String subject;
    private String to;
    private String message;
    private String startDate;
    private String endDate;

    int cday, cmonth, cyear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_leave);

        initViews();
        initListners();
        initLocalVar();

    }
    private void initViews() {

        setStartDate = (AppCompatButton) findViewById(R.id.btn_al_startdate);
        setEndDate = (AppCompatButton) findViewById(R.id.btn_al_endtdate);
        confirmLeave = (AppCompatButton) findViewById(R.id.btn_al_confirmleave);

    }
    private void initListners() {
        setStartDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                new DatePickerDialog(ApplyLeaveActivity.this, listenerstartdate,
                        calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        setEndDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                new DatePickerDialog(ApplyLeaveActivity.this, listenerenddate,
                        calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

    }
    private void initLocalVar(){

        subject = "Leave Application for" + getIntent().getExtras().getString("Display Name");
        to = databaseHelper.returnManager(getIntent().getExtras().getString("Display Name"));
        message = "TO Manager," +
                " I would like to apply for leave from" + startDate + "Till " + endDate +". Can you Please Approve.";
    }

    DatePickerDialog.OnDateSetListener listenerstartdate = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            startDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
            textInputEditTextStartDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
        }
    };
    DatePickerDialog.OnDateSetListener listenerenddate = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            endDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
            textInputEditTextEndDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
        }
    };

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_lp_signin:
                initLocalVar();
                sendRequest();
                break;
        }
    }

    private void sendRequest (){

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL,new String [] {to});
        intent.putExtra(Intent.EXTRA_SUBJECT,new String [] {subject});
        intent.putExtra(Intent.EXTRA_TEXT,new String [] {message});
        intent.setType("message/rfc822");

        startActivity(Intent.createChooser(intent, "Select Email App"));

    }


}
