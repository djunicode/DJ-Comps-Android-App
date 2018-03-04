package io.github.djunicode.djcomps.fragments;

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

import io.github.djunicode.djcomps.R;
import io.github.djunicode.djcomps.adapters.UserAdapter;
import io.github.djunicode.djcomps.database.data.User;

public class UserViewFragment extends Fragment {

    UserAdapter userAdapter;
    Button sortByButton;

    public static UserViewFragment getInstance() {
        return new UserViewFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_userview, container, false);
        setHasOptionsMenu(true);

        FragmentActivity act = getActivity();
        if(act != null) getActivity().setTitle("Users");

        RecyclerView recyclerView = view.findViewById(R.id.userview_rv);
        userAdapter = new UserAdapter(getContext());

        sortByButton = view.findViewById(R.id.file_view_sort_by);
        sortByButton.setOnClickListener(sortByButtonOnClick());

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(userAdapter);

        prepareUsers();

        return view;
    }

    private Button.OnClickListener sortByButtonOnClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO:
                if(view.getTag() == "ascending"){
                    sortByButton.setText("Name ↓");
                    view.setTag("descending");
                }
                else{
                    sortByButton.setText("Name ↑");
                    view.setTag("ascending");
                }
                //TODO: also make api call to get users in ascending or descending order
            }
        };
    }

    private void prepareUsers() {

        User ar[];
        ar=new User[10];

        ar[0]= new User(60000000000l, "XYZ", "Abcdef Ghijkl", 1l, "");
        ar[1]= new User(60000000001l, "ABC", "Xyzqbc Defg", 2l, "");

        for(int i=0; i<2; i++){
            userAdapter.addUser(ar[i]);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_explore, menu);
        menu.getItem(0).setIcon(R.drawable.ic_folder_open_white_24dp);
        menu.getItem(0).setTitle("Explore Files");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_explore_by:
                FragmentManager fragmentManager = getFragmentManager();
                if(fragmentManager != null){
                    fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    Fragment fragment = FileViewFragment.getInstance(FileViewFragment.Type.Explore);
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
