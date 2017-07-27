package prasadkathale.leaveapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.LinearLayout;

import prasadkathale.leaveapp.R;
import prasadkathale.leaveapp.helper.InputValidation;
import prasadkathale.leaveapp.model.User;
import prasadkathale.leaveapp.sql.DatabaseHelper;

/**
 * Created by PrasadKathale on 7/22/2017.
 */

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = SignUp.this;

    private NestedScrollView nestedScrollView;
    private TextInputLayout textInputLayoutDisplayName;
    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPhonenumber;

    private TextInputEditText textInputEditTextDisplayName;
    private TextInputEditText textInputEditTextEmail;
    private TextInputEditText textInputEditTextPhonenumber;

    private static String userPassword;

    private AppCompatButton appCompatButtonRegister;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
      /*  getSupportActionBar().hide();   */

        initViews();
        initListeners();
        initObjects();

    }
    private void initViews() {

        nestedScrollView = (NestedScrollView) findViewById(R.id.nl_sp_nestedlayout);

        textInputLayoutDisplayName = (TextInputLayout) findViewById(R.id.il_sp_displayname);
        textInputLayoutEmail = (TextInputLayout) findViewById(R.id.il_sp_emial_id);
        textInputLayoutPhonenumber = (TextInputLayout) findViewById(R.id.il_sp_phone_number);

        textInputEditTextEmail = (TextInputEditText) findViewById(R.id.et_sp_emial_id);
        textInputEditTextDisplayName = (TextInputEditText) findViewById(R.id.et_sp_displayname);
        textInputEditTextPhonenumber = (TextInputEditText) findViewById(R.id.et_sp_phone_number);

        appCompatButtonRegister = (AppCompatButton) findViewById(R.id.btn_sp_Register);

        userPassword  = "Welcome$123";

    }
    private void initListeners() {
        appCompatButtonRegister.setOnClickListener((View.OnClickListener) this);
    }
    private void initObjects(){
        inputValidation = new InputValidation(activity);
        databaseHelper = new DatabaseHelper(activity);
        user = new User();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sp_Register:
                createUserInSQLite();
                break;
        }
    }

    private void createUserInSQLite (){
        if (!inputValidation.isInputEditTextFilled(textInputEditTextDisplayName, textInputLayoutDisplayName, getString(R.string.error_message_name))) {
            return;
        };
        if (!inputValidation.isInputEditTextFilled(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        };
        if (!inputValidation.isInputEditTextFilled(textInputEditTextPhonenumber, textInputLayoutPhonenumber, getString(R.string.error_message_phonenumber))) {
            return;
        };
        if (!databaseHelper.validateUser(textInputEditTextEmail.getText().toString().trim())) {


            user.setDispName(textInputEditTextDisplayName.getText().toString().trim());
            user.setUserEmail(textInputEditTextEmail.getText().toString().trim());
            user.setUserPhonenumber(textInputEditTextPhonenumber.getInputType());
            user.setUserPassword(userPassword.toString().trim());


            databaseHelper.registerUser(user);

            Intent intent_Login_Page = new Intent(getApplicationContext(),LoginPage.class);
            startActivity(intent_Login_Page);


        }else{
            Snackbar.make(nestedScrollView, getString(R.string.error_email_exists), Snackbar.LENGTH_LONG).show();
        }
    }


}
