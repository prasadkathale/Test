package prasadkathale.leaveapp.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.LoginFilter;

import prasadkathale.leaveapp.model.User;

/**
 * Created by PrasadKathale on 7/22/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "LoginInfo.db";
    private static final String TABLE_USER = "USER_LOGIN_DETAIL_DB";
    private static final String COLUMN_USER_ID = "USER_ID";
    private static final String COLUMN_USER_DISPLAYNAME = "USER_DISPLAYNAME";
    private static final String COLUMN_USER_EMAIl = "USER_EMAIL";
    private static final String COLUMN_USER_PASSWORD = "USER_PASSWORD";
    private static final String COLUMN_USER_PHONENUMBER = "USER_PHONENUMBER";


    private static final String TABLE_USER_COMPANY_DETAILS = "USER_COMPANY_DETAIL_DB";
    private static final String COLUMN_USER_COMPANY_NAME = "USER_COMPANYNAME";
    private static final String COLUMN_USER_PROJECT_NAME = "USER_PROJECTNAME";
    private static final String COLUMN_USER_MANAGER_EMAIL = "USER_MANAGEREMAIL";
    private static final String  COLUMN_USER_ANNUAL_LEAVE = "USER_ANNUAL_LEAVE";

    private static final String TABLE_USER_LEAVE_DETAILS = "USER_LEAVE_DETAIL_DB";
    private static final String COLUMN_USER_LEAVE_START_DATE = "USER_LEAVE_START_DATE";
    private static final String COLUMN_USER_LEAVE_END_DATE = "USER_LEAVE_END_DATE";
    private static final String COLUMN_USER_LEAVE_STATUS = "USER_LEAVE_STATUS";
    private static final String COLUMN_USER_LEAVE_LEFT = "USER_LEAVE_LEFT";


    private String userPassword;
    private String newPassword;
    private String newProject;
    private String newManagerEmail;
    private String userEmail;
    private String companyName;
    private String projectName;
    private String managerEmailId;
    private int numberofLeaves;
    private String[] userdetails;
    Cursor cursor;


    private String CREATE_LOGIN_INFO_TABLE = "CREATE TABLE " + TABLE_USER +"("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_DISPLAYNAME + " TEXT,"
            + COLUMN_USER_EMAIl + " TEXT," + COLUMN_USER_PASSWORD + " TEXT, " + COLUMN_USER_PHONENUMBER + " INTEGER" + ")" ;

    private String CREATE_USER_COMPANY_DETAILS = "CREATE TABLE " + TABLE_USER_COMPANY_DETAILS +"(" + COLUMN_USER_EMAIl + " TEXT,"
            + COLUMN_USER_COMPANY_NAME + " TEXT," + COLUMN_USER_PROJECT_NAME + " TEXT,"
            + COLUMN_USER_MANAGER_EMAIL + " TEXT," + COLUMN_USER_ANNUAL_LEAVE + " TEXT "  + ")" ;

    private String CREATE_USER_LEAVE_DETAILS = "CREATE TABLE " + TABLE_USER_LEAVE_DETAILS + "(" + COLUMN_USER_EMAIl + " TEXT,"
            + COLUMN_USER_LEAVE_START_DATE + " TEXT," + COLUMN_USER_LEAVE_END_DATE + " TEXT,"
            + COLUMN_USER_LEAVE_STATUS + " TEXT," + COLUMN_USER_LEAVE_LEFT + " INT " + ")" ;



    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_LOGIN_INFO_TABLE);
        db.execSQL(CREATE_USER_COMPANY_DETAILS);
        db.execSQL(CREATE_USER_LEAVE_DETAILS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion ){
        db.execSQL(DROP_USER_TABLE);
        onCreate(db);
    }

    public void registerUser (User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_DISPLAYNAME, user.getDispName());
        values.put(COLUMN_USER_EMAIl, user.getUserEmail());
        values.put(COLUMN_USER_PASSWORD, user.getUserPassword());
        values.put(COLUMN_USER_PHONENUMBER, user.getUserPhonenumber());

        db.insert(TABLE_USER, null, values);
        db.close();

    }

    public boolean updateManagerEmail (User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        newManagerEmail = user.getUserManagerEmail().toString().trim();
        userEmail =  user.getUserEmail().toString().trim();

        db.execSQL("UPDATE USER_COMPANY_DETAIL_DB SET USER_MANAGEREMAIL = '" + newManagerEmail + "'WHERE USER_EMAIL =  '" + userEmail +"'");
        db.close();

        return true;

    }

    public String returnManager (String email){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;

        cursor = db.rawQuery("SELECT USER_MANAGEREMAIL FROM USER_COMPANY_DETAIL_DB WHERE USER_EMAIL =  '" + userEmail +"'",null);
        managerEmailId = cursor.getString(1);

        db.close();

        return managerEmailId;
    }

    public boolean updateUserPassword (User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        newPassword = user.getUserPassword().toString().trim();
        userEmail =  user.getUserEmail().toString().trim();

        db.execSQL("UPDATE USER_LOGIN_DETAIL_DB SET USER_PASSWORD = '" + newPassword + "'WHERE USER_EMAIL =  '" + userEmail +"'");
        db.close();

        return true;

    }

    public boolean updateUserProject (User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        newProject = user.getUserProjectName().toString().trim();
        userEmail =  user.getUserEmail().toString().trim();;

        db.execSQL("UPDATE USER_DB SET USER_PROJECTNAME = '" + newProject + "'WHERE USER_EMAIL =  '" + userEmail +"'");
        db.close();

        return true;

    }

    public boolean validateUser (String email){
        String [] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COLUMN_USER_EMAIl + " = ? ";
        String[] selectionArgs = { email };

        Cursor cursor = db.query(TABLE_USER,columns,selection,selectionArgs,null, null, null);

        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0){
            return true;
        }
        return false;

    }
    public boolean validateUser (String email, String password){
        String [] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COLUMN_USER_EMAIl + " = ? " +" AND "+ COLUMN_USER_PASSWORD + " = ? ";
        String [] selectionArgs = {email, password};

        Cursor cursor = db.query(TABLE_USER, columns, selection, selectionArgs, null, null,null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0){
            return true;
        }
        return false;

    }

    public boolean updateCompanyDetails (User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_USER_COMPANY_NAME, user.getUserCompany());
        values.put(COLUMN_USER_PROJECT_NAME, user.getUserProjectName());
        values.put(COLUMN_USER_MANAGER_EMAIL, user.getUserManagerEmail());
        values.put(COLUMN_USER_ANNUAL_LEAVE, user.getUserAnualLeave());
        values.put(COLUMN_USER_EMAIl, user.getUserEmail());

        db.insert(TABLE_USER_COMPANY_DETAILS, null, values);
        db.close();

        return true;

    }

    public boolean getUserdetails (String email){

        String [] columns = {
                COLUMN_USER_EMAIl
        };
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COLUMN_USER_EMAIl + " = ? ";
        String[] selectionArgs = { email };

        Cursor cursor = db.query(TABLE_USER_COMPANY_DETAILS,columns,selection,selectionArgs,null, null, null);

        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0){
            return true;
        }
        return false;


    }

    public String[] viewUserDetails(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        userEmail =  user.getUserEmail().toString().trim();
        cursor = db.rawQuery("SELECT *  FROM USER_COMPANY_DETAIL_DB WHERE USER_EMAIL =  '" + userEmail +"'", null);
        if(cursor.moveToFirst()){
            userdetails [1] = cursor.getString(1);
            userdetails [2] = cursor.getString(2);
            userdetails [3] = cursor.getString(3);
            userdetails [4] = cursor.getString(4);
            userdetails [5] = cursor.getString(5);

        }
        cursor = db.rawQuery("SELECT *  FROM USER_LOGIN_DETAIL_DB WHERE USER_EMAIL =  '" + userEmail +"'", null);
        if(cursor.moveToFirst()){
            userdetails [6] = cursor.getString(1);
            userdetails [7] = cursor.getString(2);
            userdetails [8] = cursor.getString(3);
            userdetails [9] = cursor.getString(4);
        }

        return userdetails;
    }

    public boolean deleteUser (String email) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM USER_LOGIN_DETAIL_DB WHERE USER_EMAIL =  '" + email +"'");
        db.execSQL("DELETE FROM USER_COMPANY_DETAIL_DB WHERE USER_EMAIL =  '" + email +"'");
        db.close();

        return true;

    }

}