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
    this method writes a report of the ocr success rate for a single file
    @param String report :the string that contains recognition success rates for all files
    @param String recogtext :the ocr string for this particular file
    @param String filename :the name of the file for wich the success rate is calculated
    @param JSONObject js :the object that contains the string to compare to recogtext
    @return String report
    @modifies String report to update it with the result of that picture
    Written by Balzan Pietro
     */
    public String testAndReport(String report, String recogtext, String filename, JSONObject js){
        String original= null;
        try {
            original = js.getString("Ingredienti");
        }
        catch (org.json.JSONException e){
            return report+filename+" => something went wrong.\n";
        }
        double n= ocrEffectiveness(original, recogtext);
        report= report+filename+" => Success ratio: "+String.valueOf(n)+"\n";
        return report;
    }

    /*
    this method compares the recognized string with the original one.
    @param String original
    @param String OCRtext
    @return Double p : (0 <= p <= 1) ratio of matching words in the two strings
    written by Balzan Pietro
     */
    public double ocrEffectiveness(String original, String OCRtext) {
        String s1= original.toLowerCase();
        String s2= OCRtext.toLowerCase();
        int rec = 0;    //matching words
        int j=0;
        int beginsWithIngredients= 0;
        String[] a= s1.split(": |:|, |,|\\. |\\.");
        if(a[0].equalsIgnoreCase("ingredients") || a[0].equalsIgnoreCase("ingredienti")) {
            j = 1;  //start fom the second ingredient
            beginsWithIngredients = 1;  //one less ingredient
        }
        int par= a.length;
        for (int i=j; i<par; i++){
            if(s2.contains(a[i])){
                rec++;
            }
        }
        return rec/(par-beginsWithIngredients);
    }
}