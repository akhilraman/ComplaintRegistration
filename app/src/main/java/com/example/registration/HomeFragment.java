package com.example.registration;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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

    DatabaseReference referenceExpert;





    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("Complaints");

        FloatingActionButton registerButton=view.findViewById(R.id.registerButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(),RegistrationActivity.class);
                startActivity(i);
            }
        });

        listview=view.findViewById(R.id.list);

        listview.setItemsCanFocus(false);
        arrayList = new ArrayList<Complaint>();
        adapterData = new ArrayList<Complaint>();


        adapter = new CustomRow(getContext(), arrayList);
        listview.setAdapter(adapter);


        //arrayList.add(new Complaint("123","this is title","akhil","19bce1564","ragging","rahul","registered"));
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Complaint complaint=dataSnapshot.getValue(Complaint.class);

                            arrayList.add(complaint);
                            adapter.notifyDataSetChanged();
                            listview.requestLayout();
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


}
