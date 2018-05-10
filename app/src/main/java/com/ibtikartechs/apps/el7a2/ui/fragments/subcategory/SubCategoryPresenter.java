package com.ibtikartechs.apps.el7a2.ui.fragments.subcategory;

import com.ibtikartechs.apps.el7a2.data.DataManager;
import com.ibtikartechs.apps.el7a2.data.models.FooterListItemModel;
import com.ibtikartechs.apps.el7a2.ui.activities.base.BasePresenter;

import java.util.ArrayList;

/**
 * Created by ahmedyehya on 5/9/18.
 */

public class SubCategoryPresenter <V extends SubCategoryMvpView> extends BasePresenter<V> implements SubCategoryMvpPresenter<V> {
    public SubCategoryPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void loadFirstPage(String subCategoryId) {
        getMvpView().showProgressBar();
        getMvpView().setLastPageTrue();

        if (subCategoryId.equals("0")) {
            ArrayList<FooterListItemModel> firstFooterList = new ArrayList<>();
            firstFooterList.add(new FooterListItemModel("0", "2200$", "https://i5.walmartimages.com/asr/f5608778-2a32-4b49-bbdc-10b7edb11e19_1.f583d9ee5b2fc78a0dd64a305adf3ef4.jpeg?odnHeight=450&odnWidth=450&odnBg=FFFFFF", "ايفون جميل جدا ايفون جميل جدا ايفون جميل جدا", "2018-06-01"));
            firstFooterList.add(new FooterListItemModel("1", "2200$", "https://i5.walmartimages.com/asr/f5608778-2a32-4b49-bbdc-10b7edb11e19_1.f583d9ee5b2fc78a0dd64a305adf3ef4.jpeg?odnHeight=450&odnWidth=450&odnBg=FFFFFF", "ايفون جميل جدا ايفون جميل جدا ايفون جميل جدا", "2018-05-18"));
            firstFooterList.add(new FooterListItemModel("2", "2200$", "https://i5.walmartimages.com/asr/f5608778-2a32-4b49-bbdc-10b7edb11e19_1.f583d9ee5b2fc78a0dd64a305adf3ef4.jpeg?odnHeight=450&odnWidth=450&odnBg=FFFFFF", "ايفون جميل جدا ايفون جميل جدا ايفون جميل جدا", "2018-07-03"));
            firstFooterList.add(new FooterListItemModel("3", "2200$", "https://i5.walmartimages.com/asr/f5608778-2a32-4b49-bbdc-10b7edb11e19_1.f583d9ee5b2fc78a0dd64a305adf3ef4.jpeg?odnHeight=450&odnWidth=450&odnBg=FFFFFF", "ايفون جميل جدا ايفون جميل جدا ايفون جميل جدا", "2018-10-01"));
            firstFooterList.add(new FooterListItemModel("4", "2200$", "https://i5.walmartimages.com/asr/f5608778-2a32-4b49-bbdc-10b7edb11e19_1.f583d9ee5b2fc78a0dd64a305adf3ef4.jpeg?odnHeight=450&odnWidth=450&odnBg=FFFFFF", "ايفون جميل جدا ايفون جميل جدا ايفون جميل جدا", "2018-5-10"));
            firstFooterList.add(new FooterListItemModel("5", "2200$", "https://i5.walmartimages.com/asr/f5608778-2a32-4b49-bbdc-10b7edb11e19_1.f583d9ee5b2fc78a0dd64a305adf3ef4.jpeg?odnHeight=450&odnWidth=450&odnBg=FFFFFF", "ايفون جميل جدا ايفون جميل جدا ايفون جميل جدا", "2018-07-01"));
            firstFooterList.add(new FooterListItemModel("6", "2200$", "https://i5.walmartimages.com/asr/f5608778-2a32-4b49-bbdc-10b7edb11e19_1.f583d9ee5b2fc78a0dd64a305adf3ef4.jpeg?odnHeight=450&odnWidth=450&odnBg=FFFFFF", "ايفون جميل جدا ايفون جميل جدا ايفون جميل جدا", "2018-07-01"));
            firstFooterList.add(new FooterListItemModel("7", "2200$", "https://i5.walmartimages.com/asr/f5608778-2a32-4b49-bbdc-10b7edb11e19_1.f583d9ee5b2fc78a0dd64a305adf3ef4.jpeg?odnHeight=450&odnWidth=450&odnBg=FFFFFF", "ايفون جميل جدا ايفون جميل جدا ايفون جميل جدا", "2018-06-01"));
            firstFooterList.add(new FooterListItemModel("8", "2200$", "https://i5.walmartimages.com/asr/f5608778-2a32-4b49-bbdc-10b7edb11e19_1.f583d9ee5b2fc78a0dd64a305adf3ef4.jpeg?odnHeight=450&odnWidth=450&odnBg=FFFFFF", "ايفون جميل جدا ايفون جميل جدا ايفون جميل جدا", "2018-05-18"));
            firstFooterList.add(new FooterListItemModel("9", "2200$", "https://i5.walmartimages.com/asr/f5608778-2a32-4b49-bbdc-10b7edb11e19_1.f583d9ee5b2fc78a0dd64a305adf3ef4.jpeg?odnHeight=450&odnWidth=450&odnBg=FFFFFF", "ايفون جميل جدا ايفون جميل جدا ايفون جميل جدا", "2018-07-03"));
            firstFooterList.add(new FooterListItemModel("10", "2200$", "https://i5.walmartimages.com/asr/f5608778-2a32-4b49-bbdc-10b7edb11e19_1.f583d9ee5b2fc78a0dd64a305adf3ef4.jpeg?odnHeight=450&odnWidth=450&odnBg=FFFFFF", "ايفون جميل جدا ايفون جميل جدا ايفون جميل جدا", "2018-10-01"));
            firstFooterList.add(new FooterListItemModel("11", "2200$", "https://i5.walmartimages.com/asr/f5608778-2a32-4b49-bbdc-10b7edb11e19_1.f583d9ee5b2fc78a0dd64a305adf3ef4.jpeg?odnHeight=450&odnWidth=450&odnBg=FFFFFF", "ايفون جميل جدا ايفون جميل جدا ايفون جميل جدا", "2018-5-10"));
            firstFooterList.add(new FooterListItemModel("12", "2200$", "https://i5.walmartimages.com/asr/f5608778-2a32-4b49-bbdc-10b7edb11e19_1.f583d9ee5b2fc78a0dd64a305adf3ef4.jpeg?odnHeight=450&odnWidth=450&odnBg=FFFFFF", "ايفون جميل جدا ايفون جميل جدا ايفون جميل جدا", "2018-07-01"));
            firstFooterList.add(new FooterListItemModel("13", "2200$", "https://i5.walmartimages.com/asr/f5608778-2a32-4b49-bbdc-10b7edb11e19_1.f583d9ee5b2fc78a0dd64a305adf3ef4.jpeg?odnHeight=450&odnWidth=450&odnBg=FFFFFF", "ايفون جميل جدا ايفون جميل جدا ايفون جميل جدا", "2018-07-01"));
            getMvpView().addMoreToAdapter(firstFooterList);
        }
        getMvpView().hideErrorView();
    }

    @Override
    public void loadNextPage(String id, Integer page) {

    }
}
