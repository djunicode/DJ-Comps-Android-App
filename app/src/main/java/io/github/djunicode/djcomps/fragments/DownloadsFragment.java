package io.github.djunicode.djcomps.fragments;


import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.github.djunicode.djcomps.DocumentsAdapter;
import io.github.djunicode.djcomps.FileDetails;
import io.github.djunicode.djcomps.OnItemClickListener;
import io.github.djunicode.djcomps.R;
import io.github.djunicode.djcomps.database.data.File;


public class DownloadsFragment extends Fragment implements OnItemClickListener {


    private RecyclerView recyclerView;
    private DocumentsAdapter adapter;
    private List<File> documents;

    @Override
    public void onClick(View view, int position) {
        // The onClick implementation of the RecyclerView item click
        final File doc = documents.get(position);
        Intent i = new Intent(getContext(), FileDetails.class);
        i.putExtra("filename", doc.name);
        i.putExtra("id", doc.file_id);
        i.putExtra("sap",doc.sap_id);
        i.putExtra("time",doc.time_added);
        i.putExtra("size",doc.size);
        i.putExtra("stars",doc.no_of_stars);
        i.putExtra("downloads",doc.no_of_downloads);
        i.putExtra("type",doc.type);
        i.putExtra("description",doc.description);
        startActivity(i);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        View view = inflater.inflate(R.layout.uploads_fragment, container, false);


        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        documents = new ArrayList<>();
        adapter = new DocumentsAdapter(getActivity(), documents);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        prepareStaredItems();

        adapter = new DocumentsAdapter(getContext(),documents);
        adapter.setClickListener(this);

        return view;
    }


    private void prepareStaredItems() {


        File ar[];
        ar=new File[10];

        ar[0]= new File(new Long(1234567891),new Long(6122),new Long(21),5,4,"pdf","eccf notes",new Date(01/01/1997),"fgdfg");
        ar[1]= new File(new Long(1234567891),new Long(6122),new Long(21),5,4,"pdf","dlda notes",new Date(01/01/1997),"fgdfg");
        ar[2]= new File(new Long(1234567891),new Long(6122),new Long(21),5,4,"pdf","cg notes",new Date(01/01/1997),"fgdfg");
        ar[3]= new File(new Long(1234567891),new Long(6122),new Long(21),5,4,"pdf","sdadf notes",new Date(01/01/1997),"fgdfg");
        ar[4]= new File(new Long(1234567891),new Long(6122),new Long(21),5,4,"pdf","gdfghgfh notes",new Date(01/01/1997),"fgdfg");
        ar[5]= new File(new Long(1234567891),new Long(6122),new Long(21),5,4,"pdf","xxx notes",new Date(01/01/1997),"fgdfg");
        ar[0].isDownloaded=true;
        ar[3].isDownloaded=true;

        for(int i=0;i<6;i++)
        {
            if(ar[i].isDownloaded)
                documents.add(ar[i]);

        }


        adapter.notifyDataSetChanged();
    }



    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }





    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Downloaded");
    }
}
