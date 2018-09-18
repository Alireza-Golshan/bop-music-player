package order.android.com.Bop.mvp.contract;

import android.content.Context;

import java.util.List;

import order.android.com.Bop.mvp.model.Song;
import order.android.com.Bop.mvp.presenter.BasePresenter;
import order.android.com.Bop.mvp.view.BaseView;



public interface PlayRankingContract {

    interface View extends BaseView {

        Context getContext();

        void showRanking(List<Song> songList);

        void showEmptyView();
    }

    interface Presenter extends BasePresenter<View> {

        void loadRanking();
    }

}
