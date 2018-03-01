package io.github.djunicode.djcomps.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Date;

import io.github.djunicode.djcomps.adapters.FileAdapter;
import io.github.djunicode.djcomps.database.data.File;
import io.github.djunicode.djcomps.R;


public class HomeFragment extends Fragment {

    private FileAdapter recentFileAdapter, popularFileAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recentFileAdapter = new FileAdapter();
        popularFileAdapter = new FileAdapter();

        RecyclerView recentFileRV = view.findViewById(R.id.recent_uploads_rv);
        RecyclerView popularFileRV = view.findViewById(R.id.popular_uploads_rv);

        recentFileRV.setAdapter(recentFileAdapter);
        popularFileRV.setAdapter(popularFileAdapter);

        prepareRecentItems();
        preparePopularItems();

        return view;
    }

    private void prepareRecentItems() {

        File ar[];
        ar=new File[10];

        ar[0]= new File(new Long(1234567891),new Long(6122),new Long(21),5,4,"pdf","eccf notes",new Date(01/01/1997),"fgdfg");
        ar[1]= new File(new Long(1234567891),new Long(6122),new Long(21),5,4,"pdf","dlda notes",new Date(01/01/1997),"fgdfg");
        ar[2]= new File(new Long(1234567891),new Long(6122),new Long(21),5,4,"pdf","cg notes",new Date(01/01/1997),"fgdfg");
        ar[3]= new File(new Long(1234567891),new Long(6122),new Long(21),5,4,"pdf","sdadf notes",new Date(01/01/1997),"fgdfg");
        ar[4]= new File(new Long(1234567891),new Long(6122),new Long(21),5,4,"pdf","gdfghgfh notes",new Date(01/01/1997),"fgdfg");
        ar[5]= new File(new Long(1234567891),new Long(6122),new Long(21),5,4,"pdf","xxx notes",new Date(01/01/1997),"fgdfg");

        for(int i=0; i<6; i++){
            recentFileAdapter.addFile(ar[i]);
        }

    }

    private void preparePopularItems() {

        File ar[];
        ar=new File[10];
        ar[0]= new File(new Long(1234567891),new Long(6122),new Long(21),5,4,"pdf","eccf notes",new Date(01/01/1997),"fgdfg");
        ar[1]= new File(new Long(1234567891),new Long(6122),new Long(21),5,4,"pdf","dlda notes",new Date(01/01/1997),"fgdfg");
        ar[2]= new File(new Long(1234567891),new Long(6122),new Long(21),5,4,"pdf","cg notes",new Date(01/01/1997),"fgdfg");
        ar[3]= new File(new Long(1234567891),new Long(6122),new Long(21),5,4,"pdf","sdadf notes",new Date(01/01/1997),"fgdfg");
        ar[4]= new File(new Long(1234567891),new Long(6122),new Long(21),5,4,"pdf","gdfghgfh notes",new Date(01/01/1997),"fgdfg");
        ar[5]= new File(new Long(1234567891),new Long(6122),new Long(21),5,4,"pdf","xxx notes",new Date(01/01/1997),"fgdfg");

        for(int i=0; i<6; i++){
            popularFileAdapter.addFile(ar[i]);
        }

    }

}
