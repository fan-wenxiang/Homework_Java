package com.java.fanwenxiang.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;

import com.java.fanwenxiang.R;


public class ChangeCategoryActivity extends Activity {
    private boolean addNewsType;
    private boolean addPaperType;
    private CheckBox news_button=null;
    private CheckBox paper_button=null;
    private ImageButton back_button=null;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category);
        Intent intent=getIntent();

        addNewsType=intent.getBooleanExtra("addNewsType", false);
        addPaperType=intent.getBooleanExtra("addPaperType", false);
        news_button=findViewById(R.id.news_button);
        paper_button=findViewById(R.id.paper_button);
        back_button=findViewById(R.id.back_button);
        back_button.getParent().bringChildToFront(back_button);
        setResult(1, intent);

        news_button.setChecked(addNewsType);
        paper_button.setChecked(addPaperType);

        news_button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                addNewsType=checked;
            }
        });
        paper_button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                addPaperType=checked;
            }
        });
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();

                intent.putExtra("addNewsType", addNewsType);
                intent.putExtra("addPaperType", addPaperType);
                setResult(0, intent);
                finish();
            }
        });


    }


}
