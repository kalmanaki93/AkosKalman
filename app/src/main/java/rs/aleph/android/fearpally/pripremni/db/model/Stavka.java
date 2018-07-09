package rs.aleph.android.fearpally.pripremni.db.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by milossimic on 11/17/16.
 */

@DatabaseTable(tableName = Stavka.TABLE_NAME_USERS)
public class Stavka extends Prijava {

    public static final String TABLE_NAME_USERS = "stavke";
    public static final String FIELD_NAME_ID     = "id";
    public static final String TABLE_STAVKA_NASLOV = "naslov";
    public static final String TABLE_STAVKA_OPIS = "opis";
    public static final String TABLE_STAVKA_OZBILJNOST = "ozbiljnost";
    public static final String TABLE_STAVKA_KOMENTAR = "komentar";
    public static final String FIELD_NAME_DATUM = "datum";
    public static final String TABLE_STAVKA_SLIKA = "slika";
    public static final String FIELD_NAME_USER  = "user";


    @DatabaseField(columnName = FIELD_NAME_ID, generatedId = true)
    private int mId;

    @DatabaseField(columnName = TABLE_STAVKA_NASLOV)
    private String mNaslov;

    @DatabaseField(columnName = TABLE_STAVKA_OPIS)
    private String mOpis;

    @DatabaseField(columnName = TABLE_STAVKA_OZBILJNOST)
    private Float mOzbiljnost;

    @DatabaseField(columnName = TABLE_STAVKA_KOMENTAR)
    private String mKomentar;

    @DatabaseField(columnName = FIELD_NAME_DATUM)
    private String mDatum;

    @DatabaseField(columnName = TABLE_STAVKA_SLIKA)
    private String mSlika;



    @DatabaseField(columnName = FIELD_NAME_USER, foreign = true, foreignAutoRefresh = true)
    private Prijava mUser;

    public Stavka() {
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmNaslov() {

        return mNaslov;
    }

    public void setmNaslov(String mNaslov) {
        this.mNaslov = mNaslov;
    }

    public String getmOpis() {
        return mOpis;
    }

    public void setmOpis(String mOpis) {
        this.mOpis = mOpis;
    }

    public String getmKomentar() {
        return mKomentar;
    }

    public void setmKomentar(String mKomentar){
        this.mKomentar=mKomentar;
    }

    public Float getmOzbiljnost() {
        return mOzbiljnost;
    }

    public void setmOzbiljnost(Float mOzbiljnost){
        this.mOzbiljnost=mOzbiljnost;
    }

    public String getmDatum() {
        return mDatum;
    }

    public void setmDatum(String mDatum){
        this.mDatum=mDatum;
    }

    public String getmSlika(){
        return mSlika;
    }

    public void setmSlika(String mSlika) {
        this.mSlika = mSlika;
    }

    public static String getFieldNameUser() {
        return FIELD_NAME_USER;
    }

    public Prijava getmUser() {
        return mUser;
    }

    public void setmUser(Prijava mUser) {
        this.mUser = mUser;
    }



    @Override
    public String toString() {
        return mNaslov;
    }
}
