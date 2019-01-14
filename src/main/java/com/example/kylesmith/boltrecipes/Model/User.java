package com.example.kylesmith.boltrecipes.Model;

public class User {

    //Vars
    private String sUsername;
    private String sPassword;

    public User(String inUsername, String inPassword){

        this.sUsername = inUsername;
        this.sPassword = inPassword;

    }

    public User(String inUsername){

        this.sUsername = inUsername;

    }

    /*
    @implementation BaseColumns
        * Interface
        * Which adds two variables
        * _ID
        * _Count
        * _ID is being used as the primary case in this case and will auto increment
        * _Count provides the amount of rows in a specific directory
        * This is used to avoid having to add the primary key to each table each time
        * instead it is done here once for this table without having to add it to the CREATE TABLE query
        * which is easier when rendering to the a list as the id is used to determine which item has
        * has been selected in a list.
     */

    public String getsUsername() {
        return sUsername;
    }

    public void setsUsername(String sUsername) {
        this.sUsername = sUsername;
    }

    public String getsPassword() {
        return sPassword;
    }

    public void setsPassword(String sPassword) {
        this.sPassword = sPassword;
    }
}
