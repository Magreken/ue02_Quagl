package at.htlkaindorf.m15.gremam15.ue02_quagl.gui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import at.htlkaindorf.m15.gremam15.ue02_quagl.R;

public class RecyclerResultActivity extends AppCompatActivity {

    private RecyclerView recyclerView = null;
    private RecyclerView.LayoutManager layoutManager;
    final NumberFormat numberFormat = NumberFormat.getNumberInstance();

    public static class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

        private final List<TextAndImage> textAndImages = new ArrayList<>();

        public void add(CharSequence text, Drawable image) {
            textAndImages.add(new TextAndImage(text, image));
        }

        public void add(CharSequence text) {
            textAndImages.add(new TextAndImage(text));
        }

        static class TextAndImage {
            private final CharSequence text;
            private final Drawable image;

            TextAndImage(CharSequence text, Drawable image) {
                this.text = text;
                this.image = image;
            }

            public TextAndImage(CharSequence text) {
                this(text, null);
            }

            public CharSequence getText() {
                return text;
            }

            public Drawable getImage() {
                return image;
            }
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            final CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card, parent, false);
            return new MyViewHolder(cardView);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            final TextAndImage textAndImage = textAndImages.get(position);
            holder.bindText(textAndImage.getText());
            final Drawable drawable = textAndImage.getImage();
            holder.bindImage(drawable);
        }

        @Override
        public int getItemCount() {
            return textAndImages.size();
        }

        public static class MyViewHolder extends RecyclerView.ViewHolder {

            private final CardView cardView;
            private final TextView textView;
            private final ImageView imageView;

            public MyViewHolder(@NonNull CardView itemView) {
                super(itemView);
                cardView = itemView;
                textView = cardView.findViewById(R.id.cardText);
                imageView = cardView.findViewById(R.id.cardImage);
            }

            public void bindText(CharSequence text) {
                textView.setText(text);
            }

            public void bindImage(Drawable drawable) {
                imageView.setImageDrawable(drawable);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        recyclerView = new RecyclerView(this);
        layoutManager = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE
                ? new GridLayoutManager(this, 2) : new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        final MyAdapter adapter;
        recyclerView.setAdapter(adapter = new MyAdapter());
        Intent i = getIntent();
        initComponents(i, adapter);

        setContentView(recyclerView);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void initComponents(Intent i, MyAdapter adapter) {
        Bundle b = i.getExtras();

        if (b != null) {
            String s = summaryGenerator(b);
            adapter.add(s, getDrawable(R.drawable.equals));
            if (b.getInt("nor") == 1) {
                adapter.add(String.format("X1: %s", numberFormat.format(b.getDouble("x1"))), getDrawable(R.drawable.calculator));
                adapter.add(String.format(getString(R.string.xRealResults), b.getInt("nor")), getDrawable(R.drawable.info));
            } else if (b.getInt("nor") == 0) {
                adapter.add("There is no real solution");
            } else {
                if (b.getBoolean("isC")) {
                    createComplexSolution(b, adapter);
                    adapter.add(String.format(getString(R.string.xComplexResults), b.getInt("nor")), getDrawable(R.drawable.info));
                } else {
                    adapter.add(String.format("X1: %s", numberFormat.format(b.getDouble("x1"))), getDrawable(R.drawable.calculator));
                    adapter.add(String.format("X2: %s", numberFormat.format(b.getDouble("x2"))), getDrawable(R.drawable.calculator));
                    adapter.add(String.format(getString(R.string.xRealResults), b.getInt("nor")), getDrawable(R.drawable.info));
                }
            }
        }
    }

    private String summaryGenerator(Bundle b) {
        String s;
        s = String.format(Locale.GERMAN, "%.0fx²", b.getDouble("a"));

        if (b.getDouble("b") >= 0) {
            s = s + String.format(Locale.GERMAN, "+%.0fx", b.getDouble("b"));
        } else {
            s = s + String.format(Locale.GERMAN, "%.0fx", b.getDouble("b"));
        }
        if (b.getDouble("c") >= 0) {
            s = s + String.format(Locale.GERMAN, "+%.0f", b.getDouble("c"));
        } else {
            s = s + String.format(Locale.GERMAN, "%.0f", b.getDouble("c"));
        }
        s = s + " = 0";
        return s;
    }

    private void createComplexSolution(Bundle bundle, MyAdapter adapter) {
        String sp, sm;
        double b = bundle.getDouble("b");
        b *= (-1);
        sp = String.format("X1: %s + %s * i", numberFormat.format(b), numberFormat.format(bundle.getDouble("imag")));
        sm = String.format("X2: %s - %s * i", numberFormat.format(b), numberFormat.format(bundle.getDouble("imag")));
        adapter.add(sp, getDrawable(R.drawable.dino));
        adapter.add(sm, getDrawable(R.drawable.dino));
    }

}
