package com.example.bartek.badminton.Profile;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.TypedArray;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;

import com.example.bartek.badminton.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class EditProfileActivity extends AppCompatActivity{
    private Toolbar toolbar;

    //fields for upload image
    private static final int PICK_IMAGE_REQUEST=1;
    private FloatingActionButton choose_image;
    private ImageView profile_image;
    private Uri image_uri;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;

    //fields for pick data from dialog
    private Calendar calendar = Calendar.getInstance();
    private EditText edit_date;

    //fields for country spinner
    private TextView country_name;
    private ImageView country_flag;
    private Spinner country_spinner;
    private SpinnerAdapter spinnerAdapter;
    private String[] countryListISO;
    private String[] countryListName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        toolbar=findViewById(R.id.profile_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(Locale.getDefault().getCountry());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        choose_image=findViewById(R.id.edit_profile_choose_image);
        profile_image=findViewById(R.id.profile_image);
        storageReference= FirebaseStorage.getInstance().getReference( );
        choose_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateEditDate();
            }
        };

        edit_date=findViewById(R.id.edit_profile_date);
        edit_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(EditProfileActivity.this,date,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH))
                        .show();
            }
        });

        country_name = findViewById(R.id.spinner_country_item_name);
        country_flag = findViewById(R.id.spinner_country_item_flag);
        country_spinner = findViewById(R.id.edit_profile_country_spinner);

        // get country list in {ISO 3166 alpha 2} system from system Locale
        countryListISO=Locale.getISOCountries();
        countryListName=new String[countryListISO.length];

        // convert ISO country code to full country name
        for(int i=0;i<countryListISO.length;i++){
            countryListName[i]=new Locale("",countryListISO[i]).getDisplayCountry();
        }

        // get list of flags drawables declared in values/flags.xml array
        TypedArray typedArray = getResources().obtainTypedArray(R.array.flags);
        Toast.makeText(EditProfileActivity.this,"loc size: "+countryListISO.length+"   typ size:"+typedArray.length(),Toast.LENGTH_LONG).show();
        SpinnerAdapter adapter = new SpinnerAdapter(this, R.layout.spinner_country_item, countryListName, typedArray);
        country_spinner.setAdapter(adapter);

        // auto select country from Locale
        for(int i=0;i<countryListISO.length;i++) {
            if (countryListISO[i].equals(Locale.getDefault().getCountry())) {
                country_spinner.setSelection(i);
                break;
            }
        }

        country_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                profile_image.setImageDrawable(getDrawable(getResources()
                        .getIdentifier(countryListISO[parent.getSelectedItemPosition()]
                                .toLowerCase(),"drawable",getPackageName())));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void updateEditDate() {
        // Updates EditText field for birth date by date choose in calendar dialog
        DateFormat formatter = DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault());
        edit_date.setText(formatter.format(calendar.getTime()));
    }

    public void openFileChooser(){
        // Opens file chooser
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // Loads profile image to imageView
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==PICK_IMAGE_REQUEST && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            image_uri=data.getData();
            Picasso.get().load(image_uri).into(profile_image);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit_profile_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()){

            case R.id.action_save:

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
