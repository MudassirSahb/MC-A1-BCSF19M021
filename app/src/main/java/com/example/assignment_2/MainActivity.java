package com.example.assignment_2;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button search, git;
    EditText surah;
    EditText ayat;
    TextView displaySurahName;
    TextView displayAyat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        search = findViewById(R.id.search);
        surah = findViewById(R.id.surahNo);
        ayat = findViewById(R.id.ayatNo);
        git = findViewById(R.id.gitbtn);
        displaySurahName = findViewById(R.id.surahName);
        displayAyat = findViewById(R.id.displayAyat);
        QuranDataHelper qdh = new QuranDataHelper();
        QuranText qt = new QuranText();

        git.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://github.com/tk34395/Quran-App/commits/main";
                Uri webpage = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                startActivity(intent);
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int suratInt, ayatInt;
                try {
                    suratInt = Integer.parseInt(surah.getText().toString());
                }
                catch(Exception e){
                    suratInt = -1;
                }
                try {
                    ayatInt = Integer.parseInt((ayat.getText().toString()));
                }
                catch(Exception e){
                    ayatInt = -1;
                }
                int temp1, temp2, totalAyats=0;
                if(suratInt>0 && suratInt < 115){
                    totalAyats = qdh.surahAyatCount[suratInt-1];
                    if(suratInt==1){
                        totalAyats-=1;
                    }
                }

                if(suratInt <= 0  || suratInt >114)
                {
                    displaySurahName.setVisibility(View.GONE);
                    displayAyat.setText("Invalid  Surah  Index!");
                    displayAyat.setTextSize(25);
                }
                else if(ayatInt > totalAyats || ayatInt <= 0){
                    displaySurahName.setVisibility(View.GONE);
                    displayAyat.setText("Invalid  Ayat  Index!");
                    displayAyat.setTextSize(25);
                }
                else{
                    temp1 = qdh.SSP[suratInt-1];
                    temp2 = temp1 + ayatInt;
                    displaySurahName.setVisibility(View.VISIBLE);
                    displaySurahName.setText( "سورة "+ qdh.urduSurahNames[suratInt-1]);
                    displayAyat.setText( qt.QuranArabicText[temp2-1]+ "\n");
                    displayAyat.setTextSize(50);
                }
            }
        });
    }
}
