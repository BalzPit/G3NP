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
    public double OCReffectiveness(String original, String OCRtext){
        int rec= 0;         //matching words
        int par= 0;         //total amount of words checked
        int o= original.length();
        int r= OCRtext.length();
        int oi=0;          //indexes of characters to compare
        int ri=0;          //in the two strings
        char comma= ",".charAt(0);
        while(oi<o && ri<r){
            char n= original.charAt(oi);
            char m= OCRtext.charAt(ri);
            if (n==m){
                if (n==comma){  //the two chars are commas therefore both words that just ended were perfectly matching
                    rec++;
                    par++;
                }
                oi++;      //matching but the words still have chars to compare
                ri++;
            }
            else{
                oi= jumptonext(oi, original);  //comparison failed, go to the next word in both strings
                ri= jumptonext(ri, OCRtext);
                par++;
            }
        }
        return rec/par;
    }

    /*
    this method returns the index of the next word of the string, if there is one
    (words are separated by commas)
    @param String s the string
    @param int i original index
    @return int i updated index signaling the start of the next word
    @modifies int i the index
    written by Balzan Pietro
     */
    public int jumptonext(int i, String s){
        char comma= ",".charAt(0);
        while(s.charAt(i)!=comma){
            i++;
            if (i==s.length()){           //we reached the end of the string
                return i;
            }
        }
        i++;  //when s.charAt(i) equals "," that is the end of the previous word, we need to go to the next one
        return i;
    }

}
