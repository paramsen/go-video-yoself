package com.amsen.par.govideoyoself.persistence;

import com.amsen.par.govideoyoself.model.VideoStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * @author Pär Amsen 2016
 */
public class MockStorage implements Storage<VideoStatus> {
    private HashMap<Integer, VideoStatus> storage;

    public MockStorage() {
        storage = new HashMap<>();
        buildSomeMockData();
    }

    private void buildSomeMockData() {
        put(new VideoStatus(1, "Record a video of \uD83D\uDC31", "\uD83D\uDC31", false)); //cat
        put(new VideoStatus(2, "Record a video of \uD83D\uDC36", "\uD83D\uDC36", false)); //dog
        put(new VideoStatus(3, "Record a video of \uD83C\uDFE0", "\uD83C\uDFE0", false)); //house
        put(new VideoStatus(4, "Record a video of you", "\uD83D\uDD96", false)); //you
    }

    @Override
    public void put(VideoStatus videoStatus) {
        storage.put(videoStatus.getId(), videoStatus);
    }

    @Override
    public VideoStatus get(int id) {
        return storage.get(id);
    }

    @Override
    public List<VideoStatus> getAll() {
        ArrayList<VideoStatus> list = new ArrayList<>(storage.values());
        Collections.sort(list, (a, b) -> a.getId() < b.getId() ? -1 : 1);

        return list;
    }

    @Override
    public void invalidate() {
        storage.clear();
    }
}
