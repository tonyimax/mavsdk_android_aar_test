package com.reverse.sdkcoretest;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.reverse.sdkcoretest.databinding.ActivityMainBinding;
import com.reverse.sdkcore.Core; //导入SDK包

public class MainActivity extends AppCompatActivity {
    static {
        System.loadLibrary("sdkcoretest");
    }

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        TextView tv = binding.sampleText;
        tv.setText(stringFromJNI());

        //创建SDK实例
        Core c = new Core();
        //启动服务
        c.runMavsdkServer();
        Button btnArm = binding.btnArm;//解锁按钮
        Button btnDisArm = binding.btnDisArm;//闭锁按钮
        Button btnTakeOff = binding.btnTakeOff;//起飞按钮
        Button btnLand = binding.btnLand;//降落按钮
        Button btnRtl = binding.btnRtl;//返航按钮

        btnTakeOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c.TakeOff();//起飞
            }
        });

        btnLand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c.Land();//降落
            }
        });

        btnArm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c.Arm();//解锁
            }
        });

        btnDisArm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c.DisArm();//闭锁
            }
        });

        btnRtl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c.Rtl();
            }
        });
    }

    public native String stringFromJNI();
}