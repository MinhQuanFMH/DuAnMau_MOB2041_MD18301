package quanvmph44046.fpoly.duanmau_md18301.Databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "QUANLYTHUVIEN";
    public static final int DB_VERSION = 3;
    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng loại sách
    String tableLoaiSach = "CREATE TABLE LOAISACH(maloai INTEGER PRIMARY KEY AUTOINCREMENT, tenloai TEXT)";
    db.execSQL(tableLoaiSach);
        // Tạo bảng sách
    String tableSach = "CREATE TABLE SACH(masach INTEGER PRIMARY KEY AUTOINCREMENT," +
            " tensach TEXT," +
            " giathue INTEGER," +
            " namxb INTEGER," +
            " maloai INTEGER REFERENCES lOAISACH(maloai))";
    db.execSQL(tableSach);
        // Tạo bảng thủ thư
     String tableThuThu = "CREATE TABLE THUTHU(matt TEXT PRIMARY KEY," +
             " hoten TEXT," +
             " matkhau TEXT)";
     db.execSQL(tableThuThu);
        // Tạo bảng thành viên
     String tableThanhVien = "CREATE TABLE THANHVIEN(matv INTEGER PRIMARY KEY AUTOINCREMENT," +
             " hoten TEXT," +
             " namsinh TEXT)";
     db.execSQL(tableThanhVien);
        // Tạo bảng phiếu mượn
     String tablePhieuMuon = "CREATE TABLE PHIEUMUON(mapm INTEGER PRIMARY KEY AUTOINCREMENT," +
             " matv INTEGER REFERENCES THANHVIEN(matv)," +
             " matt TEXT REFERENCES THUTHU(matt) ," +
             " masach INTEGER REFERENCES SACH(masach)," +
             " ngay DATE," +
             " trasach INTEGER," +
             " tienthue INTEGER )";
     db.execSQL(tablePhieuMuon);

     // Data mẫu
        db.execSQL("INSERT INTO LOAISACH VALUES (1, 'CNTT'),(2,'Ẩm thực'),(3, 'Kinh tế')");
        db.execSQL("INSERT INTO SACH VALUES (1, 'Lập trình Android', 5500, 2017, 1), (2, 'Nấu ăn ngon', 4000, 2022, 1), (3, 'Kinh tế vĩ mô', 6000, 2023, 3)");
        db.execSQL("INSERT INTO THUTHU VALUES ('thuthu01','Vũ Minh Quân','abc123'),('thuthu02','Trần Xuân Hưng','123abc')");
        db.execSQL("INSERT INTO THANHVIEN VALUES (1,'An Đức Anh','2004'),(2,'Nguyễn Đức Vũ','2002')");
        //trả sách: 1: đã trả - 0: chưa trả
        db.execSQL("INSERT INTO PHIEUMUON VALUES (1,1,'thuthu01', 1, '12/09/2023', 1, 2500),(2,1,'thuthu01', 3, '15/09/2023', 0, 4000),(3,2,'thuthu02', 2, '26/09/2023', 1, 6000)");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if( oldVersion != newVersion ) {
            String dropTableLoaiSach= "DROP TABLE IF EXISTS LOAISACH";
            db.execSQL(dropTableLoaiSach);
            //
            String dropTableSach= "DROP TABLE IF EXISTS SACH";
            db.execSQL(dropTableSach);
            //
            String dropTableThuThu= "DROP TABLE IF EXISTS THUTHU";
            db.execSQL(dropTableThuThu);
            //
            String dropTableThanhVien= "DROP TABLE IF EXISTS THANHVIEN";
            db.execSQL(dropTableThanhVien);
            //
            String dropTablePhieuMuon= "DROP TABLE IF EXISTS PHIEUMUON";
            db.execSQL(dropTablePhieuMuon);

            onCreate(db);
        }

    }
}
