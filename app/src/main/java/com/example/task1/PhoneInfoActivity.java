
package com.example.task1;

import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

    public class PhoneInfoActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_phone_info);

            TextView phoneInfoText = findViewById(R.id.phoneInfoText);

            StringBuilder info = new StringBuilder();
            info.append("Device Model: ").append(Build.MODEL).append("\n\n");
            info.append("Manufacturer: ").append(Build.MANUFACTURER).append("\n\n");
            info.append("Android Version: ").append(Build.VERSION.RELEASE).append("\n\n");
            info.append("API Level: ").append(Build.VERSION.SDK_INT).append("\n\n");
            info.append("Hardware: ").append(Build.HARDWARE).append("\n\n");
            info.append("Product: ").append(Build.PRODUCT).append("\n\n");
            info.append("Board: ").append(Build.BOARD).append("\n\n");
            info.append("Brand: ").append(Build.BRAND).append("\n\n");
            info.append("Device: ").append(Build.DEVICE).append("\n\n");
            info.append("Display: ").append(Build.DISPLAY).append("\n\n");
            info.append("Fingerprint: ").append(Build.FINGERPRINT).append("\n\n");
            info.append("Host: ").append(Build.HOST).append("\n\n");
            info.append("ID: ").append(Build.ID).append("\n\n");
            info.append("Tags: ").append(Build.TAGS).append("\n\n");
            info.append("Type: ").append(Build.TYPE).append("\n\n");
            info.append("User: ").append(Build.USER);

            phoneInfoText.setText(info.toString());
        }
    }