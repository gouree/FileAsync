package com.example.gouree.fileasync;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    TextView content;
    EditText text;
    Button ok;
    Button delete;
    //file name
    static String FILENAME = "test.txt";
    File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //call method setid to set the ids
        setIds();

        //create file
        file = new File(getFilesDir(), FILENAME);

        try {

            //on creation of file
            if (file.createNewFile()) {
                Toast.makeText(getApplicationContext(), "File Created",
                        Toast.LENGTH_LONG).show();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //on click of add data button add contents of edit text to file
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value = text.getText().toString();
                text.setText("");

                readFromFile rf = new readFromFile(file);
                rf.execute(value);
            }
        });

        //on click of delete button , delete the file and clear the text view
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                file.delete();
                Toast.makeText(MainActivity.this, "File Deleted", Toast.LENGTH_SHORT).show();
                content.setText("");

            }
        });

    }

    // set ids of edit text, text view and buttons
    public void setIds() {
        content = (TextView) findViewById(R.id.textView);
        text = (EditText) findViewById(R.id.editText);
        ok = (Button) findViewById(R.id.button);
        delete =(Button)findViewById(R.id.button2);
    }



    //use async task to read from file
    private class readFromFile extends AsyncTask <String, Integer, String > {

        // static String FILENAME = "test.txt";
        File f;

        //reading from file
        public readFromFile(File f) {
            super();
            this.f = f;
            // TODO Auto-generated constructor stub
        }


        //write in background the contents
        //add the valures from text view each time user adds contents to the file
        @Override
        protected String doInBackground(String... str) {
            String enter = "\n";
            FileWriter writer = null;
            try {
                writer = new FileWriter(f, true);
                writer.append(str[0].toString());
                writer.append(enter);
                writer.flush();

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                try {
                    writer.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            return null;

        }


        //display the result in text view by reading contents from file

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            String name = "";
            StringBuilder sb = new StringBuilder();
            FileReader fr = null;

            try {
                fr = new FileReader(f);
                BufferedReader br = new BufferedReader(fr);
                while ((name = br.readLine()) != null) {
                    sb.append(name+"\n");
                }
                br.close();
                fr.close();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            content.setText(sb.toString());
        }

    }}