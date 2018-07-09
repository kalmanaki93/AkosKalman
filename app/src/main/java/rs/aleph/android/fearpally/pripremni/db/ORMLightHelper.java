package rs.aleph.android.fearpally.pripremni.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

//import rs.aleph.android.fearpally.db.model.Product;
import rs.aleph.android.fearpally.pripremni.db.model.Prijava;
import rs.aleph.android.fearpally.pripremni.db.model.Stavka;

/**
 * Created by milossimic on 11/17/16.
 */

public class ORMLightHelper extends OrmLiteSqliteOpenHelper{

    private static final String DATABASE_NAME    = "db";
    private static final int    DATABASE_VERSION = 1;

    private Dao<Stavka, Integer> mPrijavaDao = null;
    private Dao<Prijava, Integer> mStavkaDao = null;

    public ORMLightHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Stavka.class);
            TableUtils.createTable(connectionSource, Prijava.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Stavka.class, true);
            TableUtils.dropTable(connectionSource, Prijava.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Dao<Stavka, Integer> getPrijavaDao() throws SQLException {
        if (mPrijavaDao == null) {
            mPrijavaDao = getDao(Stavka.class);
        }

        return mPrijavaDao;
    }

    public Dao<Prijava, Integer> getmStavkaDao() throws SQLException {
        if (mStavkaDao == null) {
            mStavkaDao = getDao(Prijava.class);
        }

        return mStavkaDao;
    }

    //obavezno prilikom zatvarnaj rada sa bazom osloboditi resurse
    @Override
    public void close() {
        mPrijavaDao = null;
        mStavkaDao = null;

        super.close();
    }
}
