package com.cankutboratuncer.alicisindan.activities.ui.main.profile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cankutboratuncer.alicisindan.R;
import com.cankutboratuncer.alicisindan.activities.ui.main.advertisement.advertisement.AdvertisementFragment;
import com.cankutboratuncer.alicisindan.activities.ui.main.profile.recycleview_necessities.favorites.Favorites_Adapter;
import com.cankutboratuncer.alicisindan.activities.ui.main.profile.recycleview_necessities.favorites._Favorites;
import com.cankutboratuncer.alicisindan.activities.ui.main.profile.recycleview_necessities.myposts.MyPosts_Adapter;
import com.cankutboratuncer.alicisindan.activities.ui.main.profile.recycleview_necessities.myposts._MyPosts;
import com.cankutboratuncer.alicisindan.activities.utilities.Constants;
import com.cankutboratuncer.alicisindan.activities.utilities.LocalSave;

import org.w3c.dom.Text;

public class FavoritesFragment extends Fragment {

    private RecyclerView recyclerView;
    private Favorites_Adapter favorite_adapter;
    // temporary
    private TextView item_count;
    private LocalSave localSave;

    public FavoritesFragment() {
        // Required empty public constructor
    }

    public static FavoritesFragment newInstance() {
        FavoritesFragment fragment = new FavoritesFragment();
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_favorites, container, false)

        // get local save
        localSave = new LocalSave(getContext());
        _Favorites.local_save = localSave;

        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.favoriteFragment_recycleView_favorites);
        favorite_adapter = new Favorites_Adapter(_Favorites.manageData(),this.getActivity());

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new GridLayoutManager(this.getActivity(), 2);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(favorite_adapter);

        item_count = (TextView) view.findViewById(R.id.item_count);
        item_count.setText(Integer.toString(_Favorites.getItemCount()) + " Items");

        favorite_adapter.setOnItemClickListener(new Favorites_Adapter.OnItemClickListener() {
            @Override
            public void OnItemClick(_Favorites favorites, int position) {
                Fragment fragment = AdvertisementFragment.newInstance(favorites.getAdID());

                Bundle args = new Bundle();
                args.putString(Constants.KEY_ADVERTISEMENT_ID, favorites.getAdID());
                args.putString(Constants.KEY_ADVERTISEMENT_USERNAME,favorites.getUser());
                fragment.setArguments(args);
                loadFragment(fragment);


                Log.d(Integer.toString(position), "Favorites clicked");
            }
        });

        return view;
    }

    public void loadFragment(Fragment fragment) {
        //to attach fragment
        getParentFragmentManager().beginTransaction().replace(R.id.mainActivity_frameLayout_main, fragment).addToBackStack(null).commit();
    }
}