package io.github.djunicode.djcomps;

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

import io.github.djunicode.djcomps.Database.Data.File;
import io.github.djunicode.djcomps.Database.Data.User;


public class explore extends Fragment implements OnItemClickListener {


    private RecyclerView recyclerView;
    private UsersAdapter adapter;
    private List<User> documents;


    @Override
    public void onClick(View view, int position) {
        // The onClick implementation of the RecyclerView item click
        final User doc = documents.get(position);
        Intent i = new Intent(getContext(), UserPage.class);
        i.putExtra("name", doc.name);
        i.putExtra("sap",doc.sap_id);
        i.putExtra("bio",doc.bio);
       // i.putExtra("group_id",doc.group_id);

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
        adapter = new UsersAdapter(getActivity(), documents);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        prepareStaredItems();

        adapter = new UsersAdapter(getContext(),documents);
        adapter.setClickListener(this);


        return view;
    }



    private void prepareStaredItems() {


        User ar[];
        ar=new User[10];

        ar[0]= new User(new Long(600122),"hi my name is varun. This is the Unicode Project.","Varun P Thakkar",new Long(87876),".");
        ar[1]= new User(new Long(600123),"hi my name is mohit. This is the java Project.","Mohit Thurakhia",new Long(87876),".");
        ar[2]= new User(new Long(600114),"hi my name is umair. This is the python Project.","M.Umair Siddiqui",new Long(87876),".");
        ar[3]= new User(new Long(600104),"hi my name is rohan. This is the Unicode thing.","Rohan Shah",new Long(87876),".");
        ar[4]= new User(new Long(600103),"hi my name is reny. This is not my project.","Reny Shah",new Long(87876),".");
        ar[5]= new User(new Long(600102),"hi my name is rhs. This is the Project.","Raj H Shah",new Long(87876),".");


        for(int i=0;i<6;i++)
        {
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
        getActivity().setTitle("Explore");
    }
}


