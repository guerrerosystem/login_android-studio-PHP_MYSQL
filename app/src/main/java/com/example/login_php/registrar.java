package com.example.login_php;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class registrar extends AppCompatActivity {
    EditText txtName,txtEmail,pass;
    Button btn_insert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        txtName     = findViewById(R.id.ednombre);
        txtEmail    = findViewById(R.id.etemail);
        pass = findViewById(R.id.etcontraseña);
        btn_insert = findViewById(R.id.btn_register);





        btn_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                insertData();
            }
        });
    }

    private void insertData() {

        final String nombre = txtName.getText().toString().trim();
        final String email = txtEmail.getText().toString().trim();
        final String password = pass.getText().toString().trim();


        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("cargando...");

        if(nombre.isEmpty()){


            txtName.setError("complete los campos");
            return;
        }
        else if(email.isEmpty()){

            txtEmail.setError("complete los campos");
            return;
        }


        else{
            progressDialog.show();
            StringRequest request = new StringRequest(Request.Method.POST, "https://login.corporacionefransac.com/insertar.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            if(response.equalsIgnoreCase("Datos insertados")){

                                Toast.makeText(registrar.this, "Datos insertados", Toast.LENGTH_SHORT).show();

                                progressDialog.dismiss();

                                Intent intent=new Intent(registrar.this,login.class);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(registrar.this, response, Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                                Toast.makeText(registrar.this, "No se puede insrtar", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(registrar.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String,String> params = new HashMap<String,String>();

                    params.put("nombre",nombre);
                    params.put("email",email);
                    params.put("password",password);




                    return params;
                }
            };


            RequestQueue requestQueue = Volley.newRequestQueue(registrar.this);
            requestQueue.add(request);



        }




    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }






    public  void  login(View v){
        startActivity(new Intent(getApplicationContext(), login.class));
        finish();
    }


}