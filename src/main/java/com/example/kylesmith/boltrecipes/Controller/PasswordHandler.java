package com.example.kylesmith.boltrecipes.Controller;


import android.util.Base64;
import android.util.Log;

import java.util.Random;

public class PasswordHandler {

    //TAG for debugging
    private static final String TAG = PasswordHandler.class.getSimpleName();

    /**/

    //Random Number Generator from java.util
    Random rand = new Random();

    //Byte Array for Password String encoded to Base64
    private byte[] EncodedPasswordBase64;

    /**/ /**/

    //Default Constructor
    public PasswordHandler(){}

    /**/

    //Encode the password to Base64
    public String EncodePasswordBase64(String inPassword){

        //Min of 10, Max of 60 encoding iterations as rand.nextInt() generates between 0 and 30
        //10 is added to prevent the result of 0
       int iAmountOfEncoding = rand.nextInt(30)+10;

       //Get the bytes of the Password
       EncodedPasswordBase64 = inPassword.getBytes();

       //Encode the bytes a random amount of times
        for (int i = 0; i < iAmountOfEncoding-1; i++) {

            EncodedPasswordBase64 = Base64.encode(EncodedPasswordBase64, 0);

        }

        //Last Iteration should be encoded to a String
        String EncodedPasswordString = Base64.encodeToString(EncodedPasswordBase64, 0);

        //Concat the random amount of Encoding iterations to the String for decoding the String
        EncodedPasswordString = EncodedPasswordString + String.valueOf(iAmountOfEncoding);

        //Return the EncodedString
        return EncodedPasswordString;

    }

    //Decode the String
    public String DecodePasswordBase64(String inEncodedPassword){

        //Get the integer values that have been concat to the String for decoding
        int iAmountOfEncoding = Integer.parseInt(inEncodedPassword.substring(inEncodedPassword.length()-2, inEncodedPassword.length()));

        //Get the String without the integer values
        String sEncodedStringWithoutAmountOfEncoding = inEncodedPassword.substring(0, inEncodedPassword.length()-2);

        //Get the vytes of the new Encoded String
        byte[] bEncodedPassword = sEncodedStringWithoutAmountOfEncoding.getBytes();

        //Decode the bEncodedPassword byte array to its simplest form
        for (int i = 0; i < iAmountOfEncoding; i++) {

            bEncodedPassword = Base64.decode(bEncodedPassword, 0);

        }

        //Store the String value of the byte array
        String decodedPasswordString = new String(bEncodedPassword);

        Log.i(TAG, decodedPasswordString);

        return decodedPasswordString;

    }
}
