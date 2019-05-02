package com.example.user.bingochat;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class RequestsFragment extends Fragment {
    private RecyclerView mRequestList;

    private DatabaseReference mFriendsDatabase;
    private DatabaseReference mUsersDatabase;
    private FirebaseAuth mAuth;
    private DatabaseReference LatLng;

    private String mCurrent_user_id;

    private View mMainView;
    private Button mAccept;
    private Button mDecline;
    private DatabaseReference mFriendReqDatabase;
    private DatabaseReference mFriendDatabase;
    private String mCurrent_state;
    private FirebaseUser mCurrent_user;



    public RequestsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mMainView = inflater.inflate(R.layout.fragment_requests, container, false);

        mRequestList = (RecyclerView) mMainView.findViewById(R.id.request_list);
        mAuth = FirebaseAuth.getInstance();

        mCurrent_user_id = mAuth.getCurrentUser().getUid();
        mCurrent_state = "not_friends";
        mFriendsDatabase = FirebaseDatabase.getInstance().getReference().child("Friend_req").child(mCurrent_user_id);
        mFriendsDatabase.keepSynced(true);
        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        mUsersDatabase.keepSynced(true);
  ;

        mRequestList.setHasFixedSize(true);
        ;
        mRequestList.setLayoutManager(new LinearLayoutManager(getContext()));
        //LatLng =FirebaseDatabase.getInstance().getReference().child("Users").child(mCurrent_user_id).child("LatLng");

        // Inflate the layout for this fragment





/*mAccept.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if (mCurrent_state.equals("req_sent")){
            mAccept.setText("Cancel Friend Request");

            mFriendReqDatabase.child(mCurrent_user.getUid()).child(mCurrent_user_id).removeValue()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            mFriendReqDatabase.child(mCurrent_user_id).child(mCurrent_user.getUid()).removeValue()
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {



                                            mAccept.setVisibility(View.INVISIBLE);
                                            mAccept.setEnabled(false);

                                        }
                                    });

                        }
                    });

        }
    }
});*/

        return mMainView;
    }


public  void initcontrol(){
    mAccept = (Button) mMainView.findViewById(R.id.accept_btn);

    mDecline = (Button) mMainView.findViewById(R.id.decline_btn);
    mAccept.setOnClickListener((View.OnClickListener)this);
    mDecline.setOnClickListener((View.OnClickListener)this);

}

    @Override
    public void onStart(){
        super.onStart();


        FirebaseRecyclerAdapter<Friends, RequestsFragment.FriendsViewHolder> friendsRecyclerViewAdapter = new FirebaseRecyclerAdapter<Friends,
                RequestsFragment.FriendsViewHolder>(

                Friends.class,
                R.layout.friend_request_layout,
               RequestsFragment.FriendsViewHolder.class,
                mFriendsDatabase
        ) {
            @Override
            protected void populateViewHolder(final RequestsFragment.FriendsViewHolder friendsViewHolder, final Friends friends, final int i) {



                final String list_user_id = getRef(i).getKey();

                mUsersDatabase.child(list_user_id).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        final String userName = dataSnapshot.child("name").getValue().toString();
                        String userThumb = dataSnapshot.child("thumb_image").getValue().toString();



                        friendsViewHolder.setName(userName);
                        friendsViewHolder.setUserImage(userThumb, getContext());



                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        };

        mRequestList.setAdapter(friendsRecyclerViewAdapter);


    }


  /*  public void onClick(View v) {
        switch (v.getId()){
            case R.id.accept_btn:
                Toast.makeText(getActivity(), "This is my Toast message!",
                        Toast.LENGTH_LONG).show();
                if (mCurrent_state.equals("sent")) {
                    mAccept.setText("Cancel Friend Request");
                    mFriendReqDatabase.child(mCurrent_user.getUid()).child(mCurrent_user_id).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {


                                mFriendReqDatabase.child(mCurrent_user_id).child(mCurrent_user.getUid()).removeValue()
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {



                                                mAccept.setVisibility(View.INVISIBLE);
                                                mAccept.setEnabled(false);

                                            }
                                        });

                        }
                    });
                }
break;

            case R.id.decline_btn:
                Toast.makeText(getActivity(),"this is my toast message",Toast.LENGTH_LONG).show();
                break;
        }

    }*/


    public static class FriendsViewHolder extends RecyclerView.ViewHolder {

        static View mView;

        public FriendsViewHolder(View itemView){
            super(itemView);

            mView = itemView;

        }



        public void setName(String name){

            TextView userNameView = (TextView) mView.findViewById(R.id.freind_single_name);
            userNameView.setText(name);

        }

        public static void setUserImage(String thumb_image, Context ctx){

            CircleImageView userImageView = (CircleImageView) mView.findViewById(R.id.friend_single_image);
            Picasso.with(ctx).load(thumb_image).placeholder(R.drawable.default_avatar).into(userImageView);

        }



        }

    }





