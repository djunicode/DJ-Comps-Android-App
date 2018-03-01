package io.github.djunicode.djcomps;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.github.djunicode.djcomps.Database.Data.File;

import static io.github.djunicode.djcomps.LoginActivity.SP_LOGIN_ID;
import static io.github.djunicode.djcomps.LoginActivity.SP_LOGIN_LOGGED_IN_STATE;


public class HomeActivity extends AppCompatActivity implements OnItemClickListener {

    private RecyclerView recyclerView;
    private DocumentsAdapter adapter;
    private List<File> documents;

    private RecyclerView deadlineView;
    private DeadlineAdapter deadlineadapter;
    private List<File> deadlinelist;




    @Override
    public void onClick(View view, int position) {
        // The onClick implementation of the RecyclerView item click
        final File doc = documents.get(position);
        Intent i = new Intent(this, FileDetails.class);
        i.putExtra("filename", doc.name);
        i.putExtra("id", doc.file_id);
        i.putExtra("sap",doc.sap_id);
        i.putExtra("time",doc.time_added);
        i.putExtra("size",doc.size);
        i.putExtra("stars",doc.no_of_stars);
        i.putExtra("downloads",doc.no_of_downloads);
        i.putExtra("type",doc.type);
        i.putExtra("description",doc.description);
        // i.putExtra("image", doc.getImg());
        //i.putExtra("subject", doc.getSubject());
        startActivity(i);
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        new LoginTask(this, progressDialog).execute();

        adapter = new DocumentsAdapter(this,documents);
        adapter.setClickListener(this);

        Button explore = findViewById(R.id.uploads);
        explore.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), NavActivity.class);
                startActivityForResult(myIntent, 0);

            }

        });



        deadlineView = findViewById(R.id.deadline_view);
        deadlinelist = new ArrayList<>();
        deadlineadapter = new DeadlineAdapter(this, deadlinelist);


        recyclerView = findViewById(R.id.recycler_view);
        documents = new ArrayList<>();
        adapter = new DocumentsAdapter(this, documents);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        deadlineView.setLayoutManager(mLayoutManager);



        //  deadlineView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        deadlineView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        deadlineView.setItemAnimator(new DefaultItemAnimator());
        deadlineView.setAdapter(deadlineadapter);

        prepareDeadline();

        // getSupportActionBar().setTitle("Horizontal Recycler View");
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        prepareStaredItems();



    }



    private void prepareDeadline() {


        File ar[];
        ar=new File[10];
        ar[0]= new File(new Long(1234567891),new Long(6122),new Long(21),5,4,"pdf","eccf notes",new Date(01/01/1997),"fgdfg");
        ar[1]= new File(new Long(1234567891),new Long(6122),new Long(21),5,4,"pdf","dlda notes",new Date(01/01/1997),"fgdfg");
        ar[2]= new File(new Long(1234567891),new Long(6122),new Long(21),5,4,"pdf","cg notes",new Date(01/01/1997),"fgdfg");
        ar[3]= new File(new Long(1234567891),new Long(6122),new Long(21),5,4,"pdf","sdadf notes",new Date(01/01/1997),"fgdfg");
        ar[4]= new File(new Long(1234567891),new Long(6122),new Long(21),5,4,"pdf","gdfghgfh notes",new Date(01/01/1997),"fgdfg");
        ar[5]= new File(new Long(1234567891),new Long(6122),new Long(21),5,4,"pdf","xxx notes",new Date(01/01/1997),"fgdfg");

        for(int i=0;i<6;i++)
        {
            deadlinelist.add(ar[i]);

        }






        deadlineadapter.notifyDataSetChanged();
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


    private static class LoginTask extends AsyncTask<Void, Void, Void> {

        WeakReference<Activity> wrActivity;
        WeakReference<Context> wrContext;
        ProgressDialog progressDialog;

        LoginTask(Activity activity, ProgressDialog pd){
            wrActivity = new WeakReference<>(activity);
            wrContext = new WeakReference<>(activity.getApplicationContext());
            this.progressDialog = pd;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage(wrContext.get().getString(R.string.login_logging_in));
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            SharedPreferences prefs = wrContext.get().getSharedPreferences(SP_LOGIN_ID, Context.MODE_PRIVATE);
            boolean isLoggedIn = prefs.getBoolean(SP_LOGIN_LOGGED_IN_STATE, false);

            //TODO: check token with server

            if(!isLoggedIn){
                wrActivity.get().finish();
                wrActivity.get().startActivity(new Intent(wrActivity.get(), LoginActivity.class));
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPreExecute();
            progressDialog.dismiss();
        }

    }

}
