package com.abhishek.news.utils;

import android.text.TextUtils;
import android.widget.EditText;

public class FormDataUtils {

    public static boolean validateEmail(EditText emailView) {
        String email = emailView.getText().toString().trim();

        if(!validateInputBox(emailView)){
            return false;
        }
        if (!isValidEmail(email)) {
            emailView.setError("Email is not valid.");
            emailView.requestFocus();
            return false;
        }
        return true;
    }


    public static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean validateInputBox(EditText editText) {
        // check the length of the enter data in EditText and give error if its empty
        if (editText.getText().toString().trim().length() > 0) {
            return true; // returns true if field is not empty
        }
        editText.setError("Please Fill This");
        editText.requestFocus();
        return false;
    }

    public static boolean validateBothPasswordMatches(EditText passwordEditText, EditText confirmPasswordEditText) {
        // check the length of the enter data in EditText and give error if its empty

        if(validateInputBox(passwordEditText) && validateInputBox(confirmPasswordEditText)){

            if(TextUtils.equals(passwordEditText.getText(),confirmPasswordEditText.getText())){
                return true;
            }else{
                confirmPasswordEditText.setError("Password Do not match");
                confirmPasswordEditText.requestFocus();
            }
        }
        return false;
    }

}
