package rs.aleph.android.fearpally.pripremni.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;
import java.util.List;


import rs.aleph.android.fearpally.R;
import rs.aleph.android.fearpally.pripremni.db.ORMLightHelper;
import rs.aleph.android.fearpally.pripremni.db.model.Prijava;
import rs.aleph.android.fearpally.pripremni.db.model.Stavka;

import static rs.aleph.android.fearpally.pripremni.activity.ListActivity.NOTIF_STATUS;
import static rs.aleph.android.fearpally.pripremni.activity.ListActivity.NOTIF_TOAST;

public class Detail extends AppCompatActivity {

    private ORMLightHelper databaseHelper;
    private SharedPreferences prefs;
    private Prijava p;

    private EditText naziv;
    private EditText opis;
    private EditText datum;
    private EditText stavkas;



    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        if(toolbar != null) {
            setSupportActionBar(toolbar);
        }

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        int key = getIntent().getExtras().getInt(ListActivity.ACTOR_KEY);

        try {
            p = getDatabaseHelper().getmStavkaDao().queryForId(key);

            naziv = (EditText) findViewById(R.id.prijava_naziv);
            opis = (EditText) findViewById(R.id.prijava_opis);
            datum = (EditText) findViewById(R.id.prijava_datum);
            stavkas = (EditText) findViewById(R.id.prijava_stavka);


            naziv.setText(p.getmNaziv());
            opis.setText(p.getmOpis());
            datum.setText(p.getmDatum());
            //stavkas.setText(p.getStavkas());

        } catch (SQLException e) {
            e.printStackTrace();
        }

        final ListView listView = (ListView) findViewById(R.id.prijava_stavka);

        try {
            List<Stavka> list = getDatabaseHelper().getPrijavaDao().queryBuilder()
                    .where()
                    .eq(Stavka.FIELD_NAME_USER, p.getmId())
                    .query();

            ListAdapter adapter = new ArrayAdapter<>(this, R.layout.list_item, list);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Stavka s = (Stavka) listView.getItemAtPosition(position);
                    Toast.makeText(Detail.this, s.getmNaslov()+" "+s.getmOpis()+" "+s.getmOzbiljnost()+" "+s.getmKomentar()+" "+s.getmDatum()+" "+s.getmSlika(), Toast.LENGTH_SHORT).show();

                }
            });

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void refresh() {
        ListView listview = (ListView) findViewById(R.id.prijava_stavka);

        if (listview != null){
            ArrayAdapter<Stavka> adapter = (ArrayAdapter<Stavka>) listview.getAdapter();

            if(adapter!= null)
            {
                try {
                    adapter.clear();
                    List<Stavka> list = getDatabaseHelper().getPrijavaDao().queryBuilder()
                            .where()
                            .eq(Stavka.FIELD_NAME_USER, p.getmId())
                            .query();

                    adapter.addAll(list);

                    adapter.notifyDataSetChanged();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void showStatusMesage(String message){
        NotificationManager mNotificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.drawable.ic_launcher);
        mBuilder.setContentTitle("Pripremni test");
        mBuilder.setContentText(message);

        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_add);

        mBuilder.setLargeIcon(bm);
        // notificationID allows you to update the notification later on.
        mNotificationManager.notify(1, mBuilder.build());
    }

    private void showMessage(String message){
        //provera podesenja
        boolean toast = prefs.getBoolean(NOTIF_TOAST, false);
        boolean status = prefs.getBoolean(NOTIF_STATUS, false);

        if (toast){
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }

        if (status){
            showStatusMesage(message);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add:
                //OTVORI SE DIALOG UNESETE INFORMACIJE
                final Dialog dialog = new Dialog(this);
                dialog.setContentView(R.layout.priprema_add_movie);
                dialog.setCanceledOnTouchOutside(false); //ovo je novo

                Button add = (Button) dialog.findViewById(R.id.add_movie);
                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText naslov = (EditText) dialog.findViewById(R.id.stavka_naslov);
                        EditText opis = (EditText) dialog.findViewById(R.id.stavka_opis);
                        RatingBar ozbiljnost = (RatingBar) dialog.findViewById(R.id.stavka_ozbiljnost);
                        EditText komentar = (EditText) dialog.findViewById(R.id.stavka_komentar);
                        EditText datum = (EditText) dialog.findViewById(R.id.stavka_datum);

                        Stavka s = new Stavka();
                        s.setmNaslov(naslov.getText().toString());
                        s.setmOpis(opis.getText().toString());
                        s.setmOzbiljnost(ozbiljnost.getRating());
                        s.setmKomentar(komentar.getText().toString());
                        s.setmDatum(datum.getText().toString());
                        s.setmUser(p);

                        try {
                            getDatabaseHelper().getmStavkaDao().create(s);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        //URADITI REFRESH
                        refresh();

                        showMessage("New stavka added to prijava");

                        dialog.dismiss();
                    }
                });

                dialog.show();

                break;
            case R.id.edit:
//                //POKUPITE INFORMACIJE SA EDIT POLJA
//                a.setmName(name.getText().toString());
//                a.setmBirth(birth.getText().toString());
//                a.setmBiography(bio.getText().toString());
//                a.setmScore(rating.getRating());
//
//                try {
//                    getDatabaseHelper().getActorDao().update(a);
//
//                    showMessage("Prijava detail updated");
//
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
                p.setmNaziv(naziv.getText().toString());
                p.setmOpis(opis.getText().toString());
                p.setmDatum(datum.getText().toString());
                p.setStavkas(stavkas.getText().toString());


                try {
                    getDatabaseHelper().getPrijavaDao().update((Stavka) p);

                    showMessage("Prijava detail updated");

                } catch (SQLException e) {
                    e.printStackTrace();
                }

                break;
            case R.id.remove:
                try {
                    getDatabaseHelper().getPrijavaDao().delete((Stavka) p);

                    showMessage("Prijava deleted");

                    finish(); //moramo pozvati da bi se vratili na prethodnu aktivnost
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    public ORMLightHelper getDatabaseHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this, ORMLightHelper.class);
        }
        return databaseHelper;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();


        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }
}

