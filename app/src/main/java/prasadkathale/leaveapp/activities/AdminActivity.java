package prasadkathale.leaveapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.MenuItem;
import android.view.View;

import prasadkathale.leaveapp.R;

/**
 * Created by PrasadKathale on 7/22/2017.
 */

public class AdminActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener{

    private AppCompatButton btn_viewEmpDetails;
    private AppCompatButton btn_addManager;
    private AppCompatButton btn_delEmp;
    private AppCompatButton btn_tbd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        initViews();
        initListners();
        initlocalvariables();


    }
    public void initViews(){
        btn_viewEmpDetails = (AppCompatButton) findViewById(R.id.btn_adp_view_empdetails);
        btn_addManager = (AppCompatButton) findViewById(R.id.btn_adp_add_manager);
        btn_delEmp = (AppCompatButton) findViewById(R.id.btn_adp_del_emp);
        btn_tbd = (AppCompatButton) findViewById(R.id.btn_adp_tbd);
    }
    public void initListners(){
        btn_viewEmpDetails.setOnClickListener(this);
        btn_addManager.setOnClickListener(this);
        btn_delEmp.setOnClickListener(this);
        btn_tbd.setOnClickListener(this);
    }

    public void initlocalvariables (){

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_adp_view_empdetails:
                Intent intentViewuserdetails = new Intent(getApplicationContext(), ViewUserDetailsActivity.class);
                startActivity(intentViewuserdetails);
                break;
            case R.id.btn_adp_add_manager:
                Intent intentAddManger = new Intent(getApplicationContext(), AddManagerActivity.class);
                startActivity(intentAddManger);
                break;
            case R.id.btn_adp_del_emp:
                Intent intentDeleteUser = new Intent(getApplicationContext(), DeleteUserActivity.class);
                startActivity(intentDeleteUser);
                break;
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}
