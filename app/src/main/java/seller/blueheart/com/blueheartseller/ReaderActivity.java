package seller.blueheart.com.blueheartseller;

/**
 * Created by Subham on 13-07-2017.
 */

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import static android.R.id.list;

public class ReaderActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private Button scan_btn;
    private ImageView imageHolder;
    private final int requestCode = 20;
    private StorageReference mPhotosReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader);
        scan_btn = (Button) findViewById(R.id.scan_btn);
        final Activity activity = this;
        imageHolder = (ImageView)findViewById(R.id.captured_photo);
        Button capturedImageButton = (Button)findViewById(R.id.photo_button);
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        mPhotosReference = firebaseStorage.getReference().child("product");
        capturedImageButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(photoCaptureIntent, requestCode);
            }
        });
        scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(true);
                integrator.setBarcodeImageEnabled(true);
                integrator.initiateScan();
            }
        });
        Spinner sp1 = (Spinner) findViewById(R.id.spinner1);
        Spinner sp2 = (Spinner) findViewById(R.id.spinner2);
        sp1.setOnItemSelectedListener(this);
        String s1 = String.valueOf(sp1.getSelectedItem());
        if(s1.contentEquals("Automobile")) {
            List<String> list = new ArrayList<String>();
            list.add("AUDI");
            list.add("ROLLS ROYCE ");
            list.add("BUGATTI ");
            list.add("FERRARI");
            ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list);
            dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sp2.setAdapter(dataAdapter1);
        }
        if(s1.contentEquals("Business Services")) {
            List<String> list1 = new ArrayList<String>();
            list1.add("ZOHO");
            list1.add("IBM");
            list1.add("HCL");
            list1.add("HP");
            ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list);
            dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter2.notifyDataSetChanged();
            sp2.setAdapter(dataAdapter2);
        }
        if(s1.contentEquals("Computers")) {
            List<String> list2 = new ArrayList<String>();
            list2.add("HP");
            list2.add("DELL");
            list2.add("LENOVO ");
            list2.add("COMPAQ");
            ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list);
            dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter3.notifyDataSetChanged();
            sp2.setAdapter(dataAdapter3);
        }
        if(s1.contentEquals("Education")) {
            List<String> list3 = new ArrayList<String>();
            list3.add("SRM UNVERSITY");
            list3.add("VIT UNIVERSITY");
            list3.add("IIT MADRAS ");
            list3.add("ANNA UNIVERSITY");
            ArrayAdapter<String> dataAdapter4 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list);
            dataAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter4.notifyDataSetChanged();
            sp2.setAdapter(dataAdapter4);
        }
        if(s1.contentEquals("Personal")) {
            List<String> list4 = new ArrayList<String>();
            list4.add("PEN ");
            list4.add("PENCIL");
            list4.add("ERASER ");
            list4.add("SHARPENER");
            ArrayAdapter<String> dataAdapter5 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list);
            dataAdapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter5.notifyDataSetChanged();
            sp2.setAdapter(dataAdapter5);
        }
        if(s1.contentEquals("Travel")) {
            List<String> list5 = new ArrayList<String>();
            list5.add("DUBAI");
            list5.add("HONG KONG ");
            list5.add("U.S.A. ");
            list5.add("GERMANY");
            ArrayAdapter<String> dataAdapter6 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list);
            dataAdapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter6.notifyDataSetChanged();
            sp2.setAdapter(dataAdapter6);
        }




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null){
            if(result.getContents()==null){
                Toast.makeText(this, "You cancelled the scanning", Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(this, result.getContents(),Toast.LENGTH_LONG).show();
                EditText editText = (EditText) findViewById(R.id.editText);
                editText.setText(result.getContents());
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
        if(this.requestCode == requestCode && resultCode == RESULT_OK){
            Bitmap bitmap = (Bitmap)data.getExtras().get("data");
            imageHolder.setImageBitmap(bitmap);
            imageHolder.setDrawingCacheEnabled(true);
            imageHolder.buildDrawingCache();
            Bitmap bitmap1 = imageHolder.getDrawingCache();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap1.compress(Bitmap.CompressFormat.JPEG,100,baos);
            byte[] data1 = baos.toByteArray();
            UploadTask uploadTask = mPhotosReference.putBytes(data1);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ReaderActivity.this, ""+e, Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    Toast.makeText(ReaderActivity.this, ""+downloadUrl, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
