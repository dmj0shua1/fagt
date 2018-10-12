package jediarstudios.fagt;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;



/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link donation_dialog.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link donation_dialog#newInstance} factory method to
 * create an instance of this fragment.
 */
public class donation_dialog extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String license_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA5C2oP2K6K0l9GEUeH7rmeAYOT9BPIkRHPxjNCMpsyk0vF64JraPRB0IsQkO0XOme0uTpRSz2b5Z5u/okRrxpzenrgI1tl+0wOif2PeDjCx8BnqP8xYTm1GMuEqWjk/JKCSmjsKDeQ9h9fthMGIGVPdcfXvO6Gbm4cEN8Hy/dFyZeGp1umRMld4sD6p3TtvEr6PNkYxov86X0p03QWyAqbLkpAStJUNGahuBQ5uDY/bsIAvoDQgj6MpQcrQvAdCD3vvdNJIR7VUqGbIwYgrelDAk3+a4LynSf1O8TXQx24rOIiUSKrSUbHMhgrTI5QGrSCeAEsK78lbdv4H0tkxIHtwIDAQAB";
  //  BillingProcessor bp;

    private OnFragmentInteractionListener mListener;

    Button btnDonateNow;

    View rootView;

    public donation_dialog() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        try {
            mListener = (OnFragmentInteractionListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement OnFragmentInteractionListener");
        }


    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment donation_dialog.
     */
    // TODO: Rename and change types and number of parameters
    public static donation_dialog newInstance(String param1, String param2) {
        donation_dialog fragment = new donation_dialog();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_donation_dialog, container, false);
    /*    bp = BillingProcessor.newBillingProcessor(getContext(), null, this);
        bp.initialize();*/
        btnDonateNow = rootView.findViewById(R.id.btnDonateNow);
        addListenerOnButton();
        // Inflate the layout for this fragment
        return rootView;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }
    public void addListenerOnButton() {

        //btnDonateNow = rootView.findViewById(R.id.btnDonateNow);

        btnDonateNow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

              // bp.purchase(getActivity(), "android.test.purchased");

                Toast.makeText(getActivity(), "donate button", Toast.LENGTH_SHORT).show();

            }

        });

    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }



}
