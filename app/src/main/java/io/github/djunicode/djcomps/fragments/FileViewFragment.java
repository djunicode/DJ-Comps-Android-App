package io.github.djunicode.djcomps.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.AutoTransition;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Date;

import io.github.djunicode.djcomps.R;
import io.github.djunicode.djcomps.adapters.FileAdapter;
import io.github.djunicode.djcomps.database.data.File;
import io.github.djunicode.djcomps.dialogs.FileUploadDialog;

import static android.app.Activity.RESULT_OK;

public class FileViewFragment extends Fragment {

    public enum Type { Explore, Uploads, Stars, Downloads }

    public static final String FRAGMENT_TYPE_STR = "type_of_fragment";
    private static final int FILE_PICK_REQUEST_CODE = 3487;

    FileAdapter fileAdapter;
    Button sortByButton;

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
        fileAdapter = new FileAdapter(getContext(), false);

        View utilCard = view.findViewById(R.id.fileview_utilization_card);
        View filterbar = view.findViewById(R.id.fileview_filterbar);
        View uploadFAB = view.findViewById(R.id.fileview_upload_fab);
        sortByButton = view.findViewById(R.id.file_view_sort_by);

        Bundle argBundle = getArguments();
        Type fragmentType = (Type) (argBundle != null ? argBundle.getSerializable(FRAGMENT_TYPE_STR) : Type.Explore);

        setTitle(fragmentType);

        if(fragmentType == Type.Explore) setHasOptionsMenu(true);

        if(fragmentType == Type.Explore || fragmentType == Type.Stars || fragmentType == Type.Downloads){
            utilCard.setVisibility(View.GONE);
            uploadFAB.setVisibility(View.GONE);
        }
        else if(fragmentType == Type.Uploads){
            filterbar.setVisibility(View.GONE);
            uploadFAB.setOnClickListener(uploadButtonOnClick());
        }

        sortByButton.setOnClickListener(sortByButtonOnClick());

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(fileAdapter);


        prepareFiles();

        return view;
    }

    private View.OnClickListener uploadButtonOnClick(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("*/*");
                startActivityForResult(intent, FILE_PICK_REQUEST_CODE);
            }
        };
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == FILE_PICK_REQUEST_CODE && resultCode == RESULT_OK && getContext() != null){
            FileUploadDialog dialog = new FileUploadDialog(getContext(), data);
            dialog.show();
        }
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

    private View.OnClickListener sortByButtonOnClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] sortByCategories = {
                        "Name: A → Z",
                        "Name: Z → A",
                        "Most Recent",
                        "Least Recent",
                        "Most Popular",
                        "Least Popular"

                };
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setItems(sortByCategories, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position) {
                        boolean isAscending = true;
                        String sortBy;
                        switch (position){
                            case 0:
                                sortByButton.setText("Name ↑");
                                sortBy = "name";
                                break;
                            case 1:
                                sortByButton.setText("Name ↓");
                                isAscending = false;
                                sortBy = "name";
                                break;
                            case 2:
                                sortByButton.setText("Date ↑");
                                sortBy = "date";
                                break;
                            case 3:
                                sortByButton.setText("Date ↓");
                                isAscending = false;
                                sortBy = "date";
                                break;
                            case 4:
                                sortByButton.setText("Popular ↑");
                                sortBy = "popularity";
                                break;
                            case 5:
                                sortByButton.setText("Popular ↓");
                                isAscending = false;
                                sortBy = "popularity";
                                break;
                        }

                        //TODO: make api call to update recycler view using isAscending and sortBy values
                    }
                });
                builder.create().show();
            }
        };
    }


    private void prepareFiles() {

        File ar[];
        ar=new File[10];

        ar[0]= new File(new Long(1234567891),new Long(6122),new Long(21),5,4,"pdf","eccf notes",new Date(01/01/1997),"fgdfg", false, false);
        ar[1]= new File(new Long(1234567891),new Long(6122),new Long(21),5,4,"pdf","dlda notes",new Date(01/01/1997),"fgdfg", false, false);
        ar[2]= new File(new Long(1234567891),new Long(6122),new Long(21),5,4,"pdf","cg notes",new Date(01/01/1997),"fgdfg", false, false);
        ar[3]= new File(new Long(1234567891),new Long(6122),new Long(21),5,4,"pdf","sdadf notes",new Date(01/01/1997),"fgdfg", false, false);
        ar[4]= new File(new Long(1234567891),new Long(6122),new Long(21),5,4,"pdf","gdfghgfh notes",new Date(01/01/1997),"fgdfg", false, false);
        ar[5]= new File(new Long(1234567891),new Long(6122),new Long(21),5,4,"pdf","xxx notes",new Date(01/01/1997),"fgdfg", false, false);

        for(int i=0; i<6; i++){
            fileAdapter.addFile(ar[i]);
        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_explore, menu);
        menu.getItem(0).setIcon(R.drawable.ic_person_outline_white_24dp);
        menu.getItem(0).setTitle("Explore by users");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_explore_by:
                FragmentManager fragmentManager = getFragmentManager();
                if(fragmentManager != null){
                    fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    Fragment fragment = UserViewFragment.getInstance();
                    fragment.setEnterTransition(new AutoTransition());
                    transaction.replace(R.id.content_frame, fragment);
                    transaction.commit();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
