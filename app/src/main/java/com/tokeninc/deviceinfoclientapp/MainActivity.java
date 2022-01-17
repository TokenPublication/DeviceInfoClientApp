package com.tokeninc.deviceinfoclientapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.tokeninc.deviceinfo.DeviceInfo;

public class MainActivity extends AppCompatActivity {

    TextView fiscalIdText, modemVersionText, secureBoardVersionText, imeiNumberText, imsiNumberText, cardRedirectionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        secureBoardVersionText = findViewById(R.id.secureBoardVersion);
        fiscalIdText = findViewById(R.id.fiscalID);
        modemVersionText = findViewById(R.id.modemVersion);
        imeiNumberText = findViewById(R.id.imeiNumber);
        imsiNumberText = findViewById(R.id.imsiNumber);
        cardRedirectionText = findViewById(R.id.cardRedirect);

        DeviceInfo deviceInfo = new DeviceInfo(this);

        // get FiscalId
        deviceInfo.getFiscalId(new DeviceInfo.DeviceInfoResponseHandler() {
            @Override
            public void onSuccess(String result) {
                fiscalIdText.setText(result);
            }

            @Override
            public void onFail(String errMessage) {
                fiscalIdText.setText(errMessage);
            }
        });

        // get IMEI number
        deviceInfo.getImeiNumber(new DeviceInfo.DeviceInfoResponseHandler() {
            @Override
            public void onSuccess(String result) {
                imeiNumberText.setText(result);
            }

            @Override
            public void onFail(String errMessage) {
                imeiNumberText.setText(errMessage);
            }
        });

        // get IMSI number
        deviceInfo.getImsiNumber(new DeviceInfo.DeviceInfoResponseHandler() {
            @Override
            public void onSuccess(String result) {
                imsiNumberText.setText(result);
            }

            @Override
            public void onFail(String errMessage) {
                imsiNumberText.setText(errMessage);
            }
        });

        // get Modem version
        deviceInfo.getModemVersion(new DeviceInfo.DeviceInfoResponseHandler() {
            @Override
            public void onSuccess(String result) {
                modemVersionText.setText(result);
            }

            @Override
            public void onFail(String errMessage) {
                modemVersionText.setText(errMessage);
            }
        });

        // get SecureBoardVersion version
        deviceInfo.getSecureBoardVersion(new DeviceInfo.DeviceInfoResponseHandler() {
            @Override
            public void onSuccess(String result) {
                secureBoardVersionText.setText(result);
            }

            @Override
            public void onFail(String errMessage) {
                secureBoardVersionText.setText(errMessage);
            }
        });

        // get CardRedirection version
        deviceInfo.getCardRedirection(new DeviceInfo.DeviceInfoResponseHandler() {
            @Override
            public void onSuccess(String result) {
                DeviceInfo.CardRedirect cardRedirect = DeviceInfo.CardRedirect.valueOf(result);

                switch (cardRedirect)
                {
                    case YES:
                        // your code here
                        break;
                    case NO:
                        // your code here
                        break;
                    case NOT_ASSIGNED:
                        // your code here
                        break;
                }

                cardRedirectionText.setText(DeviceInfo.CardRedirect.valueOf(result).toString());
            }

            @Override
            public void onFail(String errMessage) {
                cardRedirectionText.setText(errMessage);
            }
        });


        // to get multiple fields in a single callback, use getFields method
        deviceInfo.getFields(

                fields -> {
                    if (fields == null) return;
                    // fields is the string array that contains info in the requested order
                    Log.i("Example 2", "Fiscal ID:   " + fields[0]);
                    Log.i("Example 2", "IMEI Number: " + fields[1]);
                    Log.i("Example 2", "IMSI Number: " + fields[2]);
                },

                // write requested fields
                DeviceInfo.Field.FISCAL_ID, DeviceInfo.Field.IMEI_NUMBER, DeviceInfo.Field.IMSI_NUMBER
        );

        // Example 3: Getting mode of Operation
        deviceInfo.getOperationMode(new DeviceInfo.DeviceInfoResponseHandler() {
            @Override
            public void onSuccess(String result) {
                String operationModeStr = result;
                // if you prefer to store it in enum form;
                DeviceInfo.PosModeEnum operationMode = DeviceInfo.PosModeEnum.valueOf(operationModeStr);

                Log.i("Example 3", "getOperationMode returned: " + operationMode);
            }

            @Override
            public void onFail(String errMessage) {
                Log.e("DeviceInfoService", errMessage);
            }
        });

        // Example 4: Setting bank parameters
        deviceInfo.setAppParams(result-> {
            Log.i("Example 4", "setBankParams returned: " + result);
        }, "13232", "122a2");
    }
}