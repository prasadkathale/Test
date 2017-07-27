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

public class DeleteUserActivity extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = DeleteUserActivity.this;

    private TextInputLayout textInputLayoutEmail;
    private TextInputEditText textInputEditTextEmail;

    private AppCompatButton appCompatButtonDeleteuser;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    private User user;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_user);
      /*  getSupportActionBar().hide();   */

        initViews();
        initListeners();
        initObjects();

    }
    public void initViews(){
        textInputLayoutEmail = (TextInputLayout) findViewById(R.id.il_du_useremail);
        textInputEditTextEmail = (TextInputEditText) findViewById(R.id.et_du_useremail);

        appCompatButtonDeleteuser = (AppCompatButton) findViewById(R.id.btn_du_deluser);

    }
    public void initListeners(){
        appCompatButtonDeleteuser.setOnClickListener((View.OnClickListener) this);

    }
    public void initObjects(){
        inputValidation = new InputValidation(activity);
        databaseHelper = new DatabaseHelper(activity);
        user = new User();
    }

    public void initlocalvar (){}


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_du_deluser:
                initlocalvar();
                DeleteUser();
                break;
        }
    }
    public void DeleteUser(){
        if (!inputValidation.isInputEditTextFilled(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        };
        if (databaseHelper.validateUser(textInputEditTextEmail.getText().toString().trim())) {
            databaseHelper.deleteUser(textInputEditTextEmail.getText().toString().trim());

            Toast.makeText(getApplicationContext(), getString(R.string.confirm_user_delete),Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getApplicationContext(),getString(R.string.error_message_not_Email), Toast.LENGTH_LONG).show();
        }


    }
}
