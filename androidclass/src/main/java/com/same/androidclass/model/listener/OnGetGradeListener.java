package com.same.androidclass.model.listener;

import com.same.androidclass.bean.Credit;
import com.same.androidclass.bean.Grade;

import java.util.List;

/**
 *
 * Created by alic on 16-5-13.
 */
public interface OnGetGradeListener {
    void success(List<Grade> grades,Credit credit);
    void failed();
}
