package com.samir.andrew.myticket.views.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.samir.andrew.myticket.R;
import com.samir.andrew.myticket.interfaces.InterfaceAddDataToFirebase;
import com.samir.andrew.myticket.interfaces.InterfaceGetDataFromFirebase;
import com.samir.andrew.myticket.models.ModelChair;
import com.samir.andrew.myticket.models.ModelProfile;
import com.samir.andrew.myticket.utlities.DataEnum;
import com.samir.andrew.myticket.utlities.HandleAddDataToFirebase;
import com.samir.andrew.myticket.utlities.HandleGetDataFromFirebase;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Profile extends AppCompatActivity implements InterfaceAddDataToFirebase, InterfaceGetDataFromFirebase {

    @Bind(R.id.edtProfileName)
    EditText edtProfileName;

    @Bind(R.id.edtProfileEmail)
    EditText edtProfileEmail;

    @Bind(R.id.edtProfileChurch)
    EditText edtProfileChurch;

    @OnClick(R.id.tvProfileBirthdate)
    public void onClicktvProfileBirthdate() {
        // TODO submit data to server...
    }

    @OnClick(R.id.btnSave)
    public void onClickbtnSave() {
        if (validation()) {
            ModelProfile modelProfile = new ModelProfile();
            modelProfile.setChurch(edtProfileChurch.getText().toString());
            modelProfile.setMail(edtProfileEmail.getText().toString());
            modelProfile.setMobile(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());
            modelProfile.setName(edtProfileName.getText().toString());
            HandleAddDataToFirebase.getInstance(this).callAddProfileData(DataEnum.callAddProfileData.name(), modelProfile);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        HandleAddDataToFirebase.getInstance(this).setClickDialogListener(this);
        HandleGetDataFromFirebase.getInstance(this).setGetDataFromFirebaseInterface(this);
        HandleGetDataFromFirebase.getInstance(this).callCheckProfileData(DataEnum.callCheckProfile.name());
    }

    private boolean validation() {

        boolean valid = true;

        if (edtProfileName.getText().toString().trim().length() == 0) {
            edtProfileName.setError("حقل مطلوب");
            valid = false;
        }
        if (edtProfileChurch.getText().toString().trim().length() == 0) {
            edtProfileChurch.setError("حقل مطلوب");
            valid = false;
        }

        if (edtProfileEmail.getText().toString().trim().length() == 0) {
            edtProfileEmail.setError("حقل مطلوب");
            valid = false;
        } else if (!isValidEmail(edtProfileEmail.getText().toString().trim())) {
            edtProfileEmail.setError("أدخل بريد اليكتونى صحيح");
            valid = false;
        }

        return valid;
    }


    private boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    @Override
    public void onDataAddedSuccess(String flag) {
        if (flag.equals(DataEnum.callAddProfileData.name())) {
            finish();
        }
    }

    @Override
    public void onDataAddedFailed(String flag) {

    }

    @Override
    public void onDataAddedRepeated(String flag) {

    }

    @Override
    public void onGetDataFromFirebase(DataSnapshot dataSnapshot, String flag) {
        if (flag.equals(DataEnum.callCheckProfile.name()) && dataSnapshot.exists()) {

            ModelProfile modelProfile = dataSnapshot.child("details").getValue(ModelProfile.class);
            edtProfileName.setText(modelProfile.getName());
            edtProfileEmail.setText(modelProfile.getMail());
            edtProfileChurch.setText(modelProfile.getChurch());
        }
    }

    @Override
    public void onGetStageChairs(List<ModelChair> modelChairLists, String flag) {

    }

    @Override
    public void onChairChanged(ModelChair modelChair, String flag) {

    }
}
