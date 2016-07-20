package com.khalnayak.imageprocessing.Setting;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.khalnayak.imageprocessing.R;

/**
 * Created by !!KhalNayak!! on 17-06-2016.
 */
public class Setting extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    LinearLayout layout;
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity);
        layout = (LinearLayout) findViewById(R.id.linear);
        findViewById(R.id.btnSettingBg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(Setting.this, view);
                popupMenu.setOnMenuItemClickListener(Setting.this);
                popupMenu.inflate(R.menu.pop_up_menu);
                popupMenu.show();
            }
        });
        sp = getSharedPreferences("BG_Color", Context.MODE_PRIVATE);
        layout.setBackgroundColor(sp.getInt("BG_Color_Key",Color.WHITE));

    }
    public boolean onMenuItemClick(MenuItem item) {
        SharedPreferences.Editor editor;
        switch (item.getItemId()) {
            case R.id.red:
                layout.setBackgroundColor(Color.RED);
                editor = sp.edit();
                editor.putInt("BG_Color_Key",Color.RED);
                editor.commit();
                Toast.makeText(this,"BackGround Color Updated",Toast.LENGTH_LONG).show();
                break;
            case R.id.blue:
                layout.setBackgroundColor(Color.BLUE);
                editor = sp.edit();
                editor.putInt("BG_Color_Key",Color.BLUE);
                editor.commit();
                Toast.makeText(this,"BackGround Color Updated",Toast.LENGTH_LONG).show();
                break;
            case R.id.green:
                layout.setBackgroundColor(Color.GREEN);
                editor = sp.edit();
                editor.putInt("BG_Color_Key",Color.GREEN);
                editor.commit();
                Toast.makeText(this,"BackGround Color Updated",Toast.LENGTH_LONG).show();
                break;
            case R.id.yellow:
                layout.setBackgroundColor(Color.YELLOW);
                editor = sp.edit();
                editor.putInt("BG_Color_Key",Color.YELLOW);
                editor.commit();
                Toast.makeText(this,"BackGround Color Updated",Toast.LENGTH_LONG).show();
                break;
        }
        return true;
    }
}
