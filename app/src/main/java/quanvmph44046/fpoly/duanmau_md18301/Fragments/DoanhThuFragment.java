package quanvmph44046.fpoly.duanmau_md18301.Fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Calendar;

import quanvmph44046.fpoly.duanmau_md18301.DAO.ThongKeDAO;
import quanvmph44046.fpoly.duanmau_md18301.R;

public class DoanhThuFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doanhthu, container, false);

        EditText edtNgayBatDau = view.findViewById(R.id.edtNgayBatDau);
        EditText edtNgayKetThuc = view.findViewById(R.id.edtNgayKetThuc);
        Button btnThongKe = view.findViewById(R.id.btnThongKe);
        TextView txtKetQua = view.findViewById(R.id.txtKetQua);

        Calendar calendar = Calendar.getInstance();

        edtNgayBatDau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                edtNgayBatDau.setText(dayOfMonth + "/" + (month + 1) + "/" + year);

                            }
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                );
                datePickerDialog.show();
            }
        });
        //
        edtNgayKetThuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                edtNgayKetThuc.setText(dayOfMonth + "/" + (month + 1) + "/" + year);

                            }
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                );
                datePickerDialog.show();
            }
        });

        btnThongKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThongKeDAO thongKeDAO = new ThongKeDAO(getContext());
                String ngaybatdau = edtNgayBatDau.getText().toString();
                String ngayketthuc = edtNgayKetThuc.getText().toString();
                int doanhthu = thongKeDAO.getDoanhThu(ngaybatdau, ngayketthuc);
                txtKetQua.setText("Doanh thu: " + doanhthu + "VND");
            }
        });

        return view;
    }
}
