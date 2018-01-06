package com.jqh.httpuploaddemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button_get).setOnClickListener(this);
        findViewById(R.id.button_head).setOnClickListener(this);
        findViewById(R.id.button_post).setOnClickListener(this);
    }

    /**
     * Get请求
     */
    private void getRequest(){

    }

    /**
     * Head请求
     */
    private void headRequest(){

    }

    /**
     * Post请求
     */
    private void postRequest(){

    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button_get:{
                getRequest();
                break;
            }
            case R.id.button_head: {
                headRequest();
                break;
            }
            case R.id.button_post: {
                postRequest();
                break;
            }
        }
    }
}
