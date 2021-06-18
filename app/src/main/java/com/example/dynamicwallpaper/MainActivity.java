package com.example.dynamicwallpaper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.acMainSetting).setOnClickListener(v -> {
            Intent chooseIntent = new Intent(Intent.ACTION_SET_WALLPAPER);
            // 启动系统选择应用
            Intent intent = new Intent(Intent.ACTION_CHOOSER);
            intent.putExtra(Intent.EXTRA_INTENT, chooseIntent);
            intent.putExtra(Intent.EXTRA_TITLE, "选择动态壁纸");
            startActivity(intent);
        });
    }
}