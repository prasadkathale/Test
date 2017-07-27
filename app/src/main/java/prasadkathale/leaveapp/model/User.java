package prasadkathale.leaveapp.model;

/**
 * Created by PrasadKathale on 7/22/2017.
 */

public class User {

    private String DispName;
    private String UserEmail;
    private String UserPassword;
    private int UserPhonenumber;


    private String UserCompany;
    private String UserProjectName;
    private String UserManagerEmail;
    private int UserAnualLeave;

    public int getUserPhonenumber(){
        return UserPhonenumber;
    }

    public void setUserPhonenumber(int UserPhonenumber){
        this.UserPhonenumber = UserPhonenumber;
    }

    public String getDispName(){
        return DispName;
    }

    public void setDispName(String DispName ){
        this.DispName = DispName;
    }

    public String getUserEmail(){
        return UserEmail;
    }

    public void setUserEmail (String UserEmail){
        this.UserEmail = UserEmail;
    }

    public String getUserPassword(){
        return UserPassword;
    }

    public void setUserPassword(String UserPassword){
        this.UserPassword = UserPassword;
    }

    public String getUserCompany(){ return UserCompany;}

    public void setUserCompany (String UserCompany){this.UserCompany = UserCompany;}

    public String getUserProjectName(){ return UserProjectName;}

    public void setUserProjectName (String UserProjectName){this.UserProjectName = UserProjectName;}

    public String getUserManagerEmail(){ return UserManagerEmail;}

    public void setUserManagerEmail (String UserManagerEmail){this.UserManagerEmail = UserManagerEmail;}

    public int getUserAnualLeave(){ return UserAnualLeave;}

    public void setUserAnualLeave (int UserManagerEmail){this.UserAnualLeave = UserAnualLeave;}



}