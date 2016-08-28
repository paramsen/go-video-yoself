package com.amsen.par.govideoyoself.view.fragment;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amsen.par.govideoyoself.R;
import com.amsen.par.govideoyoself.base.rx.event.EventStream;
import com.amsen.par.govideoyoself.base.rx.subscriber.OnNextSubscriber;
import com.amsen.par.govideoyoself.model.VideoStatus;
import com.amsen.par.govideoyoself.source.VideoSource;
import com.amsen.par.govideoyoself.view.activity.BaseActivity;
import com.amsen.par.govideoyoself.view.view.VideoStatusItemView;

import java.util.List;

import butterknife.BindView;

/**
 * @author Pär Amsen 2016
 */
public class ListFragment extends BaseFragment {
    @BindView(R.id.recycler)
    RecyclerView recycler;

    private VideoSource videoSource;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initDependencies();
        initViews();
        initialState();
    }

    private void initialState() {
        videoSource.get()
                .subscribe(new OnNextSubscriber<>(this::initialVideos));
    }

    private void initialVideos(List<VideoStatus> videos) {
        recycler.setAdapter(new Adapter(getBaseActivity(), getEventStream(), videos));
    }

    private void initViews() {
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void initDependencies() {
        videoSource = getGraph().source;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_list;
    }

    public static class Adapter extends RecyclerView.Adapter<ItemHolder> {
        private BaseActivity activity;
        private EventStream eventStream;
        private List<VideoStatus> videos;

        public Adapter(BaseActivity activity, EventStream eventStream, List<VideoStatus> videos) {
            this.activity = activity;
            this.eventStream = eventStream;
            this.videos = videos;
        }

        @Override
        public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ItemHolder(new VideoStatusItemView(activity, eventStream, activity.getViewGraph().videoBehavior));
        }

        @Override
        public void onBindViewHolder(ItemHolder holder, int position) {
            VideoStatus video = videos.get(position);
            holder.view.apply(video);
        }

        @Override
        public int getItemCount() {
            return videos.size();
        }
    }

    public static class ItemHolder extends RecyclerView.ViewHolder {
        public VideoStatusItemView view;

        public ItemHolder(VideoStatusItemView view) {
            super(view);

            this.view = view;
        }
    }
}
