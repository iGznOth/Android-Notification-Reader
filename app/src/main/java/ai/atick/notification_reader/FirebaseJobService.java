package ai.atick.notification_reader;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;

import static ai.atick.notification_reader.Key.TAG;

public class FirebaseJobService extends JobService {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference();

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG, "Uploading to Firebase");
        doInBackground(params);
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG, "Upload cancelled");
        return true;
    }

    private void doInBackground(final JobParameters parameters) {
        Date date = Calendar.getInstance().getTime();
        reference.child("timestamp").setValue(date.getTime(), new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                Log.d(TAG, "Firebase upload finished");
                jobFinished(parameters, false);
            }
        });
    }
}