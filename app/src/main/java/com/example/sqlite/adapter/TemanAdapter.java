package com.example.sqlite.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sqlite.EditData;
import com.example.sqlite.MainActivity;
import com.example.sqlite.MenampilkanData;
import com.example.sqlite.R;
import com.example.sqlite.database.DBController;
import com.example.sqlite.database.Teman;

import java.util.ArrayList;

public class TemanAdapter extends RecyclerView.Adapter<TemanAdapter.TemanViewHolder> {

    private ArrayList<Teman> ListData;
    //  construktor
    public TemanAdapter(ArrayList<Teman> listData) {
        ListData = listData;
    }

    //    memanggil tampilan/layout dari adapternya  menggunakan Inflater #1
    @Override
    public TemanAdapter.TemanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInf = LayoutInflater.from(parent.getContext());
        View view = layoutInf.inflate(R.layout.row_data_teman,parent,false);
        return new TemanViewHolder(view);
    }

    //    untuk menampilkan #3
    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(TemanAdapter.TemanViewHolder holder, int position) {
        String nm,tlp;
        Teman data = ListData.get(position);
        nm = data.getNama();
        tlp = data.getTelpon();

        holder.namaTxt.setTextColor(Color.BLACK);
        holder.namaTxt.setTextSize(20);
        holder.namaTxt.setText(nm);

        holder.telponTxt.setText(tlp);


    }

    //  menghitung ukuran dari arraylist #4
//  bisa ditambahin / biarkan saja
    @Override
    public int getItemCount() {
        return (ListData != null) ? ListData.size() : 0;
    }

    //  untuk mendaftarkan terlebih dahulu #2
    public class TemanViewHolder extends RecyclerView.ViewHolder{
        private CardView cardku;
        private TextView namaTxt , telponTxt;
        private Context context;
        String id, nama, telpon;
        Bundle bundle = new Bundle();
        DBController controller;
        public TemanViewHolder(View view){
            super(view);
            cardku = (CardView) view.findViewById(R.id.kartuku);
            namaTxt = (TextView) view.findViewById(R.id.textNama);
            telponTxt = (TextView) view.findViewById(R.id.textTelpon);
            context = itemView.getContext();
            controller = new DBController(context);

            cardku.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    nama = ListData.get(getAdapterPosition()).getNama();
                    telpon = ListData.get(getAdapterPosition()).getTelpon();

                    bundle.putString("nama", nama.trim());
                    bundle.putString("telpon", telpon.trim());

                    Intent intent = new Intent(context, MenampilkanData.class);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });

            cardku.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    PopupMenu pop = new PopupMenu(context, view);
                    pop.inflate(R.menu.popup_menu);

                    pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.mnedit:
                                    id = ListData.get(getAdapterPosition()).getId();
                                    nama = ListData.get(getAdapterPosition()).getNama();
                                    telpon = ListData.get(getAdapterPosition()).getTelpon();

                                    bundle.putString("id", id.trim());
                                    bundle.putString("nama", nama.trim());
                                    bundle.putString("telpon", telpon.trim());

                                    Intent intent = new Intent(context, EditData.class);
                                    intent.putExtras(bundle);
                                    context.startActivity(intent);
                                    break;
                                case R.id.mndelete:
                                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                    builder.setTitle("Hapus Data");
                                    builder.setMessage("Yakin ingin di hapus?");
                                    builder.setCancelable(true);
                                    builder.setPositiveButton("Ya",
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    id = ListData.get(getAdapterPosition()).getId();
                                                    controller.hapusData(id);

                                                    Intent intent = new Intent(context, MainActivity.class);
                                                    context.startActivity(intent);
                                                }
                                            }
                                    );
                                    builder.setNegativeButton("Batal",
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    dialogInterface.cancel();
                                                }
                                            }
                                    );
                                    AlertDialog alertDialog = builder.create();
                                    alertDialog.show();
                                    break;
                            }
                            return false;
                        }
                    });
                    pop.show();
                    return false;
                }
            });
        }
    }
}
