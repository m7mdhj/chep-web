package com.example.cheapweb;


import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
public class UploadingItems extends AppCompatActivity {

            private Button btnchoose, btnUpload;
            private EditText itemName, itemPrice, itemInfo, Link1, Link2, Link3, PriceInLink1, PriceInLink2, PriceInLink3;
            private ImageView imageView;

            private Uri filePath;
            //Firebase
            private FirebaseStorage storage;
            private StorageReference storageReference;
            private DatabaseReference databaseReference;

            private final int PICK_IMAGE_REQUEST = 71;
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override

                protected void onCreate(Bundle savedInstanceState) {
                    super.onCreate(savedInstanceState);
                    setContentView(R.layout.activity_uploading_items);

                ActionBar actionBar = getSupportActionBar();
                actionBar.setTitle("Upload Items");
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setDisplayShowHomeEnabled(true);
                //Initialize Views
                btnchoose = (Button) findViewById(R.id.btnChos);
                btnUpload = (Button) findViewById(R.id.btnUplod);
                imageView = (ImageView) findViewById(R.id.imgView);
                //Firebase
                storage = FirebaseStorage.getInstance();
                storageReference = storage.getReference();
                itemName=findViewById(R.id.editname);
                itemPrice=findViewById(R.id.editprice);
                itemInfo=findViewById(R.id.editinfo);
                Link1=findViewById(R.id.editlink1);
                Link2=findViewById(R.id.editlink2);
                Link3=findViewById(R.id.editlink3);
                PriceInLink1=findViewById(R.id.editprice_link1);
                PriceInLink2=findViewById(R.id.editprice_link2);
                PriceInLink3=findViewById(R.id.editprice_link3);
                databaseReference= FirebaseDatabase.getInstance().getReference("items");


                Initialize();


            }



            private void Initialize() {
                btnchoose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        chooseImage();
                    }
                });

                btnUpload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        uploadImage();
                    }
                });
            }

            private void uploadImage() {
                if(filePath != null)
                {
                    final ProgressDialog progressDialog = new ProgressDialog(this);
                    progressDialog.setTitle("Uploading...");
                    progressDialog.show();
                    StorageReference storageReference2=storageReference.child("items_images/"+ System.currentTimeMillis()+"."+ getFileExtension(filePath));
                    storageReference2.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            String itemname=itemName.getText().toString().trim();
                            String itemprice=itemPrice.getText().toString().trim();
                            String iteminfo=itemInfo.getText().toString().trim();
                            String link1=Link1.getText().toString().trim();
                            String link2=Link2.getText().toString().trim();
                            String link3=Link3.getText().toString().trim();
                            String PriceLink1=PriceInLink1.getText().toString().trim();
                            String PriceLink2=PriceInLink2.getText().toString().trim();
                            String PriceLink3=PriceInLink3.getText().toString().trim();
                            Toast.makeText( UploadingItems.this, "uploaded!", Toast.LENGTH_SHORT ).show();
                            Task<Uri> uri = taskSnapshot.getStorage().getDownloadUrl();
                            while(!uri.isComplete());
                            Uri url = uri.getResult();
                            String ImgaeuploadId=databaseReference.push().getKey();
                            Model uploadImage=new Model(ImgaeuploadId, url.toString(),itemname, itemprice, iteminfo, link1, link2, link3,
                                    PriceLink1, PriceLink2, PriceLink3);
                            databaseReference.child(ImgaeuploadId).setValue(uploadImage);
                            progressDialog.dismiss();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(UploadingItems.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });


                }
                if(filePath== null)
                    Toast.makeText(UploadingItems.this, "select an image...", Toast.LENGTH_SHORT).show();

            }

            private String getFileExtension(Uri uri){
                ContentResolver cR = getContentResolver();
                MimeTypeMap mime = MimeTypeMap.getSingleton();
                return mime.getExtensionFromMimeType( cR.getType( uri ) );
            }
            private void chooseImage() {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
            @Override
            protected void onActivityResult(int requestCode, int resultCode, Intent data){
                super.onActivityResult(requestCode, resultCode, data);
                if(resultCode == RESULT_OK && requestCode == PICK_IMAGE_REQUEST){
                    filePath = data.getData();
                    imageView.setImageURI( filePath );

           /* try {
                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
                imageView.setImageBitmap(bitmap);
            }
            catch (Exception e){
                Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }

            */
                }
            }
            private String getImageType(String name){
                String[] type = name.split( "\\." );
                return type[1];
            }

}
