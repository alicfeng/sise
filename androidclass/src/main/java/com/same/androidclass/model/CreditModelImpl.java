package com.same.androidclass.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.same.androidclass.bean.Credit;
import com.same.androidclass.common.AppConfig;
import com.same.androidclass.util.AppUtil;

/**
 * Created by alic on 16-5-15.
 */
public class CreditModelImpl implements CreditModel {

    /**
     * 获得绩点等数据
     *
     * @param context   context
     * @param studentId 学生号
     * @return 绩点等数据
     */
    @Override
    public Credit getCourseMessage(Context context, String studentId) {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context, AppConfig.DATABASE_NAME, null, AppUtil.getVersionCode(context));
        SQLiteDatabase readableDatabase = dataBaseHelper.getReadableDatabase();

        Cursor cursor = readableDatabase.query(
                AppConfig.TABLE_COURGE_MESSAGE,
                null
                ,"studentId=?",new String[]{studentId},
                null,
                null,
                null
        );
        Credit credit = new Credit();
        while (cursor.moveToNext()) {
            credit.setStudentId(cursor.getString(cursor.getColumnIndex("studentId")));
            credit.setObligatoryGradeAll(cursor.getFloat(cursor.getColumnIndex("obligatoryGradeAll")));
            credit.setObligatoryGraded(cursor.getFloat(cursor.getColumnIndex("obligatoryGraded")));
            credit.setOptionGradeAll(cursor.getFloat(cursor.getColumnIndex("optionGradeAll")));
            credit.setOptionGraded(cursor.getFloat(cursor.getColumnIndex("optionGraded")));
            credit.setGraded(cursor.getFloat(cursor.getColumnIndex("graded")));
            credit.setGrading(cursor.getFloat(cursor.getColumnIndex("grading")));
            credit.setOptionCourseGrade(cursor.getFloat(cursor.getColumnIndex("optionCourseGrade")));
            credit.setExpectedGrade(cursor.getFloat(cursor.getColumnIndex("expectedGrade")));
            credit.setAveragePoint(cursor.getFloat(cursor.getColumnIndex("averagePoint")));
            credit.setGradeAll(cursor.getFloat(cursor.getColumnIndex("gradeAll")));
            //close
            DataBaseHelper.closeDatabase(readableDatabase, cursor);
            return credit;
        }
        //debug
        AppUtil.LOG(this.getClass().getName(), "从本地获取学分信息成功");
        //close
        DataBaseHelper.closeDatabase(readableDatabase, cursor);
        return null;
    }

    @Override
    public void saveCourseMessageToLocal(Context context, Credit credit) {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context, AppConfig.DATABASE_NAME, null, AppUtil.getVersionCode(context));
        SQLiteDatabase writableDatabase = dataBaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        if (credit != null) {
            values.put("studentId", credit.getStudentId());
            values.put("obligatoryGradeAll", credit.getObligatoryGradeAll());
            values.put("obligatoryGraded", credit.getObligatoryGraded());
            values.put("optionGradeAll", credit.getOptionGradeAll());
            values.put("optionGraded", credit.getOptionGraded());
            values.put("graded", credit.getGraded());
            values.put("grading", credit.getGrading());
            values.put("optionCourseGrade", credit.getOptionCourseGrade());
            values.put("expectedGrade", credit.getExpectedGrade());
            values.put("averagePoint", credit.getAveragePoint());
            values.put("gradeAll", credit.getGradeAll());
            writableDatabase.insert(AppConfig.TABLE_COURGE_MESSAGE, null, values);
        }
        //debug
        AppUtil.LOG(this.getClass().getName(), "我在保存学分信息呢");
    }

    @Override
    public void deleteCourseMessageFromLocal(Context context, String studentId) {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context, AppConfig.DATABASE_NAME, null, AppUtil.getVersionCode(context));
        SQLiteDatabase writableDatabase = dataBaseHelper.getWritableDatabase();
        writableDatabase.delete(AppConfig.TABLE_COURGE_MESSAGE, "studentId=?", new String[]{studentId});
    }
}
