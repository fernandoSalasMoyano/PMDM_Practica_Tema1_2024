package es.studium.practica;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    Button btnBotonMostrar;
    Button btnBotonVaciar;
    Button btnBotonSpanish;
    Button btnBotonFrench;
    Button btnBotonEnglish;
    EditText edtNombre;
    EditText edtApellidos;
    EditText edtEdad;
    RadioGroup rdgSexo;
    Spinner spnEstado;
    Switch swtHijos;
    TextView txtTexto;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // Si está en horizontal, cargar el layout de landscape
            setContentView(R.layout.land_activity_main);
        } else {
            // Si está en vertical, cargar el layout normal
            setContentView(R.layout.activity_main);
        }

        btnBotonMostrar = findViewById(R.id.buttonMostrar);
        btnBotonMostrar.setOnClickListener(this);
        btnBotonVaciar = findViewById(R.id.buttonVaciar);
        btnBotonVaciar.setOnClickListener(this);
        btnBotonSpanish = findViewById(R.id.botonSpanish);
        btnBotonSpanish.setOnClickListener(this);
        btnBotonFrench = findViewById(R.id.botonFrench);
        btnBotonFrench.setOnClickListener(this);
        btnBotonEnglish = findViewById(R.id.botonEnglish);
        btnBotonEnglish.setOnClickListener(this);

        edtNombre = findViewById(R.id.editTextNombre);
        edtApellidos = findViewById(R.id.editTextApellido);
        edtEdad = findViewById(R.id.editTextEdad);

        rdgSexo = findViewById(R.id.radioGroup);

        spnEstado = findViewById(R.id.spinner);
        // Crear un ArrayAdapter usando el array de strings definido en strings.xml
        ArrayAdapter<CharSequence> adaptador = ArrayAdapter.createFromResource(
                this, R.array.estadoCivil, android.R.layout.simple_spinner_item);
        spnEstado.setAdapter(adaptador);

        swtHijos = findViewById(R.id.switch1);

        txtTexto = findViewById(R.id.textView);
    }

    @Override
    public void onClick(View view)
    {
        if (view.getId() == R.id.buttonMostrar)
        {
            // Obtener el contenido de los EditText
            String nombre = edtNombre.getText().toString();
            String apellidos = edtApellidos.getText().toString();
            String edadTexto = edtEdad.getText().toString();
            int edad = 0;
            if (!edadTexto.equals(""))
            {
                edad = Integer.parseInt(edadTexto);
            }

            // Obtener el sexo seleccionado
            int sexoSeleccionado = rdgSexo.getCheckedRadioButtonId();
            RadioButton rdb = findViewById(sexoSeleccionado);
            String sexo = "";
            if(sexoSeleccionado != -1)
            {
                sexo = rdb.getText().toString();
            }

            // Obtener el estado civil seleccionado en el Spinner
            String estado = spnEstado.getSelectedItem().toString();

            // Comprobar si el Switch está activado o no
            boolean hijos = swtHijos.isChecked();

            // Validar si los campos EditText están vacíos o si el estado civil no ha sido seleccionado
            String[] estadosCiviles = getResources().getStringArray(R.array.estadoCivil);
            String seleccionarEstado = estadosCiviles[0];
            if (nombre.isEmpty() || apellidos.isEmpty() || edadTexto.isEmpty() || estado.equals(seleccionarEstado) || sexoSeleccionado == -1)
            {
                txtTexto.setTextColor(ContextCompat.getColor(this, R.color.red));
                txtTexto.setText(R.string.campos_Vacios);

                if (nombre.isEmpty()) {
                    txtTexto.append(getString(R.string.error_nombre));
                }
                if (apellidos.isEmpty()) {
                    txtTexto.append(getString(R.string.error_apellidos));
                }
                if (edadTexto.isEmpty()) {
                    txtTexto.append(getString(R.string.error_edad));
                }
                if (estado.equals(seleccionarEstado)) {
                    txtTexto.append(getString(R.string.error_estado_civil));
                }
                if (sexoSeleccionado == -1) {
                    txtTexto.append(getString(R.string.error_sexo));
                }
            }
            else
            {
                txtTexto.setText("");

                // Obtener los textos de los recursos
                String mayor18 = getString(R.string.mayor18);
                String menor18 = getString(R.string.menor18);
                String siHijos = getString(R.string.si_Hijos);
                String noHijos = getString(R.string.no_Hijos);

                // Crear el mensaje para mostrar
                String mensaje = apellidos + ", " + nombre + ", " + (edad >= 18 ? mayor18 : menor18) + ", " + sexo + ", " + estado + " ";

                // Manejar la situación de los hijos
                mensaje += (hijos ? siHijos : noHijos);

                // Mostrar el mensaje final en el TextView
                Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
            }
        }
        else if (view.getId() == R.id.buttonVaciar)
        {
            edtNombre.setText("");
            edtApellidos.setText("");
            edtEdad.setText("");
            rdgSexo.clearCheck();
            spnEstado.setSelection(0);
            swtHijos.setChecked(false);
            txtTexto.setText("");
            edtNombre.requestFocus();
        }
        else if (view.getId() == R.id.botonSpanish)
        {
            if((getResources().getConfiguration().locale.getLanguage()).equals("es"))
            {
                txtTexto.setTextColor(ContextCompat.getColor(this, R.color.red));
                txtTexto.setText(R.string.error_idioma);
            }
            else
            {
                setLocale("es");
            }
        }
        else if (view.getId() == R.id.botonFrench)
        {
            if((getResources().getConfiguration().locale.getLanguage()).equals("fr"))
            {
                txtTexto.setTextColor(ContextCompat.getColor(this, R.color.red));
                txtTexto.setText(R.string.error_idioma);
            }
            else
            {
                setLocale("fr");
            }
        }
        else if (view.getId() == R.id.botonEnglish)
        {
            if((getResources().getConfiguration().locale.getLanguage()).equals("en"))
            {
                txtTexto.setTextColor(ContextCompat.getColor(this, R.color.red));
                txtTexto.setText(R.string.error_idioma);
            }
            else
            {
                setLocale("en");
            }
        }

    }
    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);

        Resources resources = getResources();
        Configuration config = resources.getConfiguration();

        // Usamos el método setLocale para versiones >= API 24 (Nougat) y
        // adjustamos locales para versiones más antiguas
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            config.setLocale(locale);
        } else {
            config.locale = locale;
        }

        // Actualiza la configuración del recurso
        DisplayMetrics dm = resources.getDisplayMetrics();
        resources.updateConfiguration(config, dm);

        // Reiniciar la actividad para aplicar el nuevo idioma
        Intent refresh = new Intent(this, MainActivity.class);
        startActivity(refresh);
        finish(); // Finalizar la actividad actual
    }
}
