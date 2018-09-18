package order.android.com.Bop.mvp.presenter;

import java.util.List;

import order.android.com.Bop.mvp.contract.ArtistSongContract;
import order.android.com.Bop.mvp.model.Song;
import order.android.com.Bop.mvp.usecase.GetArtistSongs;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


public class ArtistSongPresenter implements ArtistSongContract.Presenter{

    private GetArtistSongs mUsecase;
    private ArtistSongContract.View mView;
    private CompositeSubscription mCompositeSubscription;

    public ArtistSongPresenter(GetArtistSongs getArtistSongs) {
        mUsecase = getArtistSongs;
    }

    @Override
    public void attachView(ArtistSongContract.View view) {
        mView = view;
        mCompositeSubscription = new CompositeSubscription();
    }

    @Override
    public void subscribe() {
        throw new RuntimeException("please to call subscribe(long artistID)");
    }

    @Override
    public void subscribe(long artistID) {
        loadSongs(artistID);
    }

    @Override
    public void unsubscribe() {
        mCompositeSubscription.clear();
    }

    @Override
    public void loadSongs(long artistID) {
        mCompositeSubscription.clear();
        Subscription subscription=mUsecase.execute(new GetArtistSongs.RequestValues(artistID))
                .getSongList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Song>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Song> songList) {
                        mView.showSongs(songList);
                    }
                });
        mCompositeSubscription.add(subscription);
    }
}
