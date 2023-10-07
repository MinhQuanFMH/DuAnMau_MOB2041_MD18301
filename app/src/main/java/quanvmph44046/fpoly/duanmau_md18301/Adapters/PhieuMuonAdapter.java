package quanvmph44046.fpoly.duanmau_md18301.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import quanvmph44046.fpoly.duanmau_md18301.DAO.PhieuMuonDAO;
import quanvmph44046.fpoly.duanmau_md18301.Model.PhieuMuon;
import quanvmph44046.fpoly.duanmau_md18301.R;

public class PhieuMuonAdapter  extends RecyclerView.Adapter<PhieuMuonAdapter.ViewHolder>{
    private ArrayList<PhieuMuon> list;
    private Context context;

    public PhieuMuonAdapter(ArrayList<PhieuMuon> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_phieumuon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    // Set dữ liệu lên trên textview
        holder.txtmaPM.setText("Mã phiếu mượn: " + list.get(position).getMapm());
        holder.txtmaTV.setText("Mã thành viên: " + list.get(position).getMatv());
        holder.txttenTV.setText("Tên thành viên: " + list.get(position).getTentv());
        holder.txtmaTT.setText("Mã thủ thư: " + list.get(position).getMatt());
        holder.txttenTT.setText("Tên thủ thư: " + list.get(position).getTentt());
        holder.txtmaSach.setText("Mã sách: " + list.get(position).getMasach());
        holder.txttenSach.setText("Tên sách: " + list.get(position).getTensach());
        holder.txtNgay.setText("Ngày: " + list.get(position).getNgay());

        // Trạng thái
        String trangthai = "";
        if(list.get(position).getTrasach() == 1) {
            trangthai = "Đã trả sách";
            holder.btnTraSach.setVisibility(View.GONE);
        } else {
            trangthai = "Chưa trả sách";
            holder.btnTraSach.setVisibility(View.VISIBLE);
        }
        holder.txttrangThai.setText("Trạng thái: " + trangthai);

        holder.txttienThue.setText("Tiền thuê: " + list.get(position).getTienthue());

        holder.btnTraSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhieuMuonDAO phieuMuonDAO = new PhieuMuonDAO(context);
                boolean kiemtra = phieuMuonDAO.thayDoiTrangThai(list.get(holder.getAdapterPosition()).getMapm());
                if(kiemtra) {
                    list.clear();
                    list = phieuMuonDAO.getDSPhieuMuon();
                    notifyDataSetChanged();
                } else {
                    Toast.makeText(context, "Thay đổi trạng thái không thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
    TextView txtmaPM, txtmaTV, txttenTV, txtmaTT, txttenTT, txtmaSach, txttenSach, txtNgay, txttrangThai, txttienThue;
    Button btnTraSach;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // ánh xạ các thành phần trong item_phieumuon
            txtmaPM = itemView.findViewById(R.id.txtmaPM);
            txtmaTV = itemView.findViewById(R.id.txtmaTV);
            txttenTV = itemView.findViewById(R.id.txttenTV);
            txtmaTT = itemView.findViewById(R.id.txtmaTT);
            txttenTT = itemView.findViewById(R.id.txttenTT);
            txtmaSach = itemView.findViewById(R.id.txtmaSach);
            txttenSach = itemView.findViewById(R.id.txttenSach);
            txtNgay = itemView.findViewById(R.id.txtNgay);
            txttrangThai = itemView.findViewById(R.id.txttrangThai);
            txttienThue = itemView.findViewById(R.id.txttienThue);
            btnTraSach = itemView.findViewById(R.id.btnTraSach);

        }
    }
}
