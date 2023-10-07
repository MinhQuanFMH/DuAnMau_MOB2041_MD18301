package quanvmph44046.fpoly.duanmau_md18301.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import quanvmph44046.fpoly.duanmau_md18301.DAO.ThanhVienDAO;
import quanvmph44046.fpoly.duanmau_md18301.Model.ThanhVien;
import quanvmph44046.fpoly.duanmau_md18301.R;

public class ThanhVienAdapter extends RecyclerView.Adapter<ThanhVienAdapter.ViewHolder>{
    private Context context;
    private ArrayList<ThanhVien> list;
    private ThanhVienDAO thanhVienDAO;

    public ThanhVienAdapter(Context context, ArrayList<ThanhVien> list, ThanhVienDAO thanhVienDAO) {
        this.context = context;
        this.list = list;
        this.thanhVienDAO = thanhVienDAO;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.item_thanhvien, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtMaThanhVien.setText("Mã thành viên: " + list.get(position).getMatv());
        holder.txtHoTen.setText("Họ tên: " + list.get(position).getHoten());
        holder.txtNamSinh.setText("Năm sinh: " + list.get(position).getNamsinh());

        holder.ivEditTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogCapNhatTV(list.get(holder.getAdapterPosition()));
            }
        });

        holder.ivDeleteTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int check = thanhVienDAO.xoaThongTinThanhVien(list.get(holder.getAdapterPosition()).getMatv());
                switch (check) {
                    case 1:
                        Toast.makeText(context, "Xóa thông tin thành công", Toast.LENGTH_SHORT).show();
                        loadDaTa();
                        break;
                    case 0:
                        Toast.makeText(context, "Xóa thông tin thất bại", Toast.LENGTH_SHORT).show();
                        break;
                    case -1:
                        Toast.makeText(context, "Thành viên có phiếu mượn, không được phép xóa", Toast.LENGTH_SHORT).show();
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
        TextView txtMaThanhVien, txtHoTen, txtNamSinh;
        ImageView ivEditTV, ivDeleteTV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMaThanhVien = itemView.findViewById(R.id.txtMaThanhVien);
            txtHoTen = itemView.findViewById(R.id.txtHoTen);
            txtNamSinh = itemView.findViewById(R.id.txtNamSinh);
            ivEditTV = itemView.findViewById(R.id.ivEditTV);
            ivDeleteTV = itemView.findViewById(R.id.ivDeleteTV);
        }
    }

    private void showDialogCapNhatTV(ThanhVien tv) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog_capnhatthanhvien, null);
        builder.setView(view);

        TextView txtMaTVUp = view.findViewById(R.id.txtMaTVUp);
        EditText edtHoTenUp = view.findViewById(R.id.edtHoTenUp);
        EditText edtNamSinhUp = view.findViewById(R.id.edtNamSinhUp);

        txtMaTVUp.setText("Mã thành viên: " + tv.getMatv());
        edtHoTenUp.setText(tv.getHoten());
        edtNamSinhUp.setText(tv.getNamsinh());

        builder.setPositiveButton("Cập nhật", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String hotenup = edtHoTenUp.getText().toString();
                String namsinhup = edtNamSinhUp.getText().toString();
                int matv = tv.getMatv();

                boolean check = thanhVienDAO.capNhatThanhVien(matv, hotenup, namsinhup);
                if(check = true){
                    Toast.makeText(context, "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();

                    loadDaTa();
                }else {
                    Toast.makeText(context, "Cập nhật thông tin thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    private void loadDaTa(){
        list.clear();
        list = thanhVienDAO.getDSThanhVien();
        notifyDataSetChanged();
    }

}
