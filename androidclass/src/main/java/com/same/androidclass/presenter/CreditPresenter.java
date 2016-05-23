package com.same.androidclass.presenter;

import android.content.Context;

import com.same.androidclass.bean.Credit;
import com.same.androidclass.model.CreditModel;
import com.same.androidclass.model.CreditModelImpl;
import com.same.androidclass.util.DataUtil;
import com.same.androidclass.view.view.CreditShowView;

/**
 *
 * Created by alic on 16-5-18.
 */
public class CreditPresenter {
    private Context context;
    private CreditShowView creditShowView;
    private CreditModel creditModel;

    public CreditPresenter(Context context, CreditShowView creditShowView) {
        this.context = context;
        this.creditShowView = creditShowView;
        creditModel = new CreditModelImpl();
    }
    public void doShowCreditMessage(){
        Credit credit = creditModel.getCourseMessage(context, DataUtil.readSharedPreference(context, "username", ""));
        creditShowView.showCredit(credit);
    }
}
