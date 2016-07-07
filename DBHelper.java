package proveb.gk.com.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.HashMap;

import proveb.gk.com.sqlite.modelclass.Jsonmodel;

/**
 * Created by Nehru on 28-06-2016.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBName.db";
    public static final String CONTACTS_TABLE_NAME = "contacts";
    public static final String DOCTOR_TABLE_NAME = "doctor";

    public static final String CONTACTS_COLUMN_ID = "id";
    public static final String CONTACTS_COLUMN_NAME = "name";
    public static final String CONTACTS_COLUMN_EMAIL = "email";
    public static final String CONTACTS_COLUMN_STREET = "street";
    public static final String CONTACTS_COLUMN_CITY = "place";
    public static final String CONTACTS_COLUMN_PHONE = "phone";
    public static final String DOCTOR_ID = "doc_id";
    public static final String DOCTOR_NAME = "name";
    public static final String DOCTOR_IMAGE = "image_url";
    ArrayList<Jsonmodel> jsonmodelArrayList;
    Jsonmodel jsonmodel;

    private HashMap hp;

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME , null, 4);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table contacts" +
                        "(id integer primary key, name text,phone text,email text, street text,place text)"
        );
        db.execSQL(
                "create table doctor" +
                        "(doc_id text, name text, image_url text)"
        );
        jsonmodelArrayList=new ArrayList<>();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS contacts");
        db.execSQL("DROP TABLE IF EXISTS doctor");

        onCreate(db);
    }

    public boolean insertContact  (String name, String phone, String email, String street,String place)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("phone", phone);
        contentValues.put("email", email);
        contentValues.put("street", street);
        contentValues.put("place", place);
        db.insert("contacts", null, contentValues);
        return true;
    }

    public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from contacts where id="+id+"", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, CONTACTS_TABLE_NAME);
        return numRows;
    }

    public boolean updateContact (Integer id, String name, String phone, String email, String street,String place)
    {
        SQLiteDatabase db = this.getWritableDatabase();
         ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("phone", phone);
        contentValues.put("email", email);
        contentValues.put("street", street);
        contentValues.put("place", place);
        db.update("contacts", contentValues, "id = ? ", new String[]{Integer.toString(id)});
        return true;
    }

    public Integer deleteContact (Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("contacts",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }


    public ArrayList<String> getAllCotacts()
    {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from contacts", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAME)));
            res.moveToNext();
        }
        return array_list;
    }
    /*doctor data*/
    public boolean insertDoctorDetails  (String doc_id, String name, String image_url)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor c=db.rawQuery("SELECT * FROM doctor where doc_id='" + doc_id.trim() + "'", null);
        int counter=c.getCount();
        c.close();
        if(counter>0){
            System.out.println("keys... Already Exista DOCID("+doc_id+")");
            SQLiteStatement st= db.compileStatement("update doctor set name=?,image_url=? where doc_id='"+doc_id.trim()+"'");
            st.bindString(1,name.trim());
            st.bindString(2,image_url);
            st.execute();
            st.close();
        }else {
            ContentValues contentValues = new ContentValues();
            contentValues.put("doc_id", doc_id);
            contentValues.put("name", name);
            contentValues.put("image_url", image_url);
            db.insert("doctor", null, contentValues);
        }
        db.close();
        return true;
    }
public void DBCOUNT(){
    SQLiteDatabase db = this.getWritableDatabase();

    Cursor c_total=db.rawQuery("SELECT * FROM doctor ", null);
    System.out.println("keys... TotalDBCount:"+c_total.getCount()+"");
    c_total.close();
    db.close();
}
    public Cursor getDoctorData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from doctor where doc_id="+id+"", null );
        return res;
    }

    public int numberOfRows1(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, DOCTOR_TABLE_NAME);
        return numRows;
    }

    public boolean updateDoctorData (Integer doc_id, String name, String image_url)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("doc_id", doc_id);
        contentValues.put("name", name);
        contentValues.put("image_url", image_url);

              db.update("doctor", contentValues, "doc_id = ? ", new String[] { Integer.toString(doc_id) } );
        return true;
    }

    public Integer deleteDoctorData (Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("doctor",
                "doc_id = ? ",
                new String[] { Integer.toString(id) });
    }
    public ArrayList<Jsonmodel> getAllDoctor()
    {
        ArrayList<Jsonmodel> array_list = new ArrayList<Jsonmodel>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from doctor", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            jsonmodel=new Jsonmodel();
            jsonmodel.setDoc_id(res.getString(res.getColumnIndex(DOCTOR_ID)));
            jsonmodel.setFullname(res.getString(res.getColumnIndex(DOCTOR_NAME)));
            jsonmodel.setImage_url(res.getString(res.getColumnIndex(DOCTOR_IMAGE)));

            array_list.add(jsonmodel);
            res.moveToNext();
        }
        return array_list;
    }
}
