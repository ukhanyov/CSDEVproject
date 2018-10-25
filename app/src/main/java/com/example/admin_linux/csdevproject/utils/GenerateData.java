package com.example.admin_linux.csdevproject.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;

import com.example.admin_linux.csdevproject.R;
import com.example.admin_linux.csdevproject.data.ChatItem;
import com.example.admin_linux.csdevproject.data.CropStreamMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class GenerateData {

    public static List<CropStreamMessage> generateMessages(Context context) {
        List<CropStreamMessage> list = new ArrayList<>();
        for(int i = 0; i < 50; i++){
            list.add(new CropStreamMessage(
                    randomProfilePicture(context),
                    randomProfileName(),
                    randomProfileCorpName(),
                    randomMessageDestination(),
                    randomMessageText(),
                    randomMessageTime(),
                    randomMessagePicture(context)
            ));
        }
        return list;
    }

    public static List<ChatItem> generateChatItems(Context context){
        List<ChatItem> list = new ArrayList<>();
        for(int i = 0; i < 50; i++){
            list.add(new ChatItem(
                    randomChatItemProfilePicture(context),
                    randomChatItemProfileName(),
                    randomChatItemProfileText(),
                    randomChatItemProfileTime()
            ));
        }
        return list;
    }

    private static String randomProfilePicture(Context context) {
        Random r = new Random();
        int i = r.nextInt(6); // Bound is exclusive
        switch (i) {
            case 0:
                //Bitmap bitmap1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_dummy_user_1);
                return ImageHelper.compressToByteArray(getBitmapFromVectorDrawable(context, R.drawable.ic_dummy_user_1));

            case 1:
                //Bitmap bitmap2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_dummy_user_2);
                return ImageHelper.compressToByteArray(getBitmapFromVectorDrawable(context, R.drawable.ic_dummy_user_2));

            case 2:
                //Bitmap bitmap3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_dummy_user_3);
                return ImageHelper.compressToByteArray(getBitmapFromVectorDrawable(context, R.drawable.ic_dummy_user_3));

            case 3:
                //Bitmap bitmap4 = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_dummy_user_4);
                return ImageHelper.compressToByteArray(getBitmapFromVectorDrawable(context, R.drawable.ic_dummy_user_4));

            case 4:
                //Bitmap bitmap5 = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_dummy_user_5);
                return ImageHelper.compressToByteArray(getBitmapFromVectorDrawable(context, R.drawable.ic_dummy_user_5));


            default:
                //Bitmap bitmap6 = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_dummy_default);
                return ImageHelper.compressToByteArray(getBitmapFromVectorDrawable(context, R.drawable.ic_dummy_default));
        }
    }

    private static String randomProfileName() {
        Random r = new Random();
        int i = r.nextInt(6); // Bound is exclusive
        switch (i) {
            case 0:
                return "Name One";

            case 1:
                return "Name Two";

            case 2:
                return "Name Three";

            case 3:
                return "Name Four";

            case 4:
                return "Name Five";

            default:
                return "Name Default";
        }
    }

    private static String randomProfileCorpName() {
        Random r = new Random();
        int i = r.nextInt(6); // Bound is exclusive
        switch (i) {
            case 0:
                return "Corp One";

            case 1:
                return "Corp Two";

            case 2:
                return "Corp Three";

            case 3:
                return "Corp Four";

            case 4:
                return "Corp Five";

            default:
                return "Corp Default";
        }
    }

    private static String randomMessageDestination() {
        Random r = new Random();
        int i = r.nextInt(6); // Bound is exclusive
        switch (i) {
            case 0:
                return "you";

            case 1:
                return "Destination not you";

            case 2:
                return "you";

            case 3:
                return "Destination not you";

            case 4:
                return "you";

            default:
                return "Destination Default";
        }
    }

    private static String randomMessageText() {
        Random r = new Random();
        int i = r.nextInt(6); // Bound is exclusive
        switch (i) {
            case 0:
                return "Message Text.";

            case 1:
                return "Message Text. Message Text.";

            case 2:
                return "Message Text. Message Text. Message Text. Message Text.";

            case 3:
                return "Message Text. Message Text. Message Text. Message Text. Message Text. Message Text. Message Text. Message Text.";

            case 4:
                return "Message Text. Message Text. Message Text. Message Text. Message Text. Message Text. Message Text. Message Text. Message Text. Message Text. Message Text. Message Text. Message Text. Message Text. Message Text. Message Text.";

            default:
                return "Message Default";
        }
    }

    private static String randomMessageTime() {
        Random r = new Random();
        int i = r.nextInt(6); // Bound is exclusive
        switch (i) {
            case 0:
                return "1:01";

            case 1:
                return "2:02";

            case 2:
                return "3:03";

            case 3:
                return "4:04";

            case 4:
                return "5:05";

            default:
                return "00:00";
        }
    }

    private static String randomMessagePicture(Context context) {
        Random r = new Random();
        int i = r.nextInt(8); // Bound is exclusive
        switch (i) {
            case 0:
                //Bitmap bitmap1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_dummy_1);
                return ImageHelper.compressToByteArray(getBitmapFromVectorDrawable(context, R.drawable.ic_dummy_1));

            case 1:
                //Bitmap bitmap2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_dummy_2);
                return ImageHelper.compressToByteArray(getBitmapFromVectorDrawable(context, R.drawable.ic_dummy_2));

            case 2:
                //Bitmap bitmap3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_dummy_3);
                return ImageHelper.compressToByteArray(getBitmapFromVectorDrawable(context, R.drawable.ic_dummy_3));

            case 3:
                //Bitmap bitmap4 = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_dummy_4);
                return ImageHelper.compressToByteArray(getBitmapFromVectorDrawable(context, R.drawable.ic_dummy_4));

            case 4:
                //Bitmap bitmap5 = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_dummy_5);
                return ImageHelper.compressToByteArray(getBitmapFromVectorDrawable(context, R.drawable.ic_dummy_5));

            case 5:
                return null;

            case 6:
                return null;

            case 7:
                return null;

            default:
                //Bitmap bitmap6 = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_dummy_default);
                return ImageHelper.compressToByteArray(getBitmapFromVectorDrawable(context, R.drawable.ic_dummy_default));
        }
    }

    private static Bitmap getBitmapFromVectorDrawable(Context context, int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = (DrawableCompat.wrap(Objects.requireNonNull(drawable))).mutate();
        }

        Bitmap bitmap = Bitmap.createBitmap(Objects.requireNonNull(drawable).getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    private static String randomChatItemProfilePicture(Context context){
        Random r = new Random();
        int i = r.nextInt(6); // Bound is exclusive
        switch (i) {
            case 0:
                //Bitmap bitmap1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_dummy_user_1);
                return ImageHelper.compressToByteArray(getBitmapFromVectorDrawable(context, R.drawable.ic_dummy_user_1));

            case 1:
                //Bitmap bitmap2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_dummy_user_2);
                return ImageHelper.compressToByteArray(getBitmapFromVectorDrawable(context, R.drawable.ic_dummy_user_2));

            case 2:
                //Bitmap bitmap3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_dummy_user_3);
                return ImageHelper.compressToByteArray(getBitmapFromVectorDrawable(context, R.drawable.ic_dummy_user_3));

            case 3:
                //Bitmap bitmap4 = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_dummy_user_4);
                return ImageHelper.compressToByteArray(getBitmapFromVectorDrawable(context, R.drawable.ic_dummy_user_4));

            case 4:
                //Bitmap bitmap5 = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_dummy_user_5);
                return ImageHelper.compressToByteArray(getBitmapFromVectorDrawable(context, R.drawable.ic_dummy_user_5));


            default:
                //Bitmap bitmap6 = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_dummy_default);
                return ImageHelper.compressToByteArray(getBitmapFromVectorDrawable(context, R.drawable.ic_dummy_default));
        }
    }

    private static String randomChatItemProfileName(){
        Random r = new Random();
        int i = r.nextInt(6); // Bound is exclusive
        switch (i) {
            case 0:
                return "Name One";

            case 1:
                return "Name Two";

            case 2:
                return "Name Three";

            case 3:
                return "Name Four";

            case 4:
                return "Name Five";

            default:
                return "Name Default";
        }
    }

    private static String randomChatItemProfileText(){
        Random r = new Random();
        int i = r.nextInt(6); // Bound is exclusive
        switch (i) {
            case 0:
                return "Message Text.";

            case 1:
                return "Message Text. Message Text.";

            case 2:
                return "Message Text. Message Text. Message Text. Message Text.";

            case 3:
                return "Message Text. Message Text. Message Text. Message Text. Message Text. Message Text. Message Text. Message Text.";

            case 4:
                return "Message Text. Message Text. Message Text. Message Text. Message Text. Message Text. Message Text. Message Text. Message Text. Message Text. Message Text. Message Text. Message Text. Message Text. Message Text. Message Text.";

            default:
                return "Message Default";
        }
    }

    private static String randomChatItemProfileTime(){
        Random r = new Random();
        int i = r.nextInt(6); // Bound is exclusive
        switch (i) {
            case 0:
                return "01/01/11";

            case 1:
                return "02/02/12";

            case 2:
                return "03/03/13";

            case 3:
                return "04/04/14";

            case 4:
                return "05/05/15";

            default:
                return "10/25/18";
        }
    }
}
