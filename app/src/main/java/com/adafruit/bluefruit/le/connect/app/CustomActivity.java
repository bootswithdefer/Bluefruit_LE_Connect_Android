package com.adafruit.bluefruit.le.connect.app;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.adafruit.bluefruit.le.connect.R;
import com.adafruit.bluefruit.le.connect.app.neopixel.NeopixelActivity;
import com.adafruit.bluefruit.le.connect.app.neopixel.NeopixelBoard;

public class CustomActivity extends NeopixelActivity {
    // Log
    private final static String TAG = CustomActivity.class.getSimpleName();

    // Neopixel
    private boolean isSketchDetected = false;
    private NeopixelBoard mBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);

        mBoard = new NeopixelBoard("8x4", (byte) 8, (byte) 4, (byte) 3, (byte) 8, NeopixelBoard.kDefaultType);
    }

    public void onClickOne(View view) {
        if (!isSketchDetected)
            checkSketchVersion();

        byte[] data = {0x31};
        sendData(data);
    }

    public void onClickTwo(View view) {
        if (!isSketchDetected)
            checkSketchVersion();
        byte[] data = {0x32};
        sendData(data);
    }

    public void onClickThree(View view) {
        if (!isSketchDetected)
            checkSketchVersion();
        byte[] data = {0x33};
        sendData(data);
    }

    public void onClickFour(View view) {
        if (!isSketchDetected)
            checkSketchVersion();
        byte[] data = {0x34};
        sendData(data);
    }

    public void onClickFive(View view) {
        if (!isSketchDetected)
            checkSketchVersion();
        byte[] data = {0x35};
        sendData(data);
    }

    public void onClickSix(View view) {
        if (!isSketchDetected)
            checkSketchVersion();
        byte[] data = {0x36};
        sendData(data);
    }

    public void onClickSeven(View view) {
        if (!isSketchDetected)
            checkSketchVersion();
        byte[] data = {0x37};
        sendData(data);
    }

    public void onClickEight(View view) {
        if (!isSketchDetected)
            checkSketchVersion();
        byte[] data = {0x38};
        sendData(data);
    }

    public void onClickNine(View view) {
        if (!isSketchDetected)
            checkSketchVersion();
        byte[] data = {0x39};
        sendData(data);
    }

    public void onClickTen(View view) {
        if (!isSketchDetected)
            checkSketchVersion();
        byte[] data = {0x30};
        sendData(data);
    }

    protected void checkSketchVersion() {
        // Send version command and check if returns a valid response
        Log.d(TAG, "Command: get Version");

        byte command = 0x56;
        byte[] data = {command};
        sendData(data, new SendDataCompletionHandler() {
            @Override
            public void sendDataResponse(String data) {
                isSketchDetected = false;
                if (data != null) {
                    isSketchDetected = data.startsWith("Neopixel");
                }

                Log.d(TAG, "isNeopixelAvailable: " + (isSketchDetected ? "yes" : "no"));
                setupNeopixel(mBoard);
            }
        });
    }

    private void setupNeopixel(NeopixelBoard device) {
        Log.d(TAG, "Command: Setup");

        int pixelType = device.type;
        byte[] data = {(byte) 'S', device.width, device.height, device.components, device.stride, (byte) pixelType, (byte) ((byte) (pixelType >> 8) & 0xff)};
        sendData(data, new SendDataCompletionHandler() {
            @Override
            public void sendDataResponse(String data) {
                boolean success = false;
                if (data != null) {
                    success = data.startsWith("OK");
                }
                Log.d(TAG, "setup success: " + (success ? "yes" : "no"));
            }
        });
    }

    private void setPixelColor(int color, byte x, byte y) {
        Log.d(TAG, "Command: set Pixel");


        if (mBoard != null && mBoard.components == 3) {
            byte red = (byte) Color.red(color);
            byte green = (byte) Color.green(color);
            byte blue = (byte) Color.blue(color);

            byte[] data = {0x50, x, y, red, green, blue};
            sendData(data);
        }
    }
}
