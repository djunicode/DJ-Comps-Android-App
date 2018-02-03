package io.github.djunicode.djcomps;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class myuploads extends Fragment implements OnItemClickListener{


    private RecyclerView recyclerView;
    private DocumentsAdapter adapter;
    private List<Documents> documents;


    @Override
    public void onClick(View view, int position) {
        // The onClick implementation of the RecyclerView item click
        final Documents doc = documents.get(position);
        Intent i = new Intent(getContext(), FileDetails.class);
        i.putExtra("filename", doc.getName());
        i.putExtra("creator", doc.getCreator());
       // i.putExtra("image", doc.getImg());
        i.putExtra("subject", doc.getSubject());
        startActivity(i);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        View view = inflater.inflate(R.layout.explore_fragment, container, false);


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
        int[] img = new int[]{
                R.drawable.img1,
                R.drawable.img2,
                R.drawable.img3,
                R.drawable.img4
        };

        Documents ar[];
        ar=new Documents[10];

        ar[0]= new Documents("Doc1","eccf","SE",img[0],"Varun",true,false);
        ar[1] = new Documents("Doc2","dlda","SE A",img[1],"xyz",false,false);
        ar[2] = new Documents("Doc3","oopm","SE B",img[2],"abc",true,false);
        ar[3] = new Documents("Doc4","ds","dept",img[3],"Varun",false,true);
        ar[4] = new Documents("Doc5","physics","FE",img[2],"xyz",true,true);
        ar[5] = new Documents("Doc6","chemistry","dept",img[3],"abc",false,false);


        for(int i=0;i<6;i++)
        {
            if(ar[i].getCreator()=="Varun")
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
        getActivity().setTitle("My Uploads");
    }
}
