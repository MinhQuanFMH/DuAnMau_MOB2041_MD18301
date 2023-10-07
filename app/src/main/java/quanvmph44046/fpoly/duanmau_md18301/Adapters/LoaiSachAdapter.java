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

import quanvmph44046.fpoly.duanmau_md18301.DAO.LoaiSachDAO;
import quanvmph44046.fpoly.duanmau_md18301.Model.LoaiSach;
import quanvmph44046.fpoly.duanmau_md18301.R;

public class LoaiSachAdapter extends RecyclerView.Adapter<LoaiSachAdapter.ViewHolder> {

    private Context context;
    private ArrayList<LoaiSach> list;
    private LoaiSachDAO loaiSachDAO;


    public LoaiSachAdapter(Context context, ArrayList<LoaiSach> list, LoaiSachDAO loaiSachDAO ) {
        this.context = context;
        this.list = list;
        this.loaiSachDAO = loaiSachDAO;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_loaisach, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtmaLoai.setText("Mã loại: " + String.valueOf(list.get(position).getMaloai()));
        holder.txttenLoai.setText("Tên loại: " + list.get(position).getTenloai());

        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoaiSachDAO loaiSachDAO = new LoaiSachDAO(context);
                int check = loaiSachDAO.xoaLoaiSach(list.get(holder.getAdapterPosition()).getMaloai());
                switch (check) {
                    case 1:
                        // load lại data
                        list.clear();
                        list = loaiSachDAO.getDSLoaiSach();
                        notifyDataSetChanged();
                        break;
                    case -1:
                        Toast.makeText(context, "Không thể xoá loại sách này vì đã có sách thuộc thể loại ", Toast.LENGTH_SHORT).show();
                        break;
                    case 0:
                        Toast.makeText(context, "Xoá loại sách không thành công", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        });

        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogSua(list.get(holder.getAdapterPosition()));

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtmaLoai, txttenLoai;
        ImageView ivDelete, ivEdit;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtmaLoai = itemView.findViewById(R.id.txtmaLoai);
            txttenLoai = itemView.findViewById(R.id.txttenLoai);
            ivDelete = itemView.findViewById(R.id.ivDelete);
            ivEdit = itemView.findViewById(R.id.ivEdit);
        }
    }

    private void loadData(){
        list.clear();
        list = loaiSachDAO.getDSLoaiSach();
        notifyDataSetChanged();
    }

    private void showDialogSua(LoaiSach loaiSach){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog_sualoaisach, null);
        builder.setView(view);

        EditText edtSuaLoaiSach = view.findViewById(R.id.edtSuaLoaiSach);
        edtSuaLoaiSach.setText(loaiSach.getTenloai());

        builder.setPositiveButton("Cập nhật", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String tenLoai = edtSuaLoaiSach.getText().toString();
                LoaiSach lsupdate = new LoaiSach(loaiSach.getMaloai(), tenLoai);
                boolean check = loaiSachDAO.suaLoaiSach(lsupdate);
                if(check == true){
                    Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    loadData();
                }else {
                    Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
    }
}
