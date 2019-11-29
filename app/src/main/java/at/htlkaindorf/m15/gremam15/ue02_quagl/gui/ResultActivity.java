package at.htlkaindorf.m15.gremam15.ue02_quagl.gui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.text.NumberFormat;
import java.util.Locale;

import at.htlkaindorf.m15.gremam15.ue02_quagl.R;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        try {
            Intent i = getIntent();
            initComponents(i);
        } catch (Exception e) {
            e.printStackTrace();
            Snackbar.make(findViewById(R.id.tvx1), getString(R.string.invalidInput), Snackbar.LENGTH_LONG).show();
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void initComponents(Intent i) {
        Bundle b = i.getExtras();
        final TextView tvx1 = findViewById(R.id.tvx1);
        final TextView tvx2 = findViewById(R.id.tvx2);
        final TextView tvnor = findViewById(R.id.tvnor);
        final TextView tvsummary = findViewById(R.id.tvsummary);
        final CardView cvx1 = findViewById(R.id.cvx1);
        final CardView cvx2 = findViewById(R.id.cvx2);
        final NumberFormat numberFormat = NumberFormat.getNumberInstance();

        if (b != null) {
            tvx1.setText(String.format("X1: %s", numberFormat.format(b.getDouble("x1"))));
            tvx2.setText(String.format("X2: %s", numberFormat.format(b.getDouble("x2"))));
            String s = summaryGenerator(b);
            tvsummary.setText(s);
            tvnor.setText(String.format(getString(R.string.xRealResults), b.getInt("nor")));
            if (b.getInt("nor") == 1) {
                cvx2.setVisibility(View.GONE);
            } else if (b.getInt("nor") == 0) {
                cvx1.setVisibility(View.GONE);
                cvx2.setVisibility(View.GONE);
            }
        }
    }

    private String summaryGenerator(Bundle b) {
        String s;
        if(b.getDouble("a")> 0) {
            s = String.format(Locale.GERMAN,"%.0fx²", b.getDouble("a"));
        } else {
            s = String.format(Locale.GERMAN,"%.0fx²", b.getDouble("a"));
        }
        if(b.getDouble("b")> 0) {
            s = s + String.format(Locale.GERMAN,"+%.0fx", b.getDouble("b"));
        } else {
            s = s + String.format(Locale.GERMAN,"%.0fx", b.getDouble("b"));
        }
        if(b.getDouble("c")> 0) {
            s = s + String.format(Locale.GERMAN,"+%.0f", b.getDouble("c"));
        } else {
            s = s + String.format(Locale.GERMAN,"%.0f", b.getDouble("c"));
        }
        s = s + " = 0";
        return s;
    }


}
