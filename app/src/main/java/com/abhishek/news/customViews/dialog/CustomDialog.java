package com.abhishek.news.customViews.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import com.abhishek.news.R;
import com.abhishek.news.customViews.textView.MediumText;
import com.abhishek.news.customViews.textView.SemiBoldText;
import com.abhishek.news.utils.FormDataUtils;

import java.util.Objects;

public class CustomDialog {
    // single btn
    public static void singleBtnDialog(Context context,
                                       String title,
                                       String message,
                                       SingleBtnDialogListener singleBtnDialogListener) {
        if (context != null) {
            final Dialog dialog = new Dialog(context);
            dialog.setCancelable(false);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflater.inflate(R.layout.single_button_dialog, null);
            // controls visibilty of title if title not needed just pass ""
            if (title.length() > 2)
                ((SemiBoldText) v.findViewById(R.id.tvHeadingText)).setText(title);
            else
                ((SemiBoldText) v.findViewById(R.id.tvHeadingText)).setVisibility(View.GONE);

            ((MediumText) v.findViewById(R.id.tvDescriptionText)).setText(message);

            ((SemiBoldText) v.findViewById(R.id.tvOk)).setOnClickListener(v1 -> {
                singleBtnDialogListener.onOkayPress();
                dialog.dismiss();
            });


            try {
                dialog.setContentView(v);
                dialog.show();
            } catch (Exception ignored) {
                //Do not show dialog
            }
        }
    }

    // two btn
    public static void showTwoBtnSimpleOkayDialog(Context context,
                                                  String title,
                                                  String message,
                                                  String postiveBtnText,
                                                  String negativeBtnText,
                                                  TwoBtnDialogListener twoBtnDialogListener) {
        if (context != null) {
            final Dialog dialog = new Dialog(context);
            dialog.setCancelable(false);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflater.inflate(R.layout.two_btn_dialog, null);
            // controls visibilty of title if title not needed just pass ""
            if (title.length() > 2)
                ((SemiBoldText) v.findViewById(R.id.tvHeadingText)).setText(title);
            else
                ((SemiBoldText) v.findViewById(R.id.tvHeadingText)).setVisibility(View.GONE);

            ((MediumText) v.findViewById(R.id.tvDescriptionText)).setText(message);

            if (postiveBtnText != null) {
                ((SemiBoldText) v.findViewById(R.id.positiveBtn)).setText(postiveBtnText);
            }
            if (negativeBtnText != null) {
                ((SemiBoldText) v.findViewById(R.id.negativeBtn)).setText(negativeBtnText);
            }

            // positive btn click
            ((SemiBoldText) v.findViewById(R.id.positiveBtn)).setOnClickListener(v1 -> {
                twoBtnDialogListener.onPositiveBtnPress();
                dialog.dismiss();
            });
            // negative btn click
            ((SemiBoldText) v.findViewById(R.id.negativeBtn)).setOnClickListener(v1 -> {
                twoBtnDialogListener.onNegativeBtnPress();
                dialog.dismiss();
            });

            try {
                dialog.setContentView(v);
                dialog.show();
            } catch (Exception ignored) {
                //Do not show dialog
            }
        }
    }
}
