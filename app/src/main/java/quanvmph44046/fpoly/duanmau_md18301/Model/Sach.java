package quanvmph44046.fpoly.duanmau_md18301.Model;

public class Sach {
    private int masach;
    private String tensach;
    private int giathue;
    private int maloai;
    private int soluongdamuon;
    private String tenloai;

    private int namxb;


    public Sach() {
    }

    public Sach(int masach, String tensach, int giathue, int maloai, int namxb) {
        this.masach = masach;
        this.tensach = tensach;
        this.giathue = giathue;
        this.maloai = maloai;
        this.namxb = namxb;
    }

    public Sach(int masach, String tensach, int soluongdamuon) {
        this.masach = masach;
        this.tensach = tensach;
        this.soluongdamuon = soluongdamuon;
    }

    public Sach(int masach, String tensach, int giathue, int maloai, String tenloai) {
        this.masach = masach;
        this.tensach = tensach;
        this.giathue = giathue;
        this.maloai = maloai;
        this.tenloai = tenloai;
    }

    public Sach(int masach, String tensach, int giathue, int maloai, String tenloai, int namxb) {
        this.masach = masach;
        this.tensach = tensach;
        this.giathue = giathue;
        this.maloai = maloai;
        this.tenloai = tenloai;
        this.namxb = namxb;
    }

    public int getNamxb() {
        return namxb;
    }

    public void setNamxb(int namxb) {
        this.namxb = namxb;
    }

    public String getTenloai() {
        return tenloai;
    }

    public void setTenloai(String tenloai) {
        this.tenloai = tenloai;
    }

    public int getSoluongdamuon() {
        return soluongdamuon;
    }

    public void setSoluongdamuon(int soluongdamuon) {
        this.soluongdamuon = soluongdamuon;
    }

    public int getMasach() {
        return masach;
    }

    public void setMasach(int masach) {
        this.masach = masach;
    }

    public String getTensach() {
        return tensach;
    }

    public void setTensach(String tensach) {
        this.tensach = tensach;
    }

    public int getGiathue() {
        return giathue;
    }

    public void setGiathue(int giathue) {
        this.giathue = giathue;
    }

    public int getMaloai() {
        return maloai;
    }

    public void setMaloai(int maloai) {
        this.maloai = maloai;
    }
}
