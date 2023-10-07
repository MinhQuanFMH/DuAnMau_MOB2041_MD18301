package quanvmph44046.fpoly.duanmau_md18301.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;

import quanvmph44046.fpoly.duanmau_md18301.Databases.DbHelper;
import quanvmph44046.fpoly.duanmau_md18301.Model.LoaiSach;
import quanvmph44046.fpoly.duanmau_md18301.Model.Sach;

public class LoaiSachDAO {
    DbHelper dbHelper;
    public LoaiSachDAO(Context context){
        dbHelper = new DbHelper(context);
    }
    // Lấy danh sách loại sách
    public ArrayList<LoaiSach> getDSLoaiSach() {
        ArrayList<LoaiSach> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM LOAISACH", null);
        if(cursor.getCount() != 0){
            cursor.moveToFirst(); // Đưa con trỏ lên đầu danh sách
            do{
                list.add(new LoaiSach(cursor.getInt(0),
                        cursor.getString(1)));
            }while (cursor.moveToNext());
        }

        return list;
    }
    // Thêm loại sách
    public boolean themLoaiSach(String tenloai) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenloai", tenloai);
        long check = sqLiteDatabase.insert("LOAISACH", null, contentValues);
        if(check == -1){
            return false;
        } else {
            return true;
        }
    }

    // Xoá loại sách
    // 1- xoá thành công, 0- xoá thất bại, -1- có sách tồn tại trong thể loại
    public int xoaLoaiSach(int maloai) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM SACH WHERE maloai = ?",
                new String[]{String.valueOf(maloai)});
        if(cursor.getCount() != 0) {
            return -1;
        }

        long check = sqLiteDatabase.delete("LOAISACH", "maloai = ?",
                new String[]{String.valueOf(maloai)});
        if(check == -1){
            return 0;
        } else {
            return 1;
        }
    }
    // Sửa loại sách
    public boolean suaLoaiSach(LoaiSach loaiSach) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenloai", loaiSach.getTenloai());
        long check = sqLiteDatabase.update("LOAISACH", contentValues, "maloai = ?",
                new String[]{String.valueOf(loaiSach.getMaloai())});
        if(check == -1)
            return false;
        return true;

    }
}

