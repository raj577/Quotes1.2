package com.probein.quotes;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {
    public TextView textView;
    public Button share,next,previous;
    //    ImageView imageView;
    public ArrayList<Quote> quoteList;
    public int index;
    public Stack<Quote> previousQuote;
    public  boolean isPrevious;
    //    private BitmapDrawable bitmapDrawable;
//    private Bitmap bitmap1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

//Adding firebase cloud messeging functionaloty




        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Imageview to save the drawing cache
        textView = findViewById(R.id.textView);
        textView.buildDrawingCache();


        //Testing for the stuff




        //

//        imageView = findViewById(R.id.imageView);
//        imageView.setImageBitmap(textView.getDrawingCache());



        share = findViewById(R.id.sharebutton);
        next = findViewById(R.id.next_button);
        previous = findViewById(R.id.previous_button);

        previousQuote= new Stack<>();
        //1:import quotes from strings.xml

        Resources res = getResources();
        String[] allQuotes = res.getStringArray(R.array.quotes);
        quoteList = new ArrayList<>();
        addToQuoteList(allQuotes);
        //2:generate random quote
        final int quoteslength =quoteList.size();
        index=getrandomQuote(quoteslength-1);
        textView.setText(quoteList.get(index).toString());
        //3:generate random quote when next is pressed
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPrevious = false;
                index=getrandomQuote(quoteslength-1);

                textView.setText(quoteList.get(index).toString());


                //Setting up random no for background image change

                Random rn = new Random();
                int answer = rn.nextInt(10) + 1;

                switch (answer)
                {
                    case 1:
                        textView.setBackgroundResource(R.drawable.backgroundimg);
                        break;

                    case 2:
                        textView.setBackgroundResource(R.drawable.background1);
                        break;

                    case 3:
                        textView.setBackgroundResource(R.drawable.background2);
                        break;

                    case 4:
                        textView.setBackgroundResource(R.drawable.background3);
                        break;

                    case 5:
                        textView.setBackgroundResource(R.drawable.background4);
                        break;

                    case 6:
                        textView.setBackgroundResource(R.drawable.background3);
                        break;

                    case 7:
                        textView.setBackgroundResource(R.drawable.background2);
                        break;

                    case 8:
                        textView.setBackgroundResource(R.drawable.background1);
                        break;
                    case 9:
                        textView.setBackgroundResource(R.drawable.background4);
                        break;

                    case 0:
                        textView.setBackgroundResource(R.drawable.backgroundimg);
                        break;


                }

                previousQuote.push(quoteList.get(index));
            }
        });
        //4:recall previous quote when back button pressed
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isPrevious && previousQuote.size()>0){
                    previousQuote.pop();
                    isPrevious = true;
                }
                if(previousQuote.size()>0 && isPrevious){
                    textView.setText(previousQuote.pop().toString());

                    Random rn = new Random();
                    int answer = rn.nextInt(10) + 1;

                    switch (answer)
                    {
                        case 1:
                            textView.setBackgroundResource(R.drawable.backgroundimg);
                            break;

                        case 2:
                            textView.setBackgroundResource(R.drawable.background1);
                            break;

                        case 3:
                            textView.setBackgroundResource(R.drawable.background2);
                            break;

                        case 4:
                            textView.setBackgroundResource(R.drawable.background3);
                            break;

                        case 5:
                            textView.setBackgroundResource(R.drawable.background4);
                            break;

                        case 6:
                            textView.setBackgroundResource(R.drawable.background3);
                            break;

                        case 7:
                            textView.setBackgroundResource(R.drawable.background2);
                            break;

                        case 8:
                            textView.setBackgroundResource(R.drawable.background1);
                            break;
                        case 9:
                            textView.setBackgroundResource(R.drawable.background4);
                            break;

                        case 0:
                            textView.setBackgroundResource(R.drawable.backgroundimg);
                            break;


                    }


                }
                else
                    Toast.makeText(MainActivity.this,"Get new quote",Toast.LENGTH_SHORT).show();
            }
        });
        //5:share quote on social media
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //MAIN
//        Intent sendIntent = new Intent();
//        sendIntent.setAction(Intent.ACTION_SEND);
//        sendIntent.putExtra(Intent.EXTRA_TEXT,quoteList.get(index).toString());
//        sendIntent.setType("text/plain");
//        startActivity(sendIntent);
//END OF MAIN FUNCTION

                View content = findViewById(R.id.textView);
                content.setDrawingCacheEnabled(true);

                Bitmap bitmap = content.getDrawingCache();
                File root = Environment.getExternalStorageDirectory();
                File cachePath = new File(root.getAbsolutePath() + "/DCIM/Camera/image.jpg");
                try {
                    cachePath.createNewFile();
                    FileOutputStream ostream = new FileOutputStream(cachePath);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, ostream);
                    ostream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }


                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("image/*");
                share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(cachePath));
                startActivity(Intent.createChooser(share,"Share via"));

            }



        });

//TODO:
//1. save favourate quote
        // 2.Make Hindi Version OF app
        //3.Include Top 100 Quotes
        //4.Make UI/UX more pleasing




    }

    //Adding all quotes to arrayList
    public void addToQuoteList(String[] allQuotes){
        for(int i = 0;i<allQuotes.length;i++){
            String quote = allQuotes[i];
            Quote newquote = new Quote(quote);
            quoteList.add(newquote);

        }
    }

    public int getrandomQuote(int length){
        return (int) (Math.random()*length)+1;
    }


}
