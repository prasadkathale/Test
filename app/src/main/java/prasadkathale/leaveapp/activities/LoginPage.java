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
import android.widget.Toast;

import prasadkathale.leaveapp.R;
import prasadkathale.leaveapp.helper.InputValidation;
import prasadkathale.leaveapp.sql.DatabaseHelper;

/**
 * Created by PrasadKathale on 7/22/2017.
 */

public class LoginPage extends AppCompatActivity implements View.OnClickListener {


    private final AppCompatActivity activity = LoginPage.this;
    private NestedScrollView nestedScrollView;
    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutDisplayName;
    private TextInputLayout textInputLayoutPassword;
    private TextInputEditText textInputEditTextDisplayName;
    private TextInputEditText textInputEditTextEmail;
    private TextInputEditText textInputEditTextPassword;

    private String displayname;
    private String adminDisplayName;

    private AppCompatButton btn_sign_in;
    private AppCompatButton btn_sign_up;


    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
      /*  getSupportActionBar().hide(); */

        initViews();
        initListners();
        initObjects();

    }
    private void initViews(){
        nestedScrollView = (NestedScrollView) findViewById(R.id.nl_lp_nestedLoop);

        textInputEditTextEmail = (TextInputEditText) findViewById(R.id.et_lp_email);
        textInputEditTextDisplayName = (TextInputEditText) findViewById(R.id.et_lp_displayname);
        textInputEditTextPassword = (TextInputEditText) findViewById(R.id.et_lp_password);

        textInputLayoutEmail = (TextInputLayout) findViewById(R.id.il_lp_email);
        textInputLayoutDisplayName = (TextInputLayout) findViewById(R.id.il_lp_displayname);
        textInputLayoutPassword = (TextInputLayout) findViewById(R.id.il_lp_password);

        btn_sign_up = (AppCompatButton) findViewById(R.id.btn_lp_signup);
        btn_sign_in = (AppCompatButton) findViewById(R.id.btn_lp_signin);



    }
    public void initListners(){
        btn_sign_in.setOnClickListener(this);
        btn_sign_up.setOnClickListener(this);
    }
    private void initObjects(){
        databaseHelper = new DatabaseHelper(activity);
        inputValidation = new InputValidation(activity);
    }

    public void initlocalvariables (){
        displayname = textInputEditTextDisplayName.getText().toString().trim();
        adminDisplayName = "Admin";

    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_lp_signin:
                initlocalvariables();
                verifyFromSQLite();
                break;
            case R.id.btn_lp_signup:
                Intent intentSignUp = new Intent(getApplicationContext(), SignUp.class);
                startActivity(intentSignUp);
                break;
        }
    }
    private void verifyFromSQLite() {
        if (!inputValidation.isInputEditTextFilled(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))){
            return;
        }
        if (!inputValidation.isInputEditTextEmail(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_not_Email))){
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextDisplayName, textInputLayoutDisplayName, getString(R.string.error_message_name))){
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, getString(R.string.error_message_password))){
            return;
        }
        if (databaseHelper.validateUser(textInputEditTextEmail.getText().toString().trim())) {
            if (databaseHelper.validateUser(textInputEditTextEmail.getText().toString().trim(), textInputEditTextPassword.getText().toString().trim())) {

                if (!databaseHelper.getUserdetails(textInputEditTextEmail.getText().toString().trim())) {
                    Intent signupdetailsIntent = new Intent(getApplicationContext(), SignUpDetails.class);
                    signupdetailsIntent.putExtra("DisplayName", textInputEditTextDisplayName.getText().toString().trim());
                    signupdetailsIntent.putExtra("UserEmail", textInputEditTextEmail.getText().toString().trim());
                    emptyInputEditText();
                    startActivity(signupdetailsIntent);

                } else if (displayname.equals(adminDisplayName)) {
                    Intent adminIntent = new Intent(getApplicationContext(), AdminActivity.class);
                    emptyInputEditText();
                    startActivity(adminIntent);

                } else {
                    Intent signinIntent = new Intent(getApplicationContext(), HomeActivity.class);
                    signinIntent.putExtra("DisplayName", textInputEditTextDisplayName.getText().toString().trim());
                    signinIntent.putExtra("UserEmail", textInputEditTextEmail.getText().toString().trim());
                    emptyInputEditText();
                    startActivity(signinIntent);
                }

            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.error_message_invalid_ID_Password), Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(getApplicationContext(),getString(R.string.error_valid_email_password), Toast.LENGTH_LONG).show();
        }

    }
    private void emptyInputEditText(){
        textInputEditTextEmail.setText(null);
        textInputEditTextPassword.setText(null);
    }



}
