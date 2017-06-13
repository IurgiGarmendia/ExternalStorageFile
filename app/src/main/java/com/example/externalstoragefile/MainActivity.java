package com.example.externalstoragefile;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    private final String FILENAME ="testfile.txt";
    EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEditText= (EditText) findViewById(R.id.edit_text);
    }
    public boolean isExternalStorageWritable(){
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    public boolean isExternalStorageReadable(){
        return isExternalStorageWritable() || Environment.MEDIA_MOUNTED_READ_ONLY.equals(Environment.getExternalStorageState());
    }

    public void saveFile(View view) throws IOException {
        if(isExternalStorageWritable()){
            File textfile= new File(Environment.getExternalStorageDirectory(), FILENAME);
            try {
                FileOutputStream fileOutputStream=new FileOutputStream(textfile);
                fileOutputStream.write(mEditText.getText().toString().getBytes());
                fileOutputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }else{
            Toast.makeText(this, "Cannot write to external storage",Toast.LENGTH_LONG).show();
        }
    }

    public void readFile(View view){
        if(isExternalStorageReadable()){
            StringBuilder stringBuilder= new StringBuilder();
            File textfile = new File(Environment.getExternalStorageDirectory(), FILENAME);
            try {
                FileInputStream fileInputStream = new FileInputStream(textfile);
                InputStreamReader inputStreamReader= new InputStreamReader(fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String newLine=null;
                while ((newLine=bufferedReader.readLine()) !=null){
                    stringBuilder.append(newLine+ "\n");
                }
                fileInputStream.close();
                mEditText.setText(stringBuilder);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            Toast.makeText(this, "Cannot write to external storage",Toast.LENGTH_LONG).show();
        }
    }
}
