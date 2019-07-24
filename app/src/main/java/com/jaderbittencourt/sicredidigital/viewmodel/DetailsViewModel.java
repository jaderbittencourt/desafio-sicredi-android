package com.jaderbittencourt.sicredidigital.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jaderbittencourt.sicredidigital.R;
import com.jaderbittencourt.sicredidigital.common.interaction.LoaderInteraction;
import com.jaderbittencourt.sicredidigital.model.Event;
import com.jaderbittencourt.sicredidigital.model.People;
import com.jaderbittencourt.sicredidigital.ui.interaction.DetailsInteraction;
import com.jaderbittencourt.sicredidigital.util.AddressUtil;
import com.jaderbittencourt.sicredidigital.util.DateUtil;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DetailsViewModel {

    private Context context;
    private LoaderInteraction loaderInteraction;
    private DetailsInteraction interaction;
    private Double latitude, longitude;
    private Event event;
    private ImageView imageView;

    public ObservableField<String> title = new ObservableField<>();
    public ObservableField<String> price = new ObservableField<>();
    public ObservableField<String> description = new ObservableField<>();
    public ObservableField<String> date = new ObservableField<>();
    public ObservableField<String> address = new ObservableField<>();

    @Inject
    public DetailsViewModel(Context context) {
        this.context = context;
    }

    public void setInteraction(DetailsInteraction interaction) {
        this.interaction = interaction;
    }

    public void setLoaderInteraction(LoaderInteraction loaderInteraction) {
        this.loaderInteraction = loaderInteraction;
    }

    public void bind(Event event, ImageView imageView, LinearLayout peopleContainer) {
        this.event = event;
        this.imageView = imageView;
        loaderInteraction.showLoader();

        title.set(event.getTitle());
        price.set(Double.toString(event.getPrice()));

        latitude = Double.parseDouble(event.getLatitude());
        longitude = Double.parseDouble(event.getLongitude());
        address.set(new AddressUtil().getAddress(context, latitude, longitude));

        Picasso.with(context)
                .load(event.getImage())
                .placeholder(R.drawable.placeholder)
                .into(imageView, callback);

        description.set(event.getDescription());
        date.set(DateUtil.getFormatedDate(event.getDate()));
        loadPeople(peopleContainer, event.getPeopleList());
        loaderInteraction.hideLoader();

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
    private void loadPeople(LinearLayout peopleContainer, List<People> peopleList) {
        String name = "";
        for( People p: peopleList) {
            name += p.getName() + "\n";
            TextView textView = new TextView(context);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 50, 0);
            layoutParams.setMargins(12,12,12,12);
            textView.setLayoutParams(layoutParams);
            textView.setText(p.getName());
            textView.setOnClickListener(v -> interaction.openPeopleDialog(p.getName(), p.getPicture()));
            peopleContainer.addView(textView);
        }
        loaderInteraction.hideLoader();
    }

    public void openMaps() {
        interaction.openMaps(latitude, longitude);
    }

    public void openDialog() {
        interaction.openDialog(event.getId());
    }

    public void share() {
        interaction.share(getShareText());
    }

    private String getShareText() {
        return  "Venha! Participe você também!" +
                System.lineSeparator() +
                System.lineSeparator() +
                "Evento: " + title.get() +
                System.lineSeparator() +
                System.lineSeparator() +
                "Local: " + address.get() +
                System.lineSeparator() +
                System.lineSeparator() +
                "Quando: " + date.get();
    }

}
