package com.abhishek.news.utils;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.provider.Settings;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.abhishek.news.MainApplication;
import com.abhishek.news.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;
import java.util.UUID;

import static com.abhishek.news.constants.AppConstantsKt.RANDOM_IMAGE_GENERATOR_API;
import static com.abhishek.news.constants.AppConstantsKt.RANDOM_QUERY;


public class ClassUtility {

    public static final String SUBJECT_NAME = "subjectName";
    public static final String SKILL_NAME = "skillName";
    public static final int REQUEST_NEW_SUBJECT = 11;
    private static Random random;
    private static int[] pastelColors;
    private static int[] darkColors;


    // method for hide keyboard
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    // Minimize app
    public static void minimizeApp(Context context) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    // show toast for short time
    public static void shortToast(String toastMessage) {
        if (toastMessage == null)
            return;
        Toast.makeText(MainApplication.getAppContext(), toastMessage, Toast.LENGTH_SHORT).show();
    }

    // show toast for short time
    public static void longToast(String toastMessage) {
        if (toastMessage == null)
            return;
        Toast.makeText(MainApplication.getAppContext(), toastMessage, Toast.LENGTH_LONG).show();
    }

    // return image picker intent
    public static Intent getImagePickerIntent() {
        return new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    }

    // show date picker and set date to view
    public static void showDatePicker(Context context, EditText editText) {
        final Calendar c = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, (view, year, monthOfYear, dayOfMonth) -> {
            editText.setText(String.format("%d/%d/%d", monthOfYear + 1, dayOfMonth, year));
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        // show
        datePickerDialog.show();
    }

    public static boolean isStringValid(String str) {
        if (str != null) {
            str = str.trim();
            return !str.isEmpty()
                    && !str.equalsIgnoreCase("null")
                    && !str.equalsIgnoreCase("na")
                    && !str.equalsIgnoreCase("None");
        }
        return false;
    }

    public static void appendStringIfValid(StringBuilder builderObj, String data, String dataDecorater) {
        if (isStringValid(data)) {
            builderObj.append(data);
            if (dataDecorater != null)
                builderObj.append(dataDecorater);
        }
    }

    public static String stringBuilderUsingDecorator(String decorator, String... data) {
        StringBuilder builder = new StringBuilder();

        for (String item : data)
            appendStringIfValid(builder, item, decorator);

        return removeLastDataDecorater(builder.toString(), decorator);
    }

    public static String stringBuilderUsingDecorator(String decorator, List<String> data) {
        StringBuilder builder = new StringBuilder();

        for (String item : data)
            appendStringIfValid(builder, item, decorator);

        return removeLastDataDecorater(builder.toString(), decorator);
    }

    /**
     * helper method to remove the last decorater from string
     *
     * @param data            = string you want to remove decorater from
     * @param decoraterString = decorater string
     * @return final string
     */
    public static String removeLastDataDecorater(String data, String decoraterString) {
        if (isStringValid(decoraterString) && isStringValid(data)) {
            int dataSize = data.length();
            int decoraterSize = decoraterString.length();

            if (dataSize > decoraterSize) {
                int startPointOfDataSearch = dataSize - decoraterSize;

                if (data.regionMatches(startPointOfDataSearch, decoraterString, 0, decoraterSize))
                    return data.substring(0, startPointOfDataSearch);
            }
        }
        return data;
    }

    // load image from url into image view
    public static void loadImageFromUrl(String url, ImageView imageView, Context context) {

        if (!isStringValid(url)) {
            imageView.setImageDrawable(new ColorDrawable(getRandomPastelColor(context)));
            return;
        }

        Glide.with(context)
                .setDefaultRequestOptions(new RequestOptions().timeout(30000))
                .load(url)
                .placeholder(new ColorDrawable(getRandomPastelColor(context)))
               // .error(context.getResources().getDrawable(R.drawable.ic_error_grey_24dp))
                .into(imageView);
    }

    public static Intent chooseFile() {
        // choose a file via the system's file
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        // Filter to only show results that can be "opened"
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        // To show all document types it would be "*/*"
        // To show only text files it would be "text/plain"
        // To show only image files it would be "image/*"
        intent.setType("*/*");
        return intent;
    }

    public static Intent chooseImageFile() {
        // choose a file via the system's file
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        // Filter to only show results that can be "opened"
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        // To show all document types it would be "*/*"
        // To show only text files it would be "text/plain"
        // To show only image files it would be "image/*"
        intent.setType("image/*");
        return intent;
    }

    public static int getValueInDP(Context context, int value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, context.getResources().getDisplayMetrics());
    }

