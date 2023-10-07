package quanvmph44046.fpoly.duanmau_md18301.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import quanvmph44046.fpoly.duanmau_md18301.Databases.DbHelper;

public class ThuThuDAO {
    DbHelper dbHelper;
    SQLiteDatabase sqLiteDatabase;
    public ThuThuDAO(Context context) {
      dbHelper = new DbHelper(context);
    }
    // Đăng nhập
    public boolean checkDangNhap (String matt, String matkhau) {
        sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM THUTHU WHERE matt = ? AND matkhau = ?",
                new String[]{matt, matkhau});
        if(cursor.getCount() != 0){
            return true;
        } else {
            return false;
        }
    }

    public int capNhatMatKhau(String username, String oldPass, String newPass) {
        sqLiteDatabase = dbHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM THUTHU WHERE matt = ? AND matkhau = ?",
                new String[]{username, oldPass});
        if(cursor.getCount() > 0) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("matkhau", newPass);
            long check = sqLiteDatabase.update("THUTHU", contentValues, "matt = ?",
                    new String[]{username});
            if(check == -1){
                return -1;
            } else {
                return 1;
            }
        }
        return 0;
    }


}
