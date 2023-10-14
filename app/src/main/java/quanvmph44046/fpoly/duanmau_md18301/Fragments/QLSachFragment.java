package quanvmph44046.fpoly.duanmau_md18301.Fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;

import quanvmph44046.fpoly.duanmau_md18301.Adapters.SachAdapter;
import quanvmph44046.fpoly.duanmau_md18301.DAO.LoaiSachDAO;
import quanvmph44046.fpoly.duanmau_md18301.DAO.SachDAO;
import quanvmph44046.fpoly.duanmau_md18301.Model.LoaiSach;
import quanvmph44046.fpoly.duanmau_md18301.Model.Sach;
import quanvmph44046.fpoly.duanmau_md18301.R;

public class QLSachFragment extends Fragment {
    SachDAO sachDAO;
    RecyclerView recyclerSach;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qlsach, container, false);
        recyclerSach = view.findViewById(R.id.recyclerSach);
        FloatingActionButton floatAddSach = view.findViewById(R.id.floatAddSach);

        sachDAO = new SachDAO(getContext());
        loadDaTa();

        floatAddSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        return view;
    }

    private void loadDaTa(){
        ArrayList<Sach> list = sachDAO.getDSDauSach();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerSach.setLayoutManager(linearLayoutManager);

        SachAdapter sachAdapter = new SachAdapter(getContext(), list, getDSLoaiSach(), sachDAO);
        recyclerSach.setAdapter(sachAdapter);
    }

    private void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog_themsach, null);
        builder.setView(view);

        EditText edtTenSach = view.findViewById(R.id.edtTenSach);
        EditText edtGiaThueSach = view.findViewById(R.id.edtGiaThueSach);
        EditText edtNamXb = view.findViewById(R.id.edtNamXb);
        Spinner spnLoaiSach = view.findViewById(R.id.spnLoaiSach);

        SimpleAdapter simpleAdapter = new SimpleAdapter(
                getContext(),
                getDSLoaiSach(),
                android.R.layout.simple_list_item_1,
                new String[]{"tenloai"},
                new int[]{android.R.id.text1}
        );
        spnLoaiSach.setAdapter(simpleAdapter);

        builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String tensach = edtTenSach.getText().toString();
                int giathue = Integer.parseInt(edtGiaThueSach.getText().toString());
                int namxb = Integer.parseInt(edtNamXb.getText().toString());
                HashMap<String, Object> hm = (HashMap<String, Object>) spnLoaiSach.getSelectedItem();
                int maloai = (int) hm.get("maloai");

                boolean check = sachDAO.themSachMoi(tensach, giathue, maloai, namxb);
                if(check == true){
                    Toast.makeText(getContext(), "Thêm sách mới thành công", Toast.LENGTH_SHORT).show();
                    loadDaTa();
                }else {
                    Toast.makeText(getContext(), "Thêm sách mới thất bại", Toast.LENGTH_SHORT).show();
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

    private ArrayList<HashMap<String, Object>> getDSLoaiSach() {
        LoaiSachDAO loaiSachDAO = new LoaiSachDAO(getContext());
        ArrayList<LoaiSach> list = loaiSachDAO.getDSLoaiSach();
        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();

        for(LoaiSach loai : list){
           HashMap<String, Object> hm = new HashMap<>();
           hm.put("maloai", loai.getMaloai());
           hm.put("tenloai", loai.getTenloai());
           listHM.add(hm);
        }

        return listHM;
    }
}
