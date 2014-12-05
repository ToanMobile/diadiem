package com.DiaDiem.app;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableRow;

import com.DiaDiem.app.InsertDuLieu.InsertBinhThanh;
import com.DiaDiem.app.InsertDuLieu.InsertGoVap;
import com.DiaDiem.app.InsertDuLieu.InsertHaiSan;
import com.DiaDiem.app.InsertDuLieu.InsertLau;
import com.DiaDiem.app.InsertDuLieu.InsertMonChien;
import com.DiaDiem.app.InsertDuLieu.InsertMonHap;
import com.DiaDiem.app.InsertDuLieu.InsertMonNuong;
import com.DiaDiem.app.InsertDuLieu.InsertMonXao;
import com.DiaDiem.app.InsertDuLieu.InsertNhaHang;
import com.DiaDiem.app.InsertDuLieu.InsertQuan1;
import com.DiaDiem.app.InsertDuLieu.InsertQuan10;
import com.DiaDiem.app.InsertDuLieu.InsertQuan11;
import com.DiaDiem.app.InsertDuLieu.InsertQuan3;
import com.DiaDiem.app.InsertDuLieu.InsertQuan5;
import com.DiaDiem.app.InsertDuLieu.InsertQuanAn;
import com.DiaDiem.app.InsertDuLieu.InsertQuanCafe;
import com.DiaDiem.app.InsertDuLieu.InsertTanBinh;
import com.DiaDiem.app.InsertDuLieu.InsertTanPhu;
import com.DiaDiem.app.InsertDuLieu.InsertTiemBanh;

/**
 * Created by toan on 3/15/14.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class ActivityHome extends Fragment {
    TableRow tableRow,tableRow1,tableRow2,tableRow3,tableRow4,tableRow5,tableRow6,
            tableRow7,tableRow8,tableRow9,tableRow10,tableRow11,tableRow12,tableRow13,
            tableRow14,tableRow15,tableRow16,tableRow17,tableRow18;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View view = inflater.inflate(R.layout.activity_home, container, false);
        ///CSDL cho Table Row
        tableRow = (TableRow) view.findViewById(R.id.nha_hang);
        tableRow1 = (TableRow) view.findViewById(R.id.quan_an);
        tableRow2 = (TableRow) view.findViewById(R.id.tiem_banh);
        tableRow3 = (TableRow) view.findViewById(R.id.quan_cafe);
        tableRow4 = (TableRow) view.findViewById(R.id.hai_san);
        tableRow5 = (TableRow) view.findViewById(R.id.lau);
        tableRow6 = (TableRow) view.findViewById(R.id.mon_chien);
        tableRow7 = (TableRow) view.findViewById(R.id.mon_nuong);
        tableRow8 = (TableRow) view.findViewById(R.id.mon_hap);
        tableRow9 = (TableRow) view.findViewById(R.id.mon_xao);
        tableRow10 = (TableRow) view.findViewById(R.id.quan_1);
        tableRow11 = (TableRow) view.findViewById(R.id.quan_3);
        tableRow12 = (TableRow) view.findViewById(R.id.quan_5);
        tableRow13 = (TableRow) view.findViewById(R.id.quan_10);
        tableRow14 = (TableRow) view.findViewById(R.id.quan_11);
        tableRow15 = (TableRow) view.findViewById(R.id.tan_phu);
        tableRow16 = (TableRow) view.findViewById(R.id.go_vap);
        tableRow17 = (TableRow) view.findViewById(R.id.tan_binh);
        tableRow18 = (TableRow) view.findViewById(R.id.binh_thanh);

        tableRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), InsertNhaHang.class);
                getActivity().startActivity(i);
            }
        });
        tableRow1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(getActivity(), InsertQuanAn.class);
                getActivity().startActivity(i1);
            }
        });
        tableRow2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 = new Intent(getActivity(), InsertTiemBanh.class);
                getActivity().startActivity(i2);
            }
        });
        tableRow3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i3 = new Intent(getActivity(), InsertQuanCafe.class);
                getActivity().startActivity(i3);
            }
        });
        tableRow4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i4 = new Intent(getActivity(), InsertHaiSan.class);
                getActivity().startActivity(i4);
            }
        });
        tableRow5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i5 = new Intent(getActivity(), InsertLau.class);
                getActivity().startActivity(i5);
            }
        });
        tableRow6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i6 = new Intent(getActivity(), InsertMonChien.class);
                getActivity().startActivity(i6);
            }
        });
        tableRow7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i7 = new Intent(getActivity(), InsertMonNuong.class);
                getActivity().startActivity(i7);
            }
        });
        tableRow8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i8 = new Intent(getActivity(), InsertMonHap.class);
                getActivity().startActivity(i8);
            }
        });
        tableRow9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i9 = new Intent(getActivity(), InsertMonXao.class);
                getActivity().startActivity(i9);
            }
        });
        tableRow10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), InsertQuan1.class);
                getActivity().startActivity(intent);
            }
        });
        tableRow11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getActivity(), InsertQuan3.class);
                getActivity().startActivity(intent1);
            }
        });
        tableRow12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getActivity(), InsertQuan5.class);
                getActivity().startActivity(intent2);
            }
        });
        tableRow13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i13 = new Intent(getActivity(), InsertQuan10.class);
                getActivity().startActivity(i13);
            }
        });
        tableRow14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i14 = new Intent(getActivity(), InsertQuan11.class);
                getActivity().startActivity(i14);
            }
        });
        tableRow15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i15 = new Intent(getActivity(), InsertTanPhu.class);
                getActivity().startActivity(i15);
            }
        });
        tableRow16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i16 = new Intent(getActivity(), InsertGoVap.class);
                getActivity().startActivity(i16);
            }
        });
        tableRow17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i17 = new Intent(getActivity(), InsertTanBinh.class);
                getActivity().startActivity(i17);
            }
        });
        tableRow18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i18 = new Intent(getActivity(), InsertBinhThanh.class);
                getActivity().startActivity(i18);
            }
        });

        return view;
    }
}