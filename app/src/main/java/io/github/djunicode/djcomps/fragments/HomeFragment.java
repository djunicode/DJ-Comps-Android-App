package io.github.djunicode.djcomps.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.transition.AutoTransition;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Date;

import io.github.djunicode.djcomps.HTTPRequests;
import io.github.djunicode.djcomps.adapters.FileAdapter;
import io.github.djunicode.djcomps.database.data.File;
import io.github.djunicode.djcomps.R;


public class HomeFragment extends Fragment {

    private FileAdapter recentFileAdapter, popularFileAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        FragmentActivity act = getActivity();
        if(act != null) getActivity().setTitle("Home");

        recentFileAdapter = new FileAdapter(getContext(), true);
        popularFileAdapter = new FileAdapter(getContext(), true);

        RecyclerView recentFileRV = view.findViewById(R.id.recent_uploads_rv);
        RecyclerView popularFileRV = view.findViewById(R.id.popular_uploads_rv);

        recentFileRV.setAdapter(recentFileAdapter);
        popularFileRV.setAdapter(popularFileAdapter);

        Button recentMoreButton = view.findViewById(R.id.recent_uploads_more_button);
        Button popularMoreButton = view.findViewById(R.id.popular_uploads_more_button);

        recentMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: pass argument to fragment to sort by date
                FragmentManager fragmentManager = getFragmentManager();
                if(fragmentManager != null){
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    Fragment fragment = FileViewFragment.getInstance(FileViewFragment.Type.Explore);
                    fragment.setEnterTransition(new AutoTransition());
                    transaction.replace(R.id.content_frame, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }
        });

        popularMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: pass argument to fragment to sort by popularity
                FragmentManager fragmentManager = getFragmentManager();
                if(fragmentManager != null){
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    Fragment fragment = FileViewFragment.getInstance(FileViewFragment.Type.Explore);
                    fragment.setEnterTransition(new AutoTransition());
                    transaction.replace(R.id.content_frame, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }
        });

        prepareRecentItems();
        preparePopularItems();

        return view;
    }

    private void prepareRecentItems() {

        /*File ar[];
        ar=new File[10];

        ar[0]= new File(new Long(1234567891),new Long(6122),new Long(21),5,4,"pdf","eccf notes",new Date(01/01/1997),"fgdfg", false, false);
        ar[1]= new File(new Long(1234567891),new Long(6122),new Long(21),5,4,"pdf","dlda notes",new Date(01/01/1997),"fgdfg", false, false);
        ar[2]= new File(new Long(1234567891),new Long(6122),new Long(21),5,4,"pdf","cg notes",new Date(01/01/1997),"fgdfg", false, false);
        ar[3]= new File(new Long(1234567891),new Long(6122),new Long(21),5,4,"pdf","sdadf notes",new Date(01/01/1997),"fgdfg", false, false);
        ar[4]= new File(new Long(1234567891),new Long(6122),new Long(21),5,4,"pdf","gdfghgfh notes",new Date(01/01/1997),"fgdfg", false, false);
        ar[5]= new File(new Long(1234567891),new Long(6122),new Long(21),5,4,"pdf","xxx notes",new Date(01/01/1997),"fgdfg", false, false);

        for(int i=0; i<6; i++){
            recentFileAdapter.addFile(ar[i]);
        }*/

        HTTPRequests.getAllFiles(recentFileAdapter, "http://djunicode.pythonanywhere/file", "0", "5", "recent", "descending");

    }

    private void preparePopularItems() {

        /*File ar[];
        ar=new File[10];
        ar[0]= new File(new Long(1234567891),new Long(6122),new Long(21),5,4,"pdf","eccf notes",new Date(01/01/1997),"fgdfg", false, false);
        ar[1]= new File(new Long(1234567891),new Long(6122),new Long(21),5,4,"pdf","dlda notes",new Date(01/01/1997),"fgdfg", false, false);
        ar[2]= new File(new Long(1234567891),new Long(6122),new Long(21),5,4,"pdf","cg notes",new Date(01/01/1997),"fgdfg", false, false);
        ar[3]= new File(new Long(1234567891),new Long(6122),new Long(21),5,4,"pdf","sdadf notes",new Date(01/01/1997),"fgdfg", false, false);
        ar[4]= new File(new Long(1234567891),new Long(6122),new Long(21),5,4,"pdf","gdfghgfh notes",new Date(01/01/1997),"fgdfg", false, false);
        ar[5]= new File(new Long(1234567891),new Long(6122),new Long(21),5,4,"pdf","xxx notes",new Date(01/01/1997),"fgdfg", false, false);

        for(int i=0; i<6; i++){
            popularFileAdapter.addFile(ar[i]);
        }*/

        HTTPRequests.getAllFiles(recentFileAdapter, "http://djunicode.pythonanywhere/file", "0", "5", "popularity", "descending");

    }

}
