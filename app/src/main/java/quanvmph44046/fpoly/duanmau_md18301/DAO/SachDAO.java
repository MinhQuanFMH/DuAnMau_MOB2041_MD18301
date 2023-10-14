package quanvmph44046.fpoly.duanmau_md18301.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import quanvmph44046.fpoly.duanmau_md18301.Databases.DbHelper;
import quanvmph44046.fpoly.duanmau_md18301.Model.Sach;

public class SachDAO {
    DbHelper dbHelper;
    SQLiteDatabase sqLiteDatabase;
    public SachDAO(Context context) {
       dbHelper = new DbHelper(context);
    }
    // Viết các hàm để thực hiện các chức năng

    // Lấy toàn bộ sách có trong thư viện
    public ArrayList<Sach> getDSDauSach() {
        ArrayList<Sach> list = new ArrayList<>();
        sqLiteDatabase = dbHelper.getReadableDatabase(); // Đọc database
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT sc.masach, sc.tensach, sc.giathue, sc.maloai, ls.tenloai, sc.namxb" +
                " FROM SACH sc, LOAISACH ls" +
                " WHERE sc.maloai = ls.maloai", null);

        if(cursor.getCount() != 0){
            cursor.moveToFirst(); // Đưa con trỏ lên đầu danh sách
            do{
                list.add(new Sach(cursor.getInt(0), // Mã sách
                        cursor.getString(1), // Tên sách
                        cursor.getInt(2), // Tiền thuê
                        cursor.getInt(3),// Mã loại
                        cursor.getString(4),// Tên loại
                        cursor.getInt(5))); // Năm xuất bản
            }while (cursor.moveToNext());
        }
        return list;
    }

    public boolean themSachMoi(String tensach, int giathue, int maloai, int namxb){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tensach", tensach);
        contentValues.put("giathue", giathue);
        contentValues.put("maloai", maloai);
        contentValues.put("namxb", namxb);
        long check = database.insert("SACH", null, contentValues);
        if(check == -1)
            return false;
        return true;
    }

    public boolean capNhatSach(int masach, String tensach, int giathue, int maloai, int namxb) {
        SQLiteDatabase LiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tensach", tensach);
        contentValues.put("giathue", giathue);
        contentValues.put("maloai", maloai);
        contentValues.put("namxb", namxb);

        long check = LiteDatabase.update("SACH", contentValues, "masach = ?",
                new String[]{String.valueOf(masach)});
        if(check == -1)
            return false;
        return true;
    }

    // 1.xoa thanh cong, 0.xoa that bai, -1.sach co trong phieu muon khong duoc xoa
    public int xoaSach(int masach) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM PHIEUMUON WHERE masach = ?",
                new String[]{String.valueOf(masach)});
        if(cursor.getCount() != 0){
            return -1;// khong duoc phep xoa
        }
        long check = database.delete("SACH", "masach = ?", new String[]{String.valueOf(masach)});
        if(check == -1)
            return 0;
        return 1;
    }

}
