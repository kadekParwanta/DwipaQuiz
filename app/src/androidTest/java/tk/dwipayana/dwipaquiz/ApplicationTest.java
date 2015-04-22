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
        assertEquals(3, count);

        int count2013 = db.getRowCountOnYear(2013);
        assertEquals(0, count2013);
        Log.d("testQuerryDB", "count2013 = " + count2013);
    }

    public void testGetAllQuestionsInYear() {
        DbHelper db = new DbHelper(getSystemContext());
        List<Question> list  = db.getAllQuestionsInYear(2012);
        assertEquals(3, list.size());

        List<Question> list2013  = db.getAllQuestionsInYear(2013);
        assertEquals(0, list2013.size());
    }

    public void testGetYearList() {
        DbHelper db = new DbHelper(getSystemContext());
        List<Integer> list  = db.getYearList();
        Log.d("testGetYearList", "list = " + list);
        assertEquals(1, list.size());
        assertEquals(2012, (int)list.get(0));
    }

    public void testGetMajorListInYear() {
        DbHelper db = new DbHelper(getSystemContext());
        List<Question.MAJOR> list  = db.getMajorListInYear(2012);
        Log.d("testGetMajorListInYear", "list = " + list);
        assertEquals(1, list.size());
        assertEquals(Question.MAJOR.IPA, list.get(0));
    }

    public void testGetTopicListInYearAndMajor() {
        DbHelper db = new DbHelper(getSystemContext());
        List<Question.TOPICS> list  = db.getTopicListInYearAndMajor(2012, Question.MAJOR.IPA);
        Log.d("testGetTopicList", "list = " + list);
        assertEquals(1, list.size());
        assertEquals(Question.TOPICS.INDO, list.get(0));
    }
}