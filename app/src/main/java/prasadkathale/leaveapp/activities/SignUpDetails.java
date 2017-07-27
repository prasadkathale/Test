package prasadkathale.leaveapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

import prasadkathale.leaveapp.R;
import prasadkathale.leaveapp.helper.InputValidation;
import prasadkathale.leaveapp.model.User;
import prasadkathale.leaveapp.sql.DatabaseHelper;

/**
 * Created by PrasadKathale on 7/22/2017.
 */

public class SignUpDetails extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = SignUpDetails.this;

    private NestedScrollView nestedScrollView;

    private TextInputLayout textInputLayoutPassword;
    private TextInputLayout textInputLayoutConfirmPassword;
    private TextInputLayout textInputLayoutCompanyName;
    private TextInputLayout textInputLayoutManagerEmailID;
    private TextInputLayout textInputLayoutNumberOfLeaves;
    private TextInputLayout textInputLayoutProjectName;

    private TextInputEditText textInputEditTextPassword;
    private TextInputEditText textInputEditTextConfirmPassword;
    private TextInputEditText textInputEditTextCompanyName;
    private TextInputEditText textInputEditTextProjectName;
    private TextInputEditText textInputEditTextNumberOfLeaves;
    private TextInputEditText textInputEditTextManagerEmailID;

    private AppCompatButton appCompatButtonCompleteSignup;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    private User user;

    private String userEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_details);
        /* getSupportActionBar().hide(); */

        initViews();
        initListeners();
        initObjects();

    }
    private void initViews(){
        nestedScrollView = (NestedScrollView) findViewById(R.id.nl_spd_nestedlayout);

        textInputLayoutPassword = (TextInputLayout) findViewById(R.id.il_spd_password);
        textInputLayoutConfirmPassword = (TextInputLayout) findViewById(R.id.il_spd_reenterpassword);
        textInputLayoutManagerEmailID = (TextInputLayout) findViewById(R.id.il_spd_manager_email);
        textInputLayoutNumberOfLeaves = (TextInputLayout) findViewById(R.id.il_spd_annual_leaves);
        textInputLayoutProjectName = (TextInputLayout) findViewById(R.id.il_spd_project_name);
        textInputLayoutCompanyName = (TextInputLayout) findViewById(R.id.il_spd_compnay_name);

        textInputEditTextPassword = (TextInputEditText) findViewById(R.id.et_spd_password);
        textInputEditTextConfirmPassword = (TextInputEditText) findViewById(R.id.et_spd_reenterpassword);
        textInputEditTextCompanyName = (TextInputEditText) findViewById(R.id.et_spd_compnay_name);
        textInputEditTextProjectName = (TextInputEditText) findViewById(R.id.et_spd_project_name);
        textInputEditTextManagerEmailID = (TextInputEditText) findViewById(R.id.et_spd_manager_email);
        textInputEditTextNumberOfLeaves = (TextInputEditText) findViewById(R.id.et_spd_annual_leaves);

        userEmail = getIntent().getExtras().getString("UserEmail");

        appCompatButtonCompleteSignup = (AppCompatButton) findViewById(R.id.btn_spd_CompleteRegistration);

    }
    private void initListeners(){
        appCompatButtonCompleteSignup.setOnClickListener((View.OnClickListener) this);

    }
    private void initObjects(){
        inputValidation = new InputValidation(activity);
        databaseHelper = new DatabaseHelper(activity);
        user = new User();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_spd_CompleteRegistration:
                completeRegisterationToSQLite();
                break;
        }


    }
    private void completeRegisterationToSQLite(){

        if (!inputValidation.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, getString(R.string.error_message_password))) {
            return;
        };
        if (!inputValidation.isInputEditTextFilled(textInputEditTextConfirmPassword, textInputLayoutConfirmPassword, getString(R.string.error_message_password))) {
            return;
        };
        if (!inputValidation.isInputEditTextFilled(textInputEditTextCompanyName, textInputLayoutCompanyName, getString(R.string.error_message_companyname))) {
            return;
        };
        if (!inputValidation.isInputEditTextFilled(textInputEditTextProjectName, textInputLayoutProjectName, getString(R.string.error_message_projectname))) {
            return;
        };
        if (!inputValidation.isInputEditTextFilled(textInputEditTextManagerEmailID, textInputLayoutManagerEmailID, getString(R.string.error_message_manager_email))) {
            return;
        };
        if (!inputValidation.isInputEditTextFilled(textInputEditTextNumberOfLeaves, textInputLayoutNumberOfLeaves, getString(R.string.error_message_annual_leave))) {
            return;
        };

        user.setUserEmail(userEmail);
        user.setUserPassword(textInputEditTextPassword.getText().toString().trim());
        user.setUserCompany(textInputEditTextCompanyName.getText().toString().trim());
        user.setUserProjectName(textInputEditTextProjectName.getText().toString().trim());
        user.setUserManagerEmail(textInputEditTextManagerEmailID.getText().toString().trim());
        user.setUserAnualLeave(textInputEditTextNumberOfLeaves.getInputType());

        databaseHelper.updateUserPassword(user);
        databaseHelper.updateCompanyDetails(user);

        Intent intent_HomePage = new Intent(getApplicationContext(),HomeActivity.class);
        startActivity(intent_HomePage);
    }
}
