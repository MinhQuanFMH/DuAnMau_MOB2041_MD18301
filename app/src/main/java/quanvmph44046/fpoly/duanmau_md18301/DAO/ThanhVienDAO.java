package quanvmph44046.fpoly.duanmau_md18301.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import quanvmph44046.fpoly.duanmau_md18301.Databases.DbHelper;
import quanvmph44046.fpoly.duanmau_md18301.Model.Sach;
import quanvmph44046.fpoly.duanmau_md18301.Model.ThanhVien;

public class ThanhVienDAO {
    DbHelper dbHelper;

    public ThanhVienDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public ArrayList<ThanhVien> getDSThanhVien() {
        ArrayList<ThanhVien> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM THANHVIEN", null);
        if(cursor.getCount() != 0){
            cursor.moveToFirst(); // Đưa con trỏ lên đầu danh sách
            do{
                list.add(new ThanhVien(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2)));
            }while (cursor.moveToNext());
        }
        return list;
    }
    //
    public boolean themThanhVien(String hoten, String namsinh){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("hoten", hoten);
        contentValues.put("namsinh", namsinh);
        long check = sqLiteDatabase.insert("THANHVIEN", null, contentValues);
        if(check == -1)
            return false;
        return true;
    }

    public boolean capNhatThanhVien(int matv, String hoten, String namsinh) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("hoten", hoten);
        contentValues.put("namsinh", namsinh);
        long check = sqLiteDatabase.update("THANHVIEN", contentValues, "matv = ?",
                new String[]{String.valueOf(matv)});
        if(check == -1)
            return false;
        return true;
    }

    // 1-xoa thanh cong, 0-xoa that bai, -1-tim thay thanh vien dang co phieu muon
    public int xoaThongTinThanhVien(int matv){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM PHIEUMUON WHERE matv = ?",
                new String[]{String.valueOf(matv)});
        if(cursor.getCount() != 0){
            return -1;
        }

        long check = sqLiteDatabase.delete("THANHVIEN", "matv = ?",
                new String[]{String.valueOf(matv)});
        if(check == -1)
            return 0;// xoa that bai
        return 1;// xoa thanh cong
    }

}
