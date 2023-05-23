package com.cankutboratuncer.alicisindan.activities.ui.main.profile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cankutboratuncer.alicisindan.R;
import com.cankutboratuncer.alicisindan.activities.ui.main.profile.recycleview_necessities.favorites.Favorites_Adapter;
import com.cankutboratuncer.alicisindan.activities.ui.main.profile.recycleview_necessities.favorites._Favorites;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavoritesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoritesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FavoritesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavoritesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FavoritesFragment newInstance(String param1, String param2) {
        FavoritesFragment fragment = new FavoritesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private RecyclerView recyclerView;
    private Favorites_Adapter favorite_adapter;

    // temporary
    private TextView item_count;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_favorites, container, false)

        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.favoriteFragment_recycleView_favorites);
        favorite_adapter = new Favorites_Adapter(_Favorites.manageData(),this.getActivity());

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new GridLayoutManager(this.getActivity(), 2);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(favorite_adapter);

        // temporary
        item_count = (TextView) view.findViewById(R.id.item_count);
        item_count.setText(Integer.toString(_Favorites.getItemCount()));

        return view;
    }
}