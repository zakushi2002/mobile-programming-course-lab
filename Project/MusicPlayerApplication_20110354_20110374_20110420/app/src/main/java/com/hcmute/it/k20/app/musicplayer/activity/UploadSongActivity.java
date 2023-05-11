package com.hcmute.it.k20.app.musicplayer.activity;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.hcmute.it.k20.app.musicplayer.MainActivity;
import com.hcmute.it.k20.app.musicplayer.R;
import com.hcmute.it.k20.app.musicplayer.entity.Song;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class UploadSongActivity extends AppCompatActivity {
    private static final int IMAGE_PICK_REQUEST = 1;
    private static final int AUDIO_PICK_REQUEST = 2;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    String mp3FileName, imageName;
    TextView tvMp3Name;
    Button btnUploadImage, btnChooseMp3, btnUpload;
    EditText edtTitleSong, edtArtistSong;
    Uri imageUri, mp3Uri;
    long maxId = 0L;
    UploadTask uploadTaskMp3, uploadTaskImage;
    public static InputStream streamImg;
    ImageView imageView;
    Song song = new Song();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_upload_song);

        btnUploadImage = findViewById(R.id.button_upload_image);
        btnChooseMp3 = findViewById(R.id.button_choose_mp3);
        btnUpload = findViewById(R.id.button_upload);
        btnUploadImage.setOnClickListener(view -> openFileChooser("image/*"));
        btnChooseMp3.setOnClickListener(view -> openFileChooser("audio/*"));

        edtTitleSong = findViewById(R.id.title_song_upload);
        edtArtistSong = findViewById(R.id.artist_song_upload);

        tvMp3Name = findViewById(R.id.name_mp3_file);
        imageView = findViewById(R.id.image_view);
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReferenceFromUrl("gs://application-music-player.appspot.com/");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("song");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    maxId = snapshot.getChildrenCount();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadSong();
            }
        });
    }

    private void openFileChooser(String type) {
        Intent intent = new Intent();
        String title = "Select";
        intent.setType(type);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        if (type.equals("image/*")) {
            title += "Image File";
            startActivityForResult(Intent.createChooser(intent, title), IMAGE_PICK_REQUEST);
        } else if (type.equals("audio/*")) {
            title += "Audio File";
            startActivityForResult(Intent.createChooser(intent, title), AUDIO_PICK_REQUEST);
        }
    }

    @SuppressLint("Range")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AUDIO_PICK_REQUEST && resultCode == RESULT_OK) {
            mp3Uri = data.getData();
            if (mp3Uri != null) {
                Context context = UploadSongActivity.this;
                Cursor cursor = context.getContentResolver().query(mp3Uri, null, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    mp3FileName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    System.out.println(mp3FileName);
                    cursor.close();
                }
                InputStream inputStream = null;
                try {
                    inputStream = context.getContentResolver().openInputStream(mp3Uri);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                StorageReference storageReferenceMp3 = storageReference.child("audio/" + mp3FileName);
                uploadTaskMp3 = (UploadTask) storageReferenceMp3.putStream(inputStream)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                //song.setStorageUrl(mp3Uri.toString());
                                Toast.makeText(UploadSongActivity.this, "Upload Successfully!", Toast.LENGTH_SHORT).show();
                                if (mp3FileName.endsWith(".mp3")) {
                                    mp3FileName = mp3FileName.substring(0, mp3FileName.length() - 4);
                                }
                                tvMp3Name.setText(mp3FileName);
                                /*storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {

                                    }
                                });*/
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(UploadSongActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                                Toast.makeText(UploadSongActivity.this, "Uploading", Toast.LENGTH_SHORT).show();
                            }
                        });
            }

        } else if (requestCode == IMAGE_PICK_REQUEST && resultCode == RESULT_OK) {
            imageUri = data.getData();
            Context context = UploadSongActivity.this;
            Cursor cursor = context.getContentResolver().query(imageUri, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                imageName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                cursor.close();
            }
            imageName = edtTitleSong.getText().toString().trim() + ".jpg";
            try {
                streamImg = context.getContentResolver().openInputStream(imageUri);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

            StorageReference storageReferenceImage = storageReference.child("img/" + imageName);
            uploadTaskImage = (UploadTask) storageReferenceImage.putStream(streamImg)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            //song.setImage(imageUri.toString());
                            Glide.with(UploadSongActivity.this).load(imageUri).into(imageView);
                            Toast.makeText(UploadSongActivity.this, "Upload Successfully!", Toast.LENGTH_SHORT).show();
                            /*storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                }
                            });*/

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(UploadSongActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(UploadSongActivity.this, "Uploading", Toast.LENGTH_SHORT).show();
                        }
                    });
        }

    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void UploadSong() {
        String titleSong = edtTitleSong.getText().toString().trim();
        String artistSong = edtArtistSong.getText().toString().trim();
        if (imageUri != null || !TextUtils.isEmpty(titleSong) || !TextUtils.isEmpty(artistSong) || mp3Uri != null) {
            // storageReference.child(mp3FileName + getFileExtension(imageUri));
            song.setId(maxId + 1);
            song.setTitle(titleSong);
            song.setArtist(artistSong);
            song.setImage("https://firebasestorage.googleapis.com/v0/b/application-music-player.appspot.com/o/img%2F" + titleSong + ".jpg?alt=media&token=d1260574-beb6-4228-8f72-3f88246c27d8");
            song.setStorageUrl("https://firebasestorage.googleapis.com/v0/b/application-music-player.appspot.com/o/audio%2F" + mp3FileName.replace(" ", "%20") + ".mp3?alt=media&token=56572bdd-5965-4c02-acfb-9ee030d123ce");
            databaseReference.push().setValue(song);
            Toast.makeText(UploadSongActivity.this, "Upload successful", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(UploadSongActivity.this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(UploadSongActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
        }
    }
}