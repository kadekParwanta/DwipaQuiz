package tk.dwipayana.dwipaquiz;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.util.Log;

import java.util.List;

import tk.dwipayana.dwipaquiz.helper.DbHelper;
import tk.dwipayana.dwipaquiz.model.Question;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    public void testRowCountOnYear() {
        DbHelper db = new DbHelper(getSystemContext());
        int count = db.getRowCountOnYear(2012);
        Log.d("testQuerryDB", "count = " + count);
        assertNotNull(count);

        int count2013 = db.getRowCountOnYear(2013);
        assertNotNull(count2013);
        Log.d("testQuerryDB", "count2013 = " + count2013);
    }

    public void testGetAllQuestionsInYear() {
        DbHelper db = new DbHelper(getSystemContext());
        List<Question> list  = db.getAllQuestionsInYear(2012);
        assertNotNull(list);

        List<Question> list2013  = db.getAllQuestionsInYear(9999);
        assertEquals(0, list2013.size());
    }

    public void testGetYearList() {
        DbHelper db = new DbHelper(getSystemContext());
        List<Integer> list  = db.getYearList();
        Log.d("testGetYearList", "list = " + list);
        assertNotNull(list);
    }

    public void testGetMajorListInYear() {
        DbHelper db = new DbHelper(getSystemContext());
        List<Question.MAJOR> list  = db.getMajorListInYear(2012);
        Log.d("testGetMajorListInYear", "list = " + list);
        assertNotNull(list);
    }

    public void testGetTopicListInYearAndMajor() {
        DbHelper db = new DbHelper(getSystemContext());
        List<Question.TOPICS> list  = db.getTopicListInYearAndMajor(2012, Question.MAJOR.IPA);
        Log.d("testGetTopicList", "list = " + list);
        assertNotNull(list);
    }
}