package rs.aleph.android.fearpally.pripremni.db.model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by milossimic on 11/17/16.
 */

@DatabaseTable(tableName = Prijava.TABLE_NAME_USERS)
public class Prijava {

    public static final String TABLE_NAME_USERS = "anonim";
    public static final String FIELD_NAME_ID     = "id";
    public static final String TABLE_PRIJAVA_NAZIV = "naziv";
    public static final String TABLE_PRIJAVA_OPIS = "opis";
    public static final String TABLE_PRIJAVA_DATUM = "datum";
    public static final String TABLE_STAVKA_STAVKA = "stavkas";

    @DatabaseField(columnName = FIELD_NAME_ID, generatedId = true)
    private int mId;

    @DatabaseField(columnName = TABLE_PRIJAVA_NAZIV)
    private String mNaziv;

    @DatabaseField(columnName = TABLE_PRIJAVA_OPIS)
    private String mOpis;


    @DatabaseField(columnName = TABLE_PRIJAVA_DATUM)
    private String mDatum;

    @ForeignCollectionField(columnName = Prijava.TABLE_STAVKA_STAVKA, eager = true)
    private ForeignCollection<Stavka> stavkas;

    public Prijava() {
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmNaziv() {
        return mNaziv;
    }

    public void setmNaziv(String mNaziv) {
        this.mNaziv = mNaziv;
    }



    public String getmOpis() {
        return mOpis;
    }

    public void setmOpis(String mOpis) {
        this.mOpis = mOpis;
    }


    public String getmDatum() {
        return mDatum;
    }

    public void setmDatum(String mDatum) {
        this.mDatum = mDatum;
    }

//    public String getStavkas() {
//        return stavkas;
//    }
//
//    public void setStavkas(String stavkas) {
//        this.stavkas = stavkas;
//    }


    public ForeignCollection<Stavka> getStavkas() {
        return stavkas;
    }

    public void setStavkas(ForeignCollection<Stavka> stavkas) {
        this.stavkas = stavkas;
    }
    public void setStavkas(String s) {

    }

    @Override
    public String toString() {
        return mNaziv;
    }


}
