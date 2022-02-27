package dadm.quixada.ufc.beber_agua;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Locale;

import dadm.quixada.ufc.beber_agua.Model.CalcularIngestao;

public class MainActivity extends AppCompatActivity {

    private EditText edt_peso, edt_idade;
    private Button btn_calcular, btn_lembrete, btn_alarme;
    private TextView txt_resultado_ml, txt_hora, txt_minutos;
    private ImageView redefinir_dados;

    private CalcularIngestao caluCalcularIngestao;
    private double resultado_ml = 0.0;

    TimePickerDialog timePickerDialog;
    Calendar calendario;
    int hora_atual = 0;
    int minutos_atuais = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        caluCalcularIngestao = new CalcularIngestao();
        getSupportActionBar().hide();
        iniciarComponentes();

        btn_calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edt_peso.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, R.string.toast_informe_peso, Toast.LENGTH_SHORT).show();
                }else if(edt_idade.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, R.string.toast_informe_idade, Toast.LENGTH_SHORT).show();
                }else{
                    double peso = Double.parseDouble(edt_peso.getText().toString());
                    int idade = Integer.parseInt(edt_idade.getText().toString());
                    caluCalcularIngestao.CalcularTotalML(peso, idade);
                    resultado_ml = caluCalcularIngestao.ResultadoML();
                    NumberFormat formatar = NumberFormat.getNumberInstance(new Locale("pt", "BR"));
                    formatar.isGroupingUsed();
                    txt_resultado_ml.setText(formatar.format(resultado_ml)+ " " + "ml");
                }
            }
        });

        redefinir_dados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle(R.string.dialog_title)
                    .setMessage(R.string.dialog_des)
                    .setPositiveButton("Ok", (dialogInterface, i) -> {
                        edt_peso.setText("");
                        edt_idade.setText("");
                        txt_resultado_ml.setText("");
                    });
                alert.setNegativeButton("Cancelar", (dialogInterface, i) -> {});
                AlertDialog dialog = alert.create();
                dialog.show();
            }
        });

        btn_lembrete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendario = Calendar.getInstance();
                hora_atual = calendario.get(Calendar.HOUR_OF_DAY);
                minutos_atuais = calendario.get(Calendar.MINUTE);
                timePickerDialog = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        txt_hora.setText(String.format("%02d",i));
                        txt_minutos.setText(String.format("%02d",i1));
                    }
                }, hora_atual, minutos_atuais, true);
                timePickerDialog.show();
            }
        });

        btn_alarme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!txt_hora.getText().toString().isEmpty() && !txt_minutos.getText().toString().isEmpty()){
                    Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);
                    intent.putExtra(AlarmClock.EXTRA_HOUR, Integer.parseInt(txt_hora.getText().toString()));
                    intent.putExtra(AlarmClock.EXTRA_MINUTES, Integer.parseInt(txt_minutos.getText().toString()));
                    intent.putExtra(AlarmClock.EXTRA_MESSAGE, getString(R.string.alarme_mensagem));
                    startActivity(intent);

                    if(intent.resolveActivity(getPackageManager()) != null){
                        startActivity(intent);
                    }
                }
            }
        });
    }

    private void iniciarComponentes(){
        edt_peso = findViewById(R.id.edt_peso);
        edt_idade = findViewById(R.id.edt_idade);
        btn_calcular = findViewById(R.id.bt_calcular);
        txt_resultado_ml = findViewById(R.id.txt_resultado_ml);
        redefinir_dados = findViewById(R.id.ic_redefinir);
        btn_lembrete = findViewById(R.id.bt_definir_lembrete);
        btn_alarme = findViewById(R.id.bt_alarme);
        txt_hora = findViewById(R.id.txt_hora);
        txt_minutos = findViewById(R.id.txt_minutos);
    }
}