    public static float getValueInDP(Context context, float value) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, context.getResources().getDisplayMetrics());
    }

    public static int getValueInPixel(Context context, int value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, value, context.getResources().getDisplayMetrics());
    }

    public static float getValueInPixel(Context context, float value) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, value, context.getResources().getDisplayMetrics());
    }

    public static int getRandomNumber(int limit) {
        if (random == null) random = new Random();
        return random.nextInt(limit);
    }

    public static String getRandomImageUrl() {
        return RANDOM_IMAGE_GENERATOR_API + RANDOM_QUERY + getRandomNumber(100);
    }

    public static int getRandomPastelColor(Context context) {
        if (pastelColors == null) {
            pastelColors = context.getResources().getIntArray(R.array.pastel_colors);
        }
        return pastelColors[getRandomNumber(pastelColors.length)];
    }

    public static int getRandomDarkColor(Context context) {
        if (darkColors == null) {
            darkColors = context.getResources().getIntArray(R.array.dark_colors);
        }
        return darkColors[getRandomNumber(darkColors.length)];
    }


    public static String getDateFromUnixtime(long time) {
        Date date = new Date(time * 1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT-4"));
        String formattedDate = sdf.format(date);
        return formattedDate;
    }

    public static boolean allAreSame(String compareWith, String... items) {
        for (String i : items) {
            if (!i.equals(compareWith)) {
                return false;
            }
        }
        return true;
    }


    public static String getDateCurrentTimeZone(long timestamp) {
        try {
            Calendar calendar = Calendar.getInstance();
            TimeZone tz = TimeZone.getDefault();
            calendar.setTimeInMillis(timestamp * 1000);
            calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Date currenTimeZone = (Date) calendar.getTime();
            return sdf.format(currenTimeZone);
        } catch (Exception e) {
        }
        return "";
    }

    public static String changeDateToLocal(long unix) {
        Calendar cal = Calendar.getInstance();
        TimeZone tz = cal.getTimeZone();

        //Log.d("BottomSheetTimeItem zone: ", tz.getDisplayName());
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy");
        sdf.setTimeZone(tz);
        return sdf.format(new Date(unix * 1000));
    }


    public static String changeTimeToLocal(long unix) {
        Calendar cal = Calendar.getInstance();
        TimeZone tz = cal.getTimeZone();

        //Log.d("BottomSheetTimeItem zone: ", tz.getDisplayName());
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        sdf.setTimeZone(tz);

        return sdf.format(new Date(unix * 1000));
    }

    public static String getLocalTimeWithCurrentTimeZone() {
        try {
            Calendar calendar = Calendar.getInstance();
            TimeZone tz = calendar.getTimeZone();

            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.getDefault());
            sdf.setTimeZone(tz);
            return sdf.format(calendar.getTime());
        } catch (Exception e) {
        }
        return "";
    }

    public static String getTimeCurrentTimeZone(long timestamp) {
        try {
            Calendar calendar = Calendar.getInstance();
            TimeZone tz = TimeZone.getDefault();
            calendar.setTimeInMillis(timestamp * 1000);
            calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
            // set it for UK users
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
            Date currenTimeZone = (Date) calendar.getTime();
            return sdf.format(currenTimeZone);
        } catch (Exception e) {
        }
        return "";
    }

    public static void showLoggerToast(Context context, String message) {
        try {
            Toast t = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            ((TextView) ((LinearLayout) t.getView()).getChildAt(0))
                    .setGravity(Gravity.CENTER_HORIZONTAL);
            t.show();
        } catch (Exception e) {
            //Log.e(TAG, e.toString());
        }
    }

    // to set layout_gravity
    public static void setLayoutGravity(int gravity, View... view) {
        for (View item : view)
            ((LinearLayout.LayoutParams) item.getLayoutParams()).gravity = gravity;
    }


    //custom Pogress bar
    public static Dialog getCustomProgressBar(Context context) {
        Dialog dialog = new Dialog(context);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.getWindow().setDimAmount(0.1f);
        }
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.custom_progress_bar, null, false);
        dialog.setCancelable(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(v);
        return dialog;
    }

    public static LinearLayout getDynamicProgressBar(Context context) {
        LinearLayout linearLayout = new LinearLayout(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        linearLayout.setLayoutParams(layoutParams);

        ProgressBar progressBar = new ProgressBar(context);
        int h = getValueInDP(context, 30);
        int w = ViewGroup.LayoutParams.MATCH_PARENT;
        LinearLayout.LayoutParams progressParams = new LinearLayout.LayoutParams(w, h);
        progressParams.gravity = Gravity.CENTER;
        progressBar.setLayoutParams(progressParams);
        linearLayout.addView(progressBar);

        return linearLayout;
    }

    public static String getSuperUniqueString(Context context) {
        String output = "";
        output = output + UUID.randomUUID().toString();
        String deviceId = getAndroidDeviceId(context);
        if (deviceId.equals(""))
            deviceId = deviceId + UUID.randomUUID().toString();
        output = output + deviceId;
        return output;
    }

    public static String getAndroidDeviceId(Context context) {
        String deviceId = "";
        if (context == null)
            context = MainApplication.getAppContext();
        try {
            deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        } catch (Exception e) {
        }
        return deviceId;
    }


    public static Intent getCustomFileChooserIntent(String... types) {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        // Filter to only show results that can be "opened"
        intent.addCategory(Intent.CATEGORY_OPENABLE);
//        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_MIME_TYPES, types);
        return intent;
    }


    public static <T> Intent createIntentUsingClassName(Context context, String className, Class<T> defaultClass) {
        if (className == null || className.isEmpty()) {
            return new Intent(context, defaultClass);
        }
        try {
            String activityname = className;
            Class class_name = Class.forName(activityname);
            return new Intent(context, class_name);
        } catch (Exception e) {
            return new Intent(context, defaultClass);
        }
    }


}
