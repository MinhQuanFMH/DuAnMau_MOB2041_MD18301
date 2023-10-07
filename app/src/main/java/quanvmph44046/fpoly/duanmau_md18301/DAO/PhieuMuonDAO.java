package quanvmph44046.fpoly.duanmau_md18301.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import quanvmph44046.fpoly.duanmau_md18301.Databases.DbHelper;
import quanvmph44046.fpoly.duanmau_md18301.Model.PhieuMuon;
import quanvmph44046.fpoly.duanmau_md18301.Model.Sach;

public class PhieuMuonDAO {
    DbHelper dbHelper;
    public PhieuMuonDAO(Context context) {
        dbHelper = new DbHelper(context);
    }
    public ArrayList<PhieuMuon> getDSPhieuMuon() {
        ArrayList<PhieuMuon> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT pm.mapm, pm.matv, tv.hoten, pm.matt, tt.hoten, pm.masach, sc.tensach, pm.ngay, pm.trasach, pm.tienthue" +
                " FROM PHIEUMUON pm, THANHVIEN tv, THUTHU tt, SACH sc" +
                " WHERE pm.matv = tv.matv" +
                " AND pm.matt = tt.matt" +
                " AND pm.masach = sc.masach" +
                " ORDER BY pm.mapm DESC", null);
        if(cursor.getCount() != 0){
            cursor.moveToFirst(); // Đưa con trỏ lên đầu danh sách
            do{
                list.add(new PhieuMuon(cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getInt(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getInt(8),
                        cursor.getInt(9)));
            }while (cursor.moveToNext());
        }

        return list;
    }

    // Thay đổi trạng thái trả sách
    public boolean thayDoiTrangThai(int mapm) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("trasach", 1);
        long check = sqLiteDatabase.update("PHIEUMUON", contentValues, "mapm = ?",
                new String[]{String.valueOf(mapm)});
        if(check == -1){
            return false;
        } else {
            return true;
        }
    }

    // Thêm phiếu mượn
    public boolean themPhieuMuon(PhieuMuon phieuMuon) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("matv", phieuMuon.getMatv());
        contentValues.put("matt", phieuMuon.getMatt());
        contentValues.put("masach", phieuMuon.getMasach());
        contentValues.put("ngay", phieuMuon.getNgay());
        contentValues.put("trasach", phieuMuon.getTrasach());
        contentValues.put("tienthue", phieuMuon.getTienthue());

        long check = sqLiteDatabase.insert("PHIEUMUON", null, contentValues);
        if(check == -1) {
            return false;
        } else {
            return true;
        }

    }
}
