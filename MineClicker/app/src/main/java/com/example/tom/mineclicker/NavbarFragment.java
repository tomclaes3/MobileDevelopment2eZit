package com.example.tom.mineclicker;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class NavbarFragment extends Fragment {

    Button mainButton;
    Button navigateToHighScores;
    Button navigateToUpgreade;
    Button navigatetoMiners;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_navbar, container , false);
        navigateToHighScores = (Button) view.findViewById(R.id.highscores);
        navigatetoMiners = (Button) view.findViewById(R.id.contacts);
        navigateToUpgreade = (Button) view.findViewById(R.id.upgrades);
        mainButton = (Button) view.findViewById(R.id.main);



        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).setViewPagerContent(0);
            }
        });
        navigatetoMiners.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).setViewPagerContent(1);
            }
        });
        navigateToUpgreade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).setViewPagerContent(2);
            }
        });

        navigateToHighScores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).setViewPagerContent(3);
            }
        });

        return view;
    }
}
