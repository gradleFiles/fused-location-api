package proveb.gk.com.login;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "REG.db";
    public static final String REGISTER_TABLE_NAME = "register";
    public static final String REGISTER_COLUMN_USERNAME = "name";
    public static final String REGISTER_COLUMN_PASSWORD = "password";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 4);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table register" +
                        "(name text,password text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS register");

        onCreate(db);
    }

    public boolean insertRegister(String name, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("password", password);

        db.insert("register", null, contentValues);
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from register where id=" + id + "", null);
        return res;
    }

    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, REGISTER_TABLE_NAME);
        return numRows;
    }

    public boolean updateregister(Integer id, String name, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("password", password);
        db.update("register", contentValues, "id = ? ", new String[]{Integer.toString(id)});
        return true;
    }

    public Integer deleteregister(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("register",
                "id = ? ",
                new String[]{Integer.toString(id)});
    }

    public ArrayList<String> getAllregister() {
        ArrayList<String> array_list = new ArrayList<String>();
        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from register", null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            array_list.add(res.getString(res.getColumnIndex(REGISTER_COLUMN_USERNAME)) + "," + res.getString(res.getColumnIndex(REGISTER_COLUMN_PASSWORD)));
            res.moveToNext();
        }
        return array_list;
    }


}
