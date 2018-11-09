package ocrcamera.se18.unipd.ocr_comparison;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ComparisonActivity extends AppCompatActivity {
    public String testooriginale;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comparison);

        String filename= "foto1";
        File file= new File(Environment.getExternalStorageDirectory().getAbsolutePath(), filename+".jpg");
        ImageView  image = findViewById(R.id.pippo);
        File txtf= new File(Environment.getExternalStorageDirectory().getAbsolutePath(), filename+".txt");
        TextView text= findViewById(R.id.pluto);

        if (file.exists()) {
            Bitmap bm = BitmapFactory.decodeFile(file.getAbsolutePath());
            image.setImageBitmap(bm);
        }
        else{
            text.setText("Image not found");
        }
    }

    /*
    this method compares the recognized string with the original one.
    @param String original
    @param String OCRtext
    @return Double p  0 <= p <= 1 ratio of words that are the same in both strings
    written by Balzan Pietro
     */
    public double OCReffectiveness(String original, String OCRtext) {
        int rec = 0;         //matching words
        String[] a= original.split(",[ ]*");
        for (int i=0; i<a.length; i++){
            if(OCRtext.contains(a[i])){
                rec++;
            }
        }
        int par= a.length;   //numbers of words in the original
        return rec/par;
    }
}
