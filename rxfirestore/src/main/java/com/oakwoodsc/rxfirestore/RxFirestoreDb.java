package com.oakwoodsc.rxfirestore;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Transaction;
import com.google.firebase.firestore.WriteBatch;

import java.util.concurrent.Executor;

import io.reactivex.Completable;
import io.reactivex.Observable;

/**
 * Created by Brandon Trautmann
 */

public final class RxFirestoreDb {

    @NonNull
    @CheckResult
    public static Observable<DocumentSnapshot> documentSnapshots(@NonNull DocumentReference documentReference) {
        return Observable.create(new DocumentSnapshotsOnSubscribe(documentReference));
    }

    @NonNull
    @CheckResult
    public static Observable<QuerySnapshot> querySnapshots(@NonNull Query query) {
        return Observable.create(new QuerySnapshotsOnSubscribe(query));
    }

    @NonNull
    @CheckResult
    public static Observable<DocumentChange> documentChanges(@NonNull Query query) {
        return Observable.create(new DocumentChangesOnSubscribe(query));
    }

    @NonNull
    @CheckResult
    public static <T> Completable set(@NonNull DocumentReference reference, @NonNull T value) {
        return Completable.create(new SetOnSubscribe<>(reference, value));
    }

    @NonNull
    @CheckResult
    public static Completable delete(@NonNull DocumentReference reference) {
        return Completable.create(new DeleteOnSubscribe(reference));
    }

    @NonNull
    @CheckResult
    public static <T> Completable add(@NonNull CollectionReference reference, T value) {
        return Completable.create(new AddOnSubscribe<>(reference, value));
    }

    @NonNull
    @CheckResult
    public static <T> Completable runTransaction(@NonNull FirebaseFirestore database,
                                                 @NonNull Transaction.Function<T> transaction) {
        return Completable.create(new RunTransactionOnSubscribe<T>(database, transaction));
    }

    @NonNull
    @CheckResult
    public static Completable commitBatch(@NonNull WriteBatch batch) {
        return Completable.create(new CommitBatchOnSubscribe(batch));
    }

    @NonNull
    @CheckResult
    public static Completable deleteCollection(@NonNull CollectionReference collectionReference,
                                               int batchSize, @NonNull Executor executor) {
        return Completable.create(new DeleteCollectionOnSubscribe(collectionReference,
                batchSize, executor));
    }


}
