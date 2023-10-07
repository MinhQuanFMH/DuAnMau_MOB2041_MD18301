package quanvmph44046.fpoly.duanmau_md18301.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import quanvmph44046.fpoly.duanmau_md18301.Model.Sach;
import quanvmph44046.fpoly.duanmau_md18301.R;

public class Top10Adapter extends RecyclerView.Adapter<Top10Adapter.ViewHolder>{
    private Context context;
    private ArrayList<Sach> list;

    public Top10Adapter(Context context, ArrayList<Sach> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.item_recyclertop10, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtMaSachTop10.setText(String.valueOf(list.get(position).getMasach()));
        holder.txtTenSachTop10.setText(list.get(position).getTensach());
        holder.txtSoLuongTop10.setText(String.valueOf(list.get(position).getSoluongdamuon()));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
    TextView txtMaSachTop10, txtTenSachTop10, txtSoLuongTop10;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMaSachTop10 = itemView.findViewById(R.id.txtMaSachTop10);
            txtTenSachTop10 = itemView.findViewById(R.id.txtTenSachTop10);
            txtSoLuongTop10 = itemView.findViewById(R.id.txtSoLuongTop10);
        }
    }
}
