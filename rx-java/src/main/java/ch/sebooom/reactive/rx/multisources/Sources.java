package ch.sebooom.reactive.rx.multisources;

import rx.Observable;

/**
 * Created by seb on 12.05.16.
 */
public class Sources {

    // Memory cache of data
    private Data memory = null;

    // What's currently "written" on disk
    private Data disk = null;

    // Each "network" response is different
    private int requestNumber = 0;

    // In order to simulate memory being cleared, but data still on disk
    public void clearMemory() {
        System.out.println("Wiping memory...");
        memory = null;
    }

    public Observable<Data> memory() {
        Observable<Data> observable = Observable.create(subscriber -> {
            subscriber.onNext(memory);
            subscriber.onCompleted();
        });

        return observable.compose(logSource("MEMORY"));
    }

    public Observable<Data> disk() {
        Observable<Data> observable = Observable.create(subscriber -> {
            subscriber.onNext(disk);
            subscriber.onCompleted();
        });

        // Cache disk responses in memory
        return observable.doOnNext(data -> memory = data)
                .compose(logSource("DISK"));
    }

    public Observable<Data> network() {
        Observable<Data> observable = Observable.create(subscriber -> {
            requestNumber++;
            subscriber.onNext(new Data("Server Response #" + requestNumber));
            subscriber.onCompleted();
        });

        // Save network responses to disk and cache in memory
        return observable.doOnNext(data -> {
            disk = data;
            memory = data;
        })
                .compose(logSource("NETWORK"));
    }

    // Simple logging to let us know what each source is returning
    Observable.Transformer<Data, Data> logSource(final String source) {
        return dataObservable -> dataObservable.doOnNext(data -> {
            if (data == null) {
                System.out.println(source + " does not have any data.");
            }
            else if (!data.isUpToDate()) {
                System.out.println(source + " has stale data.");
            }
            else {
                System.out.println(source + " has the data you are looking for!");
            }
        });
    }
}
