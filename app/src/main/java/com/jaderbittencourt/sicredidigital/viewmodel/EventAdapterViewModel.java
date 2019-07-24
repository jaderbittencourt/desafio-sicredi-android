package com.jaderbittencourt.sicredidigital.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;
import android.util.Log;
import android.widget.ImageView;

import com.jaderbittencourt.sicredidigital.R;
import com.jaderbittencourt.sicredidigital.model.Event;
import com.jaderbittencourt.sicredidigital.ui.interaction.MainInteraction;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class EventAdapterViewModel {

    private Context context;
    private MainInteraction interaction;
    private Event event;
    private ImageView imageView;

    public final ObservableField<String> title = new ObservableField<>();
    public final ObservableField<String> description = new ObservableField<>();

    public EventAdapterViewModel(Context context, MainInteraction interaction) {
        this.context = context;
        this.interaction = interaction;
    }

    public void bind(Event event, ImageView imageView) {
        this.imageView = imageView;
        this.event = event;
        title.set(event.getTitle());
        Picasso.with(context)
                .load(event.getImage())
                .placeholder(R.drawable.placeholder)
                .into(imageView, callback);

        description.set(event.getDescription());
    }

    public void open() {
        interaction.openDetails(event);
    }

    private Callback callback = new Callback() {
        @Override
        public void onSuccess() {
        }

        @Override
        public void onError() {
            Picasso.with(context)
                    .load(event.getImage())
                    .error(R.drawable.placeholder)
                    .into(imageView, new Callback() {
                        @Override
                        public void onSuccess() {
                        }

                        @Override
                        public void onError() {
                            Log.v("Picasso", "Could not fetch image");
                        }
                    });
        }
    };


}
