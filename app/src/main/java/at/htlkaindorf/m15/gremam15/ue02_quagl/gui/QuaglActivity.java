package at.htlkaindorf.m15.gremam15.ue02_quagl.gui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import at.htlkaindorf.m15.gremam15.ue02_quagl.R;
import at.htlkaindorf.m15.gremam15.ue02_quagl.calc.QuaglRechner;

// https://iconhandbook.co.uk/reference/chart/android/
public class QuaglActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private double getDoubleValue(int resId){
        final EditText editText= findViewById(resId);
        final String text = editText.getText().toString();
        return Double.parseDouble(text);
    }

    public void onLoesen(View view) {
        try {
            final double a = getDoubleValue(R.id.eTa);
            final double b = getDoubleValue(R.id.eTb);
            final double c = getDoubleValue(R.id.eTc);
            final QuaglRechner r = new QuaglRechner(() -> getString(R.string.aMustNotBeZero), a, b, c);
            //Toast.makeText(this, r.getNumberOfResults(), Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, RecyclerResultActivity.class);
            intent.putExtra("nor", r.getNumberOfResults()).putExtra("x1", r.getX1()).putExtra("x2", r.getX2());
            intent.putExtra("a", a).putExtra("b", b).putExtra("c", c).putExtra("isC", r.isC()).putExtra("imag", r.getImag());
            System.out.println();
            startActivity(intent);
        } catch (NumberFormatException e) {
            Snackbar.make(findViewById(R.id.eTa), getString(R.string.invalidInput), Snackbar.LENGTH_LONG).show();
        } catch (Exception e) {
            //Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            if (e.getMessage() != null) Snackbar.make(findViewById(R.id.eTa), e.getMessage(), Snackbar.LENGTH_LONG).show();
            e.printStackTrace();
        }



        //Toast.makeText(this, editText.getText().toString(), Toast.LENGTH_LONG).show();
    }
}
