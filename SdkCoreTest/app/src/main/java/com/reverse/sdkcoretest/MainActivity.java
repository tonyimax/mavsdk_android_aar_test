package com.reverse.sdkcoretest;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.reverse.sdkcore.Core;
import com.reverse.sdkcoretest.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'sdkcoretest' library on application startup.
    static {
        System.loadLibrary("sdkcoretest");
    }

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Example of a call to a native method
        TextView tv = binding.sampleText;
        tv.setText(stringFromJNI());

        Core c = new Core();
        c.runMavsdkServer();

        ConstraintLayout btnArm = binding.btnArm;
        btnArm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("ARM======");
                c.Arm();
                System.out.println("ARM======called");
                System.out.println("TakeOff======");
                c.TakeOff();
                System.out.println("TakeOff======called");
            }
        });
    }

    /**
     * A native method that is implemented by the 'sdkcoretest' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}