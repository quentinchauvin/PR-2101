package pr_2101.josselin.extruder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Josselin on 26/04/18.
 * oui.
 */

public class DatabaseHelper extends SQLiteOpenHelper{


    private static final String TABLE_LIVRES = "table_livres";
    private static final String COL_ID = "ID";
    private static final String COL_2 = "DATE";
    private static final String COL_3 = "TEMPE";
    private static final String COL_4 = "DIST";
    private static final String COL_5 = "GAS";

    private static final String CREATE_BDD = "CREATE TABLE " + TABLE_LIVRES + " ("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_2 + " TEXT NOT NULL, "
            + COL_3 + " TEXT NOT NULL, "
            + COL_4 + " TEXT NOT NULL, "
            + COL_5 + " TEXT NOT NULL);";

    public DatabaseHelper(Context context) {
        super(context, "PD", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BDD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //On peut faire ce qu'on veut ici moi j'ai décidé de supprimer la table et de la recréer
        //comme ça lorsque je change la version les id repartent de 0
        db.execSQL("DROP TABLE " + TABLE_LIVRES + ";");
        onCreate(db);
    }

    public boolean insertData(final FeedBackScheduled _f){

        SQLiteDatabase bdd = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COL_2, _f.getDate());
        values.put(COL_3, _f.getTemp());
        values.put(COL_4, _f.getDist());
        values.put(COL_5, _f.getGas());

        long a =  bdd.insert(TABLE_LIVRES, null, values);

        bdd.close();

        getFormattedData();

        return (a!=-1);
    }

    private Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("Select * from " + TABLE_LIVRES, null);
    }

    public ArrayList<FeedBackScheduled> getFormattedData(){
        ArrayList<FeedBackScheduled> list = new ArrayList<>();
        Cursor res = getAllData();
        if (res != null && res.getCount()>0){
            while (res.moveToNext()){
                list.add(new FeedBackScheduled(res));
                //Log.e("oui", new FeedBackScheduled(res).toString());
            }
        }
        return list;
    }

}