package quanvmph44046.fpoly.duanmau_md18301.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import quanvmph44046.fpoly.duanmau_md18301.Adapters.Top10Adapter;
import quanvmph44046.fpoly.duanmau_md18301.DAO.ThongKeDAO;
import quanvmph44046.fpoly.duanmau_md18301.Model.Sach;
import quanvmph44046.fpoly.duanmau_md18301.R;

public class ThongKeTop10Fragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thongketop10, container, false);
        RecyclerView recyclerTop10 = view.findViewById(R.id.recyclerTop10);

        ThongKeDAO thongKeDAO = new ThongKeDAO(getContext());
        ArrayList<Sach> list = thongKeDAO.getTop10();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerTop10.setLayoutManager(linearLayoutManager);

        Top10Adapter adapter = new Top10Adapter(getContext(), list);
        recyclerTop10.setAdapter(adapter);

        return view;
    }
}
