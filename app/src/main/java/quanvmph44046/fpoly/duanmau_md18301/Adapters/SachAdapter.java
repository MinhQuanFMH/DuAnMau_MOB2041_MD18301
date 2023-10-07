package quanvmph44046.fpoly.duanmau_md18301.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

import quanvmph44046.fpoly.duanmau_md18301.DAO.SachDAO;
import quanvmph44046.fpoly.duanmau_md18301.Model.Sach;
import quanvmph44046.fpoly.duanmau_md18301.R;

public class SachAdapter extends RecyclerView.Adapter<SachAdapter.ViewHolder>{
    private Context context;
    private ArrayList<Sach> list;
    private ArrayList<HashMap<String, Object>> listHM;
    private SachDAO sachDAO;

    public SachAdapter(Context context, ArrayList<Sach> list, ArrayList<HashMap<String, Object>> listHM, SachDAO sachDAO) {
        this.context = context;
        this.list = list;
        this.listHM = listHM;
        this.sachDAO = sachDAO;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.item_sach, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtMaSach.setText("Mã sách: " + list.get(position).getMasach());
        holder.txtTenSach.setText("Tên sách: " + list.get(position).getTensach());
        holder.txtTienThue.setText("Giá thuê: " + list.get(position).getGiathue());
        holder.txtMaLoaiSach.setText("Mã loại sách: " + list.get(position).getMaloai());
        holder.txtTenLoai.setText("Tên loại sách: " + list.get(position).getTenloai());

        holder.ivEditSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(list.get(holder.getAdapterPosition()));
            }
        });

        holder.ivDeleteSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int check = sachDAO.xoaSach(list.get(holder.getAdapterPosition()).getMasach());
                switch (check){
                    case 1:
                        Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                        loadDaTa();
                        break;
                    case 0:
                        Toast.makeText(context, "Xóa không thành công", Toast.LENGTH_SHORT).show();
                        break;
                    case -1:
                        Toast.makeText(context, "Không xóa được vì sách có trong phiếu mượn", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtMaSach, txtTenSach, txtTienThue, txtMaLoaiSach, txtTenLoai;
        ImageView ivEditSach, ivDeleteSach;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMaSach = itemView.findViewById(R.id.txtMaSach);
            txtTenSach = itemView.findViewById(R.id.txtTenSach);
            txtTienThue = itemView.findViewById(R.id.txtTienThue);
            txtMaLoaiSach = itemView.findViewById(R.id.txtMaLoaiSach);
            txtTenLoai = itemView.findViewById(R.id.txtTenLoai);
            ivEditSach = itemView.findViewById(R.id.ivEditSach);
            ivDeleteSach = itemView.findViewById(R.id.ivDeleteSach);
        }
    }
    private void showDialog(Sach sach){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog_suasach, null);
        builder.setView(view);

        TextView txtMaSachUp = view.findViewById(R.id.txtMaSachUp);
        EditText edtTenSachUp = view.findViewById(R.id.edtTenSachUp);
        EditText edtGiaThueSachUp = view.findViewById(R.id.edtGiaThueSachUp);
        Spinner spnLoaiSachUp = view.findViewById(R.id.spnLoaiSachUp);

        txtMaSachUp.setText("Mã sách: " + sach.getMasach());
        edtTenSachUp.setText(sach.getTensach());
        edtGiaThueSachUp.setText(String.valueOf(sach.getGiathue()));

        SimpleAdapter simpleAdapter = new SimpleAdapter(
                context,
                listHM,
                android.R.layout.simple_list_item_1,
                new String[]{"tenloai"},
                new int[]{android.R.id.text1}
        );
        spnLoaiSachUp.setAdapter(simpleAdapter);

        int index = 0;
        int position = -1;
        for(HashMap<String, Object> item : listHM){
            if((int)item.get("maloai") == sach.getMaloai()){
                position = index;
            }
                index++;
        }
        spnLoaiSachUp.setSelection(position);

        builder.setPositiveButton("Cập nhật", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String tensach = edtTenSachUp.getText().toString();
                int giathue = Integer.parseInt(edtGiaThueSachUp.getText().toString());
                HashMap<String, Object> hm = (HashMap<String, Object>) spnLoaiSachUp.getSelectedItem();
                int maloai = (int) hm.get("maloai");

                boolean check = sachDAO.capNhatSach(sach.getMasach(), tensach, giathue, maloai);
                if(check == true){
                    Toast.makeText(context, "Cập nhật sách thành công", Toast.LENGTH_SHORT).show();
                    loadDaTa();
                }else {
                    Toast.makeText(context, "Cập nhật sách thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void loadDaTa(){
        list.clear();
        list = sachDAO.getDSDauSach();
        notifyDataSetChanged();
    }
}
