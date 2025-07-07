package com.example.myapplication1;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.UUID;

public class SellerActivity3 extends AppCompatActivity {

    private ImageView imageView;
    private Button btnChoose, btnUpload;
    private EditText editTextTitle, editTextDescription, editTextActualPrice, editTextSellingPrice;
    private Uri imageUri;

    private StorageReference storageReference;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller3);

        // Initialize UI Components
        imageView = findViewById(R.id.imageView);
        btnChoose = findViewById(R.id.btnChoose);
        btnUpload = findViewById(R.id.btnUpload);
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDescription = findViewById(R.id.editTextDescription);
        editTextActualPrice = findViewById(R.id.editTextActualPrice);
        editTextSellingPrice = findViewById(R.id.editTextSellingPrice);

        // Firebase References
        databaseReference = FirebaseDatabase.getInstance().getReference("Customer");
        storageReference = FirebaseStorage.getInstance().getReference("Customers");

        btnChoose.setOnClickListener(v -> openFileChooser());
        btnUpload.setOnClickListener(v -> {
            if (imageUri != null && validateFields()) {
                uploadBook();
            } else {
                Toast.makeText(SellerActivity3.this, "Please select an image and fill all details!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // New Activity Result Launcher
    private final ActivityResultLauncher<Intent> imagePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    imageUri = result.getData().getData();
                    Picasso.get().load(imageUri).into(imageView);
                }
            });

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        imagePickerLauncher.launch(Intent.createChooser(intent, "Select Book Image"));
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private boolean validateFields() {
        return !editTextTitle.getText().toString().trim().isEmpty() &&
                !editTextDescription.getText().toString().trim().isEmpty() &&
                !editTextActualPrice.getText().toString().trim().isEmpty() &&
                !editTextSellingPrice.getText().toString().trim().isEmpty();
    }

    private void uploadBook() {
        if (imageUri == null) {
            Toast.makeText(this, "No image selected!", Toast.LENGTH_SHORT).show();
            return;
        }

        String fileName = System.currentTimeMillis() + "." + getFileExtension(imageUri);
        StorageReference fileReference = storageReference.child(fileName);

        fileReference.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> {
                    fileReference.getDownloadUrl().addOnSuccessListener(uri -> {
                        String bookId = UUID.randomUUID().toString();

                        String imageUrl = uri.toString();
                        String title = editTextTitle.getText().toString().trim();
                        String description = editTextDescription.getText().toString().trim();
                        String actualPrice = editTextActualPrice.getText().toString().trim();
                        String sellingPrice = editTextSellingPrice.getText().toString().trim();

                        // Create book object
                        Book book = new Book(bookId, imageUrl, title, description, actualPrice, sellingPrice);
                        databaseReference.child(bookId).setValue(book)
                                .addOnSuccessListener(aVoid -> {
                                    Toast.makeText(SellerActivity3.this, "Book uploaded successfully!", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(SellerActivity3.this, CustomerActivity3.class));
                                    finish();
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(SellerActivity3.this, "Database upload failed!", Toast.LENGTH_SHORT).show();
                                });
                    }).addOnFailureListener(e -> {
                        Toast.makeText(SellerActivity3.this, "Failed to retrieve image URL!", Toast.LENGTH_SHORT).show();
                    });
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(SellerActivity3.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }
}
