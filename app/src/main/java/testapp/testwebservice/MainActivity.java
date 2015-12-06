package testapp.testwebservice;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;



public class MainActivity extends AppCompatActivity {

    String TAG = "Response";
    Button btnCal,btnFar,btnClear;
    EditText textCel,textFar;

    String getCel;
    String getFar;
    SoapPrimitive resultString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCal = (Button) findViewById(R.id.btnCel);
        btnFar = (Button) findViewById(R.id.btnFar);
        btnClear = (Button) findViewById(R.id.btnClear);
        textCel = (EditText) findViewById(R.id.txtCel);
        textFar = (EditText) findViewById(R.id.txtFar);

        btnCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCel = textCel.getText().toString();
                AsyncCallWS_CTOF task = new AsyncCallWS_CTOF();

                task.execute();
            }
        });//btn cal

        btnFar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFar = textFar.getText().toString();
                AsyncCallWS_FTOC task = new AsyncCallWS_FTOC();

                task.execute();
            }
        });// btn Far

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textCel.setText("");
                textFar.setText("");
            }
        });


    }// Main Method

    private class AsyncCallWS_CTOF extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            Log.i(TAG, "onPreExecute");
        }
        @Override
        protected Void doInBackground(Void... params) {
            Log.i(TAG, "doInBackground");
            ConvertCaltoFar();
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            Log.i(TAG, "onPostExecute");
            Toast.makeText(MainActivity.this, "Fahrenheit : " + resultString.toString(), Toast.LENGTH_LONG).show();
            textFar.setText(resultString.toString());
        }
    }// Class Async

    private void ConvertCaltoFar() {
        String SOAP_ACTION = "http://www.w3schools.com/webservices/CelsiusToFahrenheit";
        String METHOD_NAME = "CelsiusToFahrenheit";
        String NAMESPACE = "http://www.w3schools.com/webservices/";
        String URL = "http://www.w3schools.com/webservices/tempconvert.asmx";

        try {
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            Request.addProperty("Celsius", getCel);

            SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelope.dotNet = true;
            soapEnvelope.setOutputSoapObject(Request);

            HttpTransportSE transport = new HttpTransportSE(URL);

            transport.call(SOAP_ACTION, soapEnvelope);
            resultString = (SoapPrimitive) soapEnvelope.getResponse();


            Log.i(TAG, "Result Celsius: " + resultString);
        } catch (Exception ex) {
            Log.e(TAG, "Error: " + ex.getMessage());
        }
    }// Function Cal


    private class AsyncCallWS_FTOC extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            Log.i(TAG, "onPreExecute");
        }
        @Override
        protected Void doInBackground(Void... params) {
            Log.i(TAG, "doInBackground");
            ConvertFartoCal();
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            Log.i(TAG, "onPostExecute");
            Toast.makeText(MainActivity.this, "Celsius : " + resultString.toString(), Toast.LENGTH_LONG).show();
            textCel.setText(resultString.toString());
        }
    }// Class AsyncCallWS_FTOC

    private void ConvertFartoCal() {
        String SOAP_ACTION = "http://www.w3schools.com/webservices/FahrenheitToCelsius";
        String METHOD_NAME = "FahrenheitToCelsius";
        String NAMESPACE = "http://www.w3schools.com/webservices/";
        String URL = "http://www.w3schools.com/webservices/tempconvert.asmx";

        try {
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            Request.addProperty("Fahrenheit", getFar);

            SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelope.dotNet = true;
            soapEnvelope.setOutputSoapObject(Request);

            HttpTransportSE transport = new HttpTransportSE(URL);

            transport.call(SOAP_ACTION, soapEnvelope);
            resultString = (SoapPrimitive) soapEnvelope.getResponse();


            Log.i(TAG, "Result Fahrenheit: " + resultString);
        } catch (Exception ex) {
            Log.e(TAG, "Error: " + ex.getMessage());
        }
    }// Class Convert
}
