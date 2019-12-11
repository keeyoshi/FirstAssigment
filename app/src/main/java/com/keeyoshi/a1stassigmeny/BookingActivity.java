package com.keeyoshi.a1stassigmeny;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;

public class BookingActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etName, etAdults, etChildren, etRoom, etCheckIn, etCheckOut;
    TextView tvResult;
    Spinner autoRoom;
    AutoCompleteTextView countryAuto;
    Button btnCalculate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        etName= findViewById(R.id.etName);
        etAdults=findViewById(R.id.etAdults);
        etChildren=findViewById(R.id.etChildren);
        etRoom=findViewById(R.id.etRoom);
        etCheckIn=findViewById(R.id.etCheckIn);
        etCheckOut=findViewById(R.id.etCheckOut);
        countryAuto=findViewById(R.id.autoTextView);
        autoRoom=findViewById(R.id.SpinnerRoom);
        btnCalculate=findViewById(R.id.btnCalculate);
        tvResult=findViewById(R.id.tvResult);
        String Rooms[] ={"Deluxe Rs-2000","Platinum Rs-4000","Presidential Rs-7000"};
        ArrayAdapter Spinneradapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,Rooms);
        String Country[] = {"Nepal","Afghanistan","Argentina","India","China","Spain","USA","Canada","Australia","England","New zealand","Poland","Germany","Chili"};
        ArrayAdapter autoTextadapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,Country);
        countryAuto.setAdapter(autoTextadapter);
        autoRoom.setAdapter(Spinneradapter);

        etCheckIn.setOnClickListener(this);
        etCheckOut.setOnClickListener(this);
        btnCalculate.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.etCheckIn:
            {
                if(TextUtils.isEmpty(etName.getText().toString())){
                    etName.setError("Enter a name");
                    etName.requestFocus();
                    return;
                }
                DataLoader(v);
                break;
            }
            case R.id.etCheckOut:
            {
                DataLoader(v);
                break;
            }
            case R.id.btnCalculate:
            {
                if(TextUtils.isEmpty(etCheckIn.getText().toString())){
                    etCheckIn.setError("Choose a date for check in");
                    etCheckIn.requestFocus();
                }
                else if(TextUtils.isEmpty(etCheckOut.getText().toString())){
                    etCheckOut.setError("Choose a date for check out");
                    etCheckOut.requestFocus();
                }
                else if(TextUtils.isEmpty(countryAuto.getText().toString())){
                    countryAuto.setError("Select a room");
                    countryAuto.requestFocus();
                }
                else if(TextUtils.isEmpty(etAdults.getText().toString())){
                    etAdults.setError("Enter how many number of Adults are staying ");
                    etAdults.requestFocus();

                }

                else if(TextUtils.isEmpty(etChildren.getText().toString())){
                    etChildren.setError("Enter how many number of Children are staying ");
                    etChildren.requestFocus();
                }

                else if(TextUtils.isEmpty(etRoom.getText().toString())){
                    etRoom.setError("Enter number of rooms");
                    etRoom.requestFocus();
                }
                else
                {
                    String checkIn = etCheckIn.getText().toString();
                    String checkOut = etCheckOut.getText().toString();
                    String arrcheckIn[] = checkIn.split("/",3 );
                    String arrcheckOut[] = checkOut.split("/",3 );
                    int checkday=Integer.parseInt(arrcheckIn[1]);
                    int checkoutday=Integer.parseInt(arrcheckOut[1]);
                    if(checkoutday>checkday)
                    {
                        int difference =Integer.parseInt(arrcheckOut[1])-Integer.parseInt(arrcheckIn[1]);
                        int room = Integer.parseInt(etRoom.getText().toString());
                        if(autoRoom.getSelectedItem().toString()=="Deluxe Rs-2000") {
                            float[] result=Calculate(2000,difference,room);
                            tvResult.setText("Total: Rs." + result[0]+"\n"+"Vat Rs.:"+result[1]+"\n"+"Gross Total: Rs."+result[2]);
                        }
                        else if(autoRoom.getSelectedItem().toString()=="Platinum Rs-4000")
                        {
                            float[] result=Calculate(4000,difference,room);
                            tvResult.setText("Total: Rs." + result[0]+"\n"+"Vat Rs.  :"+result[1]+"\n"+"Gross Total: Rs."+result[2]);
                        }
                        else if(autoRoom.getSelectedItem().toString()=="Presidential Rs-7000")
                        {
                            float[] result=Calculate(7000,difference,room);
                            tvResult.setText("Total: Rs." + result[0]+"\n"+"Vat Rs.:"+result[1]+"\n"+"Gross Total: Rs."+result[2]);

                        }
                    }
                    else {
                        etCheckOut.setError("Please select a valid date to check out");
                    }
                }

                break;

            }
        }
    }
    private float[] Calculate(int price,int difference,int room)
    {
        float total =difference*price*room;
        float vat =0.13f*total;
        float grandTotal =total+vat;
        return new float[]{total,vat,grandTotal};
    }
    private void DataLoader(View v )
    {
        switch (v.getId())
        {
            case R.id.etCheckIn:
            {
                final Calendar c =Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog= new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String date = month+"/"+dayOfMonth+"/"+year;
                        etCheckIn.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
                break;
            }
            case R.id.etCheckOut:
            {
                final Calendar c =Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog= new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String date =month+"/"+dayOfMonth+"/"+year;
                        etCheckOut.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
                break;
            }
        }


    }


}
