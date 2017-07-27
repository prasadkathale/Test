package prasadkathale.leaveapp.activities;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.Toast;

import prasadkathale.leaveapp.R;
import prasadkathale.leaveapp.helper.InputValidation;
import prasadkathale.leaveapp.model.User;
import prasadkathale.leaveapp.sql.DatabaseHelper;

/**
 * Created by PrasadKathale on 7/24/2017.
 */

public class AddManagerActivity extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = AddManagerActivity.this;

    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutManagerEmail;

    private TextInputEditText textInputEditTextEmail;
    private TextInputEditText textInputEditTextManagerEmail;

    private AppCompatButton appCompatButtonAddManager;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    private User user;

    private String userEmail;
    private String [] userEmails;
    private int count;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_manager);
      /*  getSupportActionBar().hide();   */

        initViews();
        initListeners();
        initObjects();

    }
    public  void initViews(){
        textInputLayoutEmail = (TextInputLayout) findViewById(R.id.il_am_useremail);
        textInputLayoutManagerEmail = (TextInputLayout) findViewById(R.id.il_am_manageremail);

        textInputEditTextEmail = (TextInputEditText) findViewById(R.id.et_am_useremail);
        textInputEditTextManagerEmail = (TextInputEditText) findViewById(R.id.et_am_manageremail);

        appCompatButtonAddManager = (AppCompatButton)findViewById(R.id.btn_adp_add_manager);
    }
    public void initListeners(){
        appCompatButtonAddManager.setOnClickListener((View.OnClickListener) this);
    }
    public void initObjects (){
        inputValidation = new InputValidation(activity);
        databaseHelper = new DatabaseHelper(activity);
        user = new User();
    }
    public void initlocalvar(){
        userEmail = textInputEditTextEmail.getText().toString().trim();
        count = 0;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_adp_add_manager:
                initlocalvar();
                UpdateUserManager();
                break;
        }
    }

    public void UpdateUserManager (){

        if (!inputValidation.isInputEditTextFilled(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        };
        if (!inputValidation.isInputEditTextFilled(textInputEditTextManagerEmail, textInputLayoutManagerEmail, getString(R.string.error_message_manager_email))) {
            return;
        };
        userEmails = userEmail.split(";");
        if (userEmails.length > 0){
            do{
                userEmail = userEmails [count];
                if (databaseHelper.validateUser(userEmail)) {
                    user.setUserEmail(userEmail);
                    user.setUserManagerEmail(textInputEditTextManagerEmail.getText().toString().trim());

                    databaseHelper.updateManagerEmail(user);
                    Toast.makeText(getApplicationContext(), getString(R.string.user_manager_updated), Toast.LENGTH_LONG).show();
                } else{
                    Toast.makeText(getApplicationContext(), getString(R.string.error_valid_email_password), Toast.LENGTH_LONG).show();
                }
            }while(count < userEmails.length );
        }
    }
}
