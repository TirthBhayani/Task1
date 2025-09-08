package com.example.task1;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.List;

public class SimInfoActivity extends AppCompatActivity {

    private static final int REQUEST_PHONE_STATE = 100;
    private TextView simInfoText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sim_info);

        simInfoText = findViewById(R.id.simInfoText);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_PHONE_STATE);
        } else {
            showSimInfo();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_PHONE_STATE &&
                grantResults.length > 0 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            showSimInfo();
        } else {
            simInfoText.setText("Permission denied. Cannot fetch SIM details.");
        }
    }

    private void showSimInfo() {
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        StringBuilder info = new StringBuilder();

        info.append("SIM Operator: ").append(telephonyManager.getSimOperatorName()).append("\n\n");
        info.append("SIM Country: ").append(telephonyManager.getSimCountryIso()).append("\n\n");
        info.append("SIM Operator Code: ").append(telephonyManager.getSimOperator()).append("\n\n");

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                info.append("Device ID (IMEI): ").append(telephonyManager.getImei()).append("\n\n");
            } else {
                info.append("Device ID (IMEI): ").append(telephonyManager.getDeviceId()).append("\n\n");
            }
        } catch (SecurityException e) {
            info.append("Permission required for IMEI\n\n");
        }

        info.append("Network Type: ").append(getNetworkType(telephonyManager.getNetworkType())).append("\n\n");
        info.append("Phone Type: ").append(getPhoneType(telephonyManager.getPhoneType())).append("\n\n");
        info.append("Network Country: ").append(telephonyManager.getNetworkCountryIso()).append("\n\n");
        info.append("Network Operator: ").append(telephonyManager.getNetworkOperatorName()).append("\n\n");
        info.append("Network Operator Code: ").append(telephonyManager.getNetworkOperator()).append("\n\n");

        // Dual SIM support (SubscriptionManager)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            SubscriptionManager subscriptionManager = (SubscriptionManager) getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                List<SubscriptionInfo> subscriptionInfos = subscriptionManager.getActiveSubscriptionInfoList();
                if (subscriptionInfos != null) {
                    for (SubscriptionInfo subInfo : subscriptionInfos) {
                        info.append("\n--- SIM Slot ").append(subInfo.getSimSlotIndex()).append(" ---\n");
                        info.append("Carrier: ").append(subInfo.getCarrierName()).append("\n");
                        info.append("Country ISO: ").append(subInfo.getCountryIso()).append("\n");
                        info.append("Number: ").append(subInfo.getNumber()).append("\n");
                        info.append("Subscription ID: ").append(subInfo.getSubscriptionId()).append("\n\n");
                    }
                }
            }
        }

        simInfoText.setText(info.toString());
    }
    private String getNetworkType(int type) {
        switch (type) {
            case TelephonyManager.NETWORK_TYPE_GPRS: return "GPRS";
            case TelephonyManager.NETWORK_TYPE_EDGE: return "EDGE";
            case TelephonyManager.NETWORK_TYPE_UMTS: return "UMTS";
            case TelephonyManager.NETWORK_TYPE_HSDPA: return "HSDPA";
            case TelephonyManager.NETWORK_TYPE_HSUPA: return "HSUPA";
            case TelephonyManager.NETWORK_TYPE_HSPA: return "HSPA";
            case TelephonyManager.NETWORK_TYPE_CDMA: return "CDMA";
            case TelephonyManager.NETWORK_TYPE_EVDO_0: return "EVDO_0";
            case TelephonyManager.NETWORK_TYPE_EVDO_A: return "EVDO_A";
            case TelephonyManager.NETWORK_TYPE_EVDO_B: return "EVDO_B";
            case TelephonyManager.NETWORK_TYPE_1xRTT: return "1xRTT";
            case TelephonyManager.NETWORK_TYPE_LTE: return "LTE";
            case TelephonyManager.NETWORK_TYPE_NR: return "NR (5G)";
            default: return "Unknown";
        }
    }

    private String getPhoneType(int type) {
        switch (type) {
            case TelephonyManager.PHONE_TYPE_NONE: return "None";
            case TelephonyManager.PHONE_TYPE_GSM: return "GSM";
            case TelephonyManager.PHONE_TYPE_CDMA: return "CDMA";
            case TelephonyManager.PHONE_TYPE_SIP: return "SIP";
            default: return "Unknown";
        }
    }
}
