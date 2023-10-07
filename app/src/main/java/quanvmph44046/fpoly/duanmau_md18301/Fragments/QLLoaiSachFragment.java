package quanvmph44046.fpoly.duanmau_md18301.Fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import quanvmph44046.fpoly.duanmau_md18301.Adapters.LoaiSachAdapter;
import quanvmph44046.fpoly.duanmau_md18301.DAO.LoaiSachDAO;
import quanvmph44046.fpoly.duanmau_md18301.Model.LoaiSach;
import quanvmph44046.fpoly.duanmau_md18301.R;

public class QLLoaiSachFragment extends Fragment {

    RecyclerView recyclerLoaiSach;
    LoaiSachDAO loaiSachDAO;
    EditText edtLoaiSach;
    int maloai;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qlloaisach, container, false);
        recyclerLoaiSach = view.findViewById(R.id.recyclerLoaiSach);
        FloatingActionButton floatAddLoaiSach = view.findViewById(R.id.floatAddLoaiSach);

        floatAddLoaiSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        loaiSachDAO = new LoaiSachDAO(getContext());
        loadData();

        return view;
    }

    private void loadData() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerLoaiSach.setLayoutManager(linearLayoutManager);

        ArrayList<LoaiSach> list = loaiSachDAO.getDSLoaiSach();

        LoaiSachAdapter loaiSachAdapter = new LoaiSachAdapter(getContext(), list, loaiSachDAO);
        recyclerLoaiSach.setAdapter(loaiSachAdapter);
    }

    private void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog_themloaisach, null);
        builder.setView(view);

        EditText edtLoaiSach = view.findViewById(R.id.edtLoaiSach);

        builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String tenloai = edtLoaiSach.getText().toString();

                boolean check = loaiSachDAO.themLoaiSach(tenloai);
                if(check == true){
                    Toast.makeText(getContext(), "Thêm loại sách thành công", Toast.LENGTH_SHORT).show();
                    // load data
                    loadData();
                }else {
                    Toast.makeText(getContext(), "Thêm loại sách thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
    }
}
