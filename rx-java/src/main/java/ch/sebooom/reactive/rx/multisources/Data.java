package ch.sebooom.reactive.rx.multisources;

/**
 * Created by seb on 12.05.16.
 */
public class Data {

    private static final long STALE_MS = 5 * 1000; // Data is stale after 5 seconds

    final String value;

    final long timestamp;

    public Data(String value) {
        this.value = value;
        this.timestamp = System.currentTimeMillis();
    }

    public boolean isUpToDate() {
        return System.currentTimeMillis() - timestamp < STALE_MS;
    }
}
