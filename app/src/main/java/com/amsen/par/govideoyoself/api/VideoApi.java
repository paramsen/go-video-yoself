package com.amsen.par.govideoyoself.api;

import android.net.Uri;

import com.amsen.par.govideoyoself.base.rx.event.EventStream;
import com.amsen.par.govideoyoself.source.event.VideoEvent;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * @author Pär Amsen 2016
 */
public class VideoApi {
    private FirebaseStorage storage;
    private StorageReference rootReference;
    private StorageReference videosReference;
    private EventStream eventStream;

    public VideoApi(EventStream eventStream) {
        this.eventStream = eventStream;
        this.storage = FirebaseStorage.getInstance();
        this.rootReference = storage.getReferenceFromUrl("gs://go-video-yoself.appspot.com");
        this.videosReference = rootReference.child("videos");
    }

    public Observable<Boolean> post(int id, Uri uri) {
        return Observable.<Boolean>create(subscriber -> {
            videosReference.child(String.valueOf(id) + ".mp4")
                    .putFile(uri)
                    .addOnProgressListener(progress -> {
                        eventStream.post(new VideoEvent<>(id, VideoEvent.Type.PROGRESS, (100.0d * progress.getBytesTransferred()) / progress.getTotalByteCount()));
                        System.out.println("id " + id + " progress: " + progress.getBytesTransferred() + " / " + progress.getTotalByteCount());
                    })
                    .addOnSuccessListener(success -> {
                        subscriber.onNext(true);
                        subscriber.onCompleted();
                        eventStream.post(new VideoEvent<>(id, VideoEvent.Type.COMPLETE, null));
                        System.out.println("success");
                    })
                    .addOnFailureListener(failure -> {
                        subscriber.onError(failure);
                        eventStream.post(new VideoEvent<>(id, VideoEvent.Type.INCOMPLETE, null));
                        failure.printStackTrace();
                    });
        });
    }
}
