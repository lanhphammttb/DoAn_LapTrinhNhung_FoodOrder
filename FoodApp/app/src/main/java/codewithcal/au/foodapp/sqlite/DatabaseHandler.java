package codewithcal.au.foodapp.sqlite;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import codewithcal.au.foodapp.model.Food;

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "foodsManager";

    // Contacts table name
    private static final String TABLE_FOODS = "foods";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PRICE = "price";
    private static final String KEY_TYPE = "type";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_FOODS_TABLE = "CREATE TABLE " + TABLE_FOODS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_PRICE + " TEXT," + KEY_TYPE + " TEXT" + ")";
        //khoi tao du lieu
        //thuc thi các câu lẹnh insert....
        db.execSQL(CREATE_FOODS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOODS);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new contact
   public long addFood(Food food) throws Exception {
        SQLiteDatabase db = null;
        long id;
        try {
            //mo ket noi
            db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(KEY_NAME, food.getName()); // Contact Name
            values.put(KEY_PRICE, food.getPrice()); // Contact Phone
            values.put(KEY_TYPE, food.getType()); // Contact Phone

            // Inserting Row, tra ve id tu tang
            id = db.insert(TABLE_FOODS, "", values);

        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {
            db.close(); // Closing database connection
        }
        return id;
    }

    // Getting single contact
    Food getFood(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_FOODS, new String[]{KEY_ID,
                        KEY_NAME, KEY_PRICE, KEY_TYPE}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Food food = new Food(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3));
        // return contact
        return food;
    }

    // Getting All Contacts
    public ArrayList<Food> getAllFoods() {
        ArrayList<Food> foodList = new ArrayList<Food>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_FOODS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
//nhấn F8 di chuyển bước tiếp theo
        //Nhấn F9 chạy sang break point tiếp theo hoặc chạy hết chương trình
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Food food = new Food();
                food.setId(Integer.parseInt(cursor.getString(0)));
                food.setName(cursor.getString(1));
                food.setPrice(cursor.getString(2));
                food.setType(cursor.getString(3));
                // Adding contact to list
                foodList.add(food);
            } while (cursor.moveToNext());
        }

        // return food list
        return foodList;
    }

    // Updating single contact
    public int updateContact(Food food) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, food.getName());
        values.put(KEY_PRICE, food.getName());
        values.put(KEY_TYPE, food.getName());

        // updating row
        return db.update(TABLE_FOODS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(food.getId())});
    }

    // Deleting single contact
    public void deleteContact(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FOODS, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
        //DELETE FROM Contact  WHERE id  = 123;
//        db.execSQL("DELETE FROM Contact  WHERE id  = "+id);
        db.close();
    }

    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_FOODS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

}
