package prasadkathale.leaveapp.activities;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

import prasadkathale.leaveapp.R;
import prasadkathale.leaveapp.helper.InputValidation;
import prasadkathale.leaveapp.model.User;
import prasadkathale.leaveapp.sql.DatabaseHelper;

/**
 * Created by PrasadKathale on 7/24/2017.
 */

public class ViewUserDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = ViewUserDetailsActivity.this;

    private TextInputLayout textInputLayoutEmail;
    private TextInputEditText textInputEditTextEmail;

    private AppCompatButton btn_viedetails;

    private String[] userdetails;
    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    private User user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewuserdetails);
      /*  getSupportActionBar().hide(); */

        initViews();
        initListners();
        initObjects();

    }
    public void initViews(){
        textInputLayoutEmail = (TextInputLayout)findViewById(R.id.il_vu_useremail);
        textInputEditTextEmail = (TextInputEditText)findViewById(R.id.et_vu_useremail);

        btn_viedetails = (AppCompatButton)findViewById(R.id.btn_vu_viewdetails);

    }
    public void initListners(){
        btn_viedetails.setOnClickListener(this);

    }
    public void initObjects(){
        inputValidation = new InputValidation(activity);
        databaseHelper = new DatabaseHelper(activity);
        user = new User();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_vu_viewdetails:
                viewUserData();
                break;
        }
    }

    public void viewUserData (){
        if (!inputValidation.isInputEditTextFilled(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        };
        user.setUserEmail(textInputEditTextEmail.getText().toString().trim());
        userdetails = databaseHelper.viewUserDetails(user);
        /* create list view*/

    }
}
