package com.example.vakz.twitterapp;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AuthorFilteredFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AuthorFilteredFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AuthorFilteredFragment extends Fragment {

    private String author;
    private ListView lw;


    private OnFragmentInteractionListener mListener;
    // TODO: Rename and change types and number of parameters
    public static AuthorFilteredFragment newInstance() {
        AuthorFilteredFragment fragment = new AuthorFilteredFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public AuthorFilteredFragment() {
        // Required empty public constructor
    }

    public void setFilter(String author) {
        this.author = author;
    }

    private void authorChanged() {
        ArrayList<Message> original = ((ViewMessagesActivity) getActivity()).getMessages().getMessages();
        ArrayList<Message> filtered = new ArrayList<>();
        for (int i = 0; i < original.size(); ++i) {
            if (original.get(i).author.equals(author))
            filtered.add(original.get(i));
        }
        MessageAdapter ma = new MessageAdapter(getContext(), filtered);
        lw.setAdapter(ma);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View w = inflater.inflate(R.layout.fragment_author_filtered, container, false);
        lw = (ListView)(w.findViewById(R.id.MessagesView));
        return w;
    }

    @Override
    public void onResume() {
        super.onResume();
        authorChanged();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
    }

}
