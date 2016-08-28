package com.amsen.par.govideoyoself.persistence;

import android.content.Intent;

import com.amsen.par.govideoyoself.model.VideoStatus;

import java.util.ArrayList;
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
        put(new VideoStatus(1, "Cat videos", false));
        put(new VideoStatus(2, "Dog videos", false));
        put(new VideoStatus(3, "You videos", false));
        put(new VideoStatus(4, "Whatever videos", false));
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
        return new ArrayList<>(storage.values());
    }

    @Override
    public void invalidate() {
        storage.clear();
    }
}
