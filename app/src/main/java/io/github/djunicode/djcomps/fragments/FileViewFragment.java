package io.github.djunicode.djcomps.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Date;

import io.github.djunicode.djcomps.R;
import io.github.djunicode.djcomps.adapters.FileAdapter;
import io.github.djunicode.djcomps.database.data.File;

public class FileViewFragment extends Fragment {

    public enum Type { Explore, Uploads, Stars, Downloads }

    public static final String FRAGMENT_TYPE_STR = "type_of_fragment";

    FileAdapter fileAdapter;

    public static FileViewFragment getInstance(Type fragmentType) {
        FileViewFragment fragment = new FileViewFragment();

        Bundle argBundle = new Bundle();
        argBundle.putSerializable(FRAGMENT_TYPE_STR, fragmentType);

        fragment.setArguments(argBundle);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fileview, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.fileview_rv);
        fileAdapter = new FileAdapter(false);

        View utilCard = view.findViewById(R.id.fileview_utilization_card);
        View filterbar = view.findViewById(R.id.fileview_filterbar);
        View uploadFAB = view.findViewById(R.id.fileview_upload_fab);

        Bundle argBundle = getArguments();
        Type fragmentType = (Type) (argBundle != null ? argBundle.getSerializable(FRAGMENT_TYPE_STR) : Type.Explore);

        if(fragmentType == Type.Explore || fragmentType == Type.Stars || fragmentType == Type.Downloads){
            utilCard.setVisibility(View.GONE);
            uploadFAB.setVisibility(View.GONE);
        }
        else if(fragmentType == Type.Uploads){
            filterbar.setVisibility(View.GONE);
        }

        setTitle(fragmentType);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(fileAdapter);

        prepareFiles();

        return view;
    }

    private void setTitle(Type fragmentType){
        String title = "DJ Comps";
        switch (fragmentType){
            case Stars: title = "Stars"; break;
            case Explore: title = "Explore"; break;
            case Uploads: title = "Uploads"; break;
            case Downloads: title = "Downloads"; break;
        }
        FragmentActivity act = getActivity();
        if(act != null) getActivity().setTitle(title);
    }


    private void prepareFiles() {

        File ar[];
        ar=new File[10];

        ar[0]= new File(new Long(1234567891),new Long(6122),new Long(21),5,4,"pdf","eccf notes",new Date(01/01/1997),"fgdfg");
        ar[1]= new File(new Long(1234567891),new Long(6122),new Long(21),5,4,"pdf","dlda notes",new Date(01/01/1997),"fgdfg");
        ar[2]= new File(new Long(1234567891),new Long(6122),new Long(21),5,4,"pdf","cg notes",new Date(01/01/1997),"fgdfg");
        ar[3]= new File(new Long(1234567891),new Long(6122),new Long(21),5,4,"pdf","sdadf notes",new Date(01/01/1997),"fgdfg");
        ar[4]= new File(new Long(1234567891),new Long(6122),new Long(21),5,4,"pdf","gdfghgfh notes",new Date(01/01/1997),"fgdfg");
        ar[5]= new File(new Long(1234567891),new Long(6122),new Long(21),5,4,"pdf","xxx notes",new Date(01/01/1997),"fgdfg");

        for(int i=0; i<6; i++){
            fileAdapter.addFile(ar[i]);
        }

    }

}
