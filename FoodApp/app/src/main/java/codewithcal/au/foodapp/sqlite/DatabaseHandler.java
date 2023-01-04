package codewithcal.au.foodapp.sqlite;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import codewithcal.au.foodapp.model.DetailBill;
import codewithcal.au.foodapp.model.User;

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 4;

    // Database Name
    private static final String DATABASE_NAME = "foodsManager";

    private static final String TABLE_DETAIL_BILLS = "detailfoods";
    private static final String TABLE_USER = "user";
    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_QUANTITY = "quantity";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_DETAIL_BILL_TABLE = "CREATE TABLE " + TABLE_DETAIL_BILLS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_QUANTITY + " TEXT" + ")";
        db.execSQL(CREATE_DETAIL_BILL_TABLE);

        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_USERNAME + " TEXT,"
                + KEY_EMAIL + " TEXT," + KEY_PASSWORD + " TEXT" + ")";
        db.execSQL(CREATE_USER_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DETAIL_BILLS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        // Create tables again
        onCreate(db);
    }

    public void dropTableDetailBill() {
        // Drop older table if existed
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DETAIL_BILLS);
        // Create tables again
        onCreate(db);
    }
    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    public void addDetailBill(DetailBill detailBill) throws Exception {
        SQLiteDatabase db = null;
        try {
            //mo ket noi
            db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(KEY_ID, detailBill.getId());
            values.put(KEY_NAME, detailBill.getName());
            values.put(KEY_QUANTITY, detailBill.getQuantity());

            long id = db.insert(TABLE_DETAIL_BILLS, "", values);

        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {
            db.close(); // Closing database connection
        };
    }

    public DetailBill getDetailBill(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_DETAIL_BILLS, new String[]{KEY_ID,
                        KEY_NAME, KEY_QUANTITY}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        DetailBill detailBill = new DetailBill(cursor.getInt(0),
                cursor.getString(1),
                cursor.getInt(2));
        // return contact
        return detailBill;
    }

    public ArrayList<DetailBill> getAllDetailBills() {
        ArrayList<DetailBill> detailBillList = new ArrayList<DetailBill>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_DETAIL_BILLS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
//nhấn F8 di chuyển bước tiếp theo
        //Nhấn F9 chạy sang break point tiếp theo hoặc chạy hết chương trình
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                DetailBill detailBill = new DetailBill();
                detailBill.setId(cursor.getInt(0));
                detailBill.setName(cursor.getString(1));
                detailBill.setQuantity(cursor.getInt(2));
                // Adding contact to list
                detailBillList.add(detailBill);
            } while (cursor.moveToNext());
        }

        // return food list
        return detailBillList;
    }
    // Updating single contact
    public int updateDetailBill(DetailBill detailBill) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, detailBill.getName());
        values.put(KEY_QUANTITY, detailBill.getQuantity());

        // updating row
        return db.update(TABLE_DETAIL_BILLS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(detailBill.getId())});
    }

    public void deleteDetailBill(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DETAIL_BILLS, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }
    // Getting contacts Count
    public int getDetailBillCount() {
        String countQuery = "SELECT  * FROM " + TABLE_DETAIL_BILLS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }

    public void saveUser(User user) throws Exception {
        SQLiteDatabase db = null;
        try {
            //mo ket noiss
            db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(KEY_ID, user.getId());
            values.put(KEY_USERNAME, user.getUsername());
            values.put(KEY_EMAIL, user.getEmail());
            values.put(KEY_PASSWORD, user.getPassword());

            long id = db.insert(TABLE_USER, "", values);

        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {
            db.close(); // Closing database connection
        };
    }

    public void deleteUser(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    public ArrayList<User>  getUser() {
        ArrayList<User> profile = new ArrayList<User>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_USER;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(cursor.getInt(0));
                user.setUsername(cursor.getString(1));
                user.setEmail(cursor.getString(2));
                user.setPassword(cursor.getString(3));
                // Adding contact to list
                profile.add(user);
            } while (cursor.moveToNext());
        }

        // return food list
        return profile;
    }
}
