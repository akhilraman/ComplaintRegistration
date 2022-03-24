package com.example.registration;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    ListView listview;
    public static ArrayList arrayList;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    public static CustomRow adapter;
    public static ArrayList adapterData;

    FloatingActionButton mainbutton,registerbutton,logoutbutton;
    Animation fabOpen,fabClose,rotateForward,rotateBackward;
    boolean isOpen=false;


    DatabaseReference referenceExpert;





    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final ProgressBar simpleProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("Complaints");

        mainbutton=(FloatingActionButton) view.findViewById(R.id.floatingActionButton);
        registerbutton=(FloatingActionButton) view.findViewById(R.id.registerbutton);
        logoutbutton=(FloatingActionButton) view.findViewById(R.id.logout_button);

        fabOpen= AnimationUtils.loadAnimation(getContext(),R.anim.from_buttom_anim);
        fabClose= AnimationUtils.loadAnimation(getContext(),R.anim.to_buttom_anim);
        rotateForward= AnimationUtils.loadAnimation(getContext(),R.anim.rotate_open_anim);
        rotateBackward= AnimationUtils.loadAnimation(getContext(),R.anim.rotate_close_anim);

        mainbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animatefab();

            }
        });


        registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(),RegistrationActivity.class);
                startActivity(i);
            }
        });

        logoutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getContext(),MainActivity.class));
            }
        });

        listview=view.findViewById(R.id.list);
        listview.setItemsCanFocus(false);
        arrayList = new ArrayList<Complaint>();
        adapterData = new ArrayList<Complaint>();


        adapter = new CustomRow(getContext(), arrayList);
        listview.setAdapter(adapter);
        simpleProgressBar.setVisibility(View.VISIBLE);

        //arrayList.add(new Complaint("123","this is title","akhil","19bce1564","ragging","rahul","registered"));
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!arrayList.isEmpty()){
                        arrayList.clear();
                    }
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Complaint complaint=dataSnapshot.getValue(Complaint.class);

                    if(complaint.getComplaintFrom().getEmail().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
                        arrayList.add(complaint);
                    }

                    adapter.notifyDataSetChanged();
                    listview.requestLayout();
                    simpleProgressBar.setVisibility(View.INVISIBLE);

                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }

        });



        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Complaint present = (Complaint) arrayList.get(position);
                ComplaintDetailsFragment complaintDetailsFragment= new ComplaintDetailsFragment(present);
                complaintDetailsFragment.show(((FragmentActivity)getContext()).getSupportFragmentManager(), complaintDetailsFragment.getTag());
                //Toast.makeText(getContext(), "hii", Toast.LENGTH_SHORT).show();
            }
        });




    }
    private void animatefab(){
        if (isOpen){
            mainbutton.startAnimation(rotateForward);
            registerbutton.startAnimation(fabClose);
            logoutbutton.startAnimation(fabClose);
            registerbutton.setClickable(false);
            logoutbutton.setClickable(false);
            isOpen=false;
        }
        else{
            mainbutton.startAnimation(rotateBackward);
            registerbutton.startAnimation(fabOpen);
            logoutbutton.startAnimation(fabOpen);
            registerbutton.setClickable(true);
            logoutbutton.setClickable(true);
            isOpen=true;
        }
    }



}
