package com.example.admin_linux.csdevproject.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.admin_linux.csdevproject.R;
import com.example.admin_linux.csdevproject.data.CorpStreamMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GenerateData {
    public static List<CorpStreamMessage> generateMessages(Context context) {
        List<CorpStreamMessage> list = new ArrayList<>();
        for(int i = 0; i < 50; i++){
            list.add(new CorpStreamMessage(
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

    private static String randomProfilePicture(Context context) {
        Random r = new Random();
        int i = r.nextInt(6); // Bound is exclusive
        switch (i) {
            case 0:
                Bitmap bitmap1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_dummy_user_1);
                return ImageHelper.compressToByteArray(bitmap1);

            case 1:
                Bitmap bitmap2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_dummy_user_2);
                return ImageHelper.compressToByteArray(bitmap2);

            case 2:
                Bitmap bitmap3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_dummy_user_3);
                return ImageHelper.compressToByteArray(bitmap3);

            case 3:
                Bitmap bitmap4 = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_dummy_user_4);
                return ImageHelper.compressToByteArray(bitmap4);

            case 4:
                Bitmap bitmap5 = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_dummy_user_5);
                return ImageHelper.compressToByteArray(bitmap5);


            default:
                Bitmap bitmap6 = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_dummy_default);
                return ImageHelper.compressToByteArray(bitmap6);
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
                Bitmap bitmap1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_dummy_1);
                return ImageHelper.compressToByteArray(bitmap1);

            case 1:
                Bitmap bitmap2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_dummy_2);
                return ImageHelper.compressToByteArray(bitmap2);

            case 2:
                Bitmap bitmap3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_dummy_3);
                return ImageHelper.compressToByteArray(bitmap3);

            case 3:
                Bitmap bitmap4 = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_dummy_4);
                return ImageHelper.compressToByteArray(bitmap4);

            case 4:
                Bitmap bitmap5 = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_dummy_5);
                return ImageHelper.compressToByteArray(bitmap5);

            case 5:
                return null;

            case 6:
                return null;

            case 7:
                return null;


            default:
                Bitmap bitmap6 = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_dummy_default);
                return ImageHelper.compressToByteArray(bitmap6);
        }
    }
}