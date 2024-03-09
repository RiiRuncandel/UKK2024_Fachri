package com.example.ukk2024_fachri;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class MainActivity extends AppCompatActivity {

    private int[]   tombolNumeric={R.id.Nol,R.id.Satu,R.id.Dua,R.id.Tiga,R.id.Empat,R.id.Lima,R.id.Enam,R.id.Tujuh,R.id.Delapan,R.id.Sembilan};
    private int[]   tombolOperator={R.id.Kali,R.id.Bagi,R.id.Tambah,R.id.Kurang};

    private TextView LayarTampil;

    private TextView LayarHasil;
    private boolean angkaTerakhir;

    private boolean kaloError;

    private boolean setelahTitik;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.LayarTampil=(TextView) findViewById(R.id.LayarTampil);
        this.LayarHasil=(TextView) findViewById(R.id.LayarHasil);
        setNumericPadaSaatDiklik();
        setOperatorPadaSaatDiklik();
    }
    private void setNumericPadaSaatDiklik() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Button tombol=(Button) v;
                if (kaloError){
                    LayarTampil.setText(tombol.getText());
                }else {
                    LayarTampil.append(tombol.getText());
                }
                angkaTerakhir=true;
            }
        };
        for (int id : tombolNumeric){
            findViewById(id).setOnClickListener(listener);
        }
    }
    private void  setOperatorPadaSaatDiklik () {
        View.OnClickListener listener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (angkaTerakhir&& !kaloError){
                    Button tombol=(Button) v;
                    LayarTampil.append(tombol.getText());
                    angkaTerakhir=false;
                    setelahTitik=false;
                }
            }
        };
        for (int id:tombolOperator){
            findViewById(id).setOnClickListener(listener);
        }
        findViewById(R.id.Koma).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (angkaTerakhir && !kaloError && !setelahTitik){
                    LayarTampil.append(".");
                    angkaTerakhir=false;
                    setelahTitik=false;
                }
            }
        });
        findViewById(R.id.tombolC).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayarTampil.setText(" ");
                LayarHasil.setText(" ");
                angkaTerakhir=false;
                kaloError=false;
                setelahTitik=false;
            }
        });
        findViewById(R.id.Hasil).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {onEqual();
            }

            private void onEqual() {
                if (angkaTerakhir && !kaloError){
                    String txt =LayarTampil.getText().toString();
                    Expression expression =new ExpressionBuilder(txt).build();
                    try{
                        double result = expression.evaluate();
                        LayarHasil.setText(Double.toString(result));
                        setelahTitik=true;
                    }catch (ArithmeticException ex){
                        LayarHasil.setText("ERROR mas");
                        kaloError=true;
                        angkaTerakhir=false;
                    }
                }
            }
        });
    }
}