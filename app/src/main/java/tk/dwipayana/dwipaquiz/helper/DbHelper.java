package tk.dwipayana.dwipaquiz.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import tk.dwipayana.dwipaquiz.model.Question;

public class DbHelper extends SQLiteOpenHelper {
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "dwipaQuiz";
	private static final String TABLE_QUEST = "questionTable";
	private static final String KEY_ID = "id";
    private static final String KEY_QUES_YEAR = "year";
    private static final String KEY_QUES_TOPIC = "topic";
    private static final String KEY_QUES_MAJOR = "major";
	private static final String KEY_QUES = "question";
	private static final String KEY_SOLUTION_ID = "solutionid";
    private static final String KEY_ANSWER = "answer";
	private static final String KEY_OPTA = "opta";
	private static final String KEY_OPTB = "optb";
	private static final String KEY_OPTC = "optc";
    private static final String KEY_OPTD = "optd";

	public DbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
        db.beginTransaction();
        try {
            String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_QUEST + " ( "
                    + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_QUES_YEAR + " INTEGER, "
                    + KEY_QUES_TOPIC + " TEXT, " + KEY_QUES_MAJOR + " TEXT, "
                    + KEY_QUES + " TEXT, " + KEY_ANSWER+ " TEXT, "+ KEY_OPTA +" TEXT, "
                    + KEY_OPTB + " TEXT, " + KEY_OPTC + " TEXT, "
                    + KEY_OPTD + " TEXT, " + KEY_SOLUTION_ID + " TEXT "
                    + ")";
            db.execSQL(sql);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
		if (DATABASE_VERSION <= oldV) {
            return;
        }

        db.beginTransaction();
        try {
            switch (oldV) {
                case 0:
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

	public void addQuestion(Question quest) {
		final SQLiteDatabase db = getWritableDatabase();
		ContentValues values = new ContentValues();
        values.put(KEY_QUES_YEAR, quest.getYear());
        values.put(KEY_QUES_TOPIC, quest.getTopic().toString());
        values.put(KEY_QUES_MAJOR, quest.getMajor().toString());
		values.put(KEY_QUES, quest.getQuestion());
		values.put(KEY_ANSWER, quest.getAnswer());
		values.put(KEY_OPTA, quest.getOptions()[0]);
		values.put(KEY_OPTB, quest.getOptions()[1]);
		values.put(KEY_OPTC, quest.getOptions()[2]);
        values.put(KEY_OPTD, quest.getOptions()[3]);
        values.put(KEY_SOLUTION_ID, quest.getSolutionID());
        db.beginTransaction();
        try {
            db.insert(TABLE_QUEST, null, values);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

	public List<Question> getAllQuestions() {
		List<Question> quesList = new ArrayList<>();
        final SQLiteDatabase db = getReadableDatabase();
        db.beginTransaction();
        try {
            String selectQuery = "SELECT  * FROM " + TABLE_QUEST;
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    Question quest = new Question();
                    quest.setID(cursor.getInt(0));
                    quest.setQuestion(cursor.getString(cursor.getColumnIndex(KEY_QUES)));
                    quest.setAnswer(cursor.getString(cursor.getColumnIndex(KEY_ANSWER)));

                    String major = cursor.getString(cursor.getColumnIndex(KEY_QUES_MAJOR));
                    Question.MAJOR mMajor = Question.MAJOR.valueOf(major);
                    quest.setMajor(mMajor);

                    String topic = cursor.getString(cursor.getColumnIndex(KEY_QUES_TOPIC));
                    Question.TOPICS mTopic = Question.TOPICS.valueOf(topic);
                    quest.setTopic(mTopic);

                    quest.setYear(cursor.getInt(cursor.getColumnIndex(KEY_QUES_YEAR)));

                    String[] options = new String[4];
                    String optA = cursor.getString(cursor.getColumnIndex(KEY_OPTA));
                    String optB = cursor.getString(cursor.getColumnIndex(KEY_OPTB));
                    String optC = cursor.getString(cursor.getColumnIndex(KEY_OPTC));
                    String optD = cursor.getString(cursor.getColumnIndex(KEY_OPTD));
                    options[0] = optA;
                    options[1] = optB;
                    options[2] = optC;
                    options[3] = optD;
                    quest.setOptions(options);

                    quest.setSolutionID(cursor.getString(cursor.getColumnIndex(KEY_SOLUTION_ID)));

                    quesList.add(quest);
                } while (cursor.moveToNext());
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

		return quesList;
	}

	public int rowCount()
	{
		int row=0;
		String selectQuery = "SELECT  * FROM " + TABLE_QUEST;
		final SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            Cursor cursor = db.rawQuery(selectQuery, null);
            row=cursor.getCount();
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();

        }
		return row;
	}

    public List<Question> getAllQuestionsInYear(int year) {
        List<Question> quesList = new ArrayList<>();
        final SQLiteDatabase db = getReadableDatabase();
        db.beginTransaction();
        try {
            String selectQuery = "SELECT  * FROM " + TABLE_QUEST + " WHERE " + KEY_QUES_YEAR + " = " + year;
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    Question quest = new Question();
                    quest.setID(cursor.getInt(0));
                    quest.setQuestion(cursor.getString(cursor.getColumnIndex(KEY_QUES)));
                    quest.setAnswer(cursor.getString(cursor.getColumnIndex(KEY_ANSWER)));

                    String major = cursor.getString(cursor.getColumnIndex(KEY_QUES_MAJOR));
                    Question.MAJOR mMajor = Question.MAJOR.valueOf(major);
                    quest.setMajor(mMajor);

                    String topic = cursor.getString(cursor.getColumnIndex(KEY_QUES_TOPIC));
                    Question.TOPICS mTopic = Question.TOPICS.valueOf(topic);
                    quest.setTopic(mTopic);

                    quest.setYear(cursor.getInt(cursor.getColumnIndex(KEY_QUES_YEAR)));

                    String[] options = new String[4];
                    String optA = cursor.getString(cursor.getColumnIndex(KEY_OPTA));
                    String optB = cursor.getString(cursor.getColumnIndex(KEY_OPTB));
                    String optC = cursor.getString(cursor.getColumnIndex(KEY_OPTC));
                    String optD = cursor.getString(cursor.getColumnIndex(KEY_OPTD));
                    options[0] = optA;
                    options[1] = optB;
                    options[2] = optC;
                    options[3] = optD;
                    quest.setOptions(options);

                    quest.setSolutionID(cursor.getString(cursor.getColumnIndex(KEY_SOLUTION_ID)));

                    quesList.add(quest);
                } while (cursor.moveToNext());
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

        return quesList;
    }

    public int getRowCountOnYear(int year)
    {
        int row=0;
        String selectQuery = "SELECT  * FROM " + TABLE_QUEST + " WHERE " + KEY_QUES_YEAR + " = " + year;
        final SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            Cursor cursor = db.rawQuery(selectQuery, null);
            row=cursor.getCount();
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();

        }
        return row;
    }

    public List<Integer> getYearList () {
        HashSet<Integer> yearList = new HashSet<>();
        List<Integer> sortedList = new ArrayList<>();
        final SQLiteDatabase db = getReadableDatabase();
        db.beginTransaction();
        try {
            String querry = "SELECT " + KEY_QUES_YEAR + " FROM " + TABLE_QUEST;
            Cursor cursor = db.rawQuery(querry, null);
            if (cursor.moveToFirst()) {
                do {
                    Integer year = cursor.getInt(cursor.getColumnIndex(KEY_QUES_YEAR));
                    yearList.add(year);
                } while (cursor.moveToNext());
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        sortedList.addAll(yearList);
        return sortedList;
    }

    public List<Question.MAJOR> getMajorListInYear (int year) {
        HashSet<Question.MAJOR> majorList = new HashSet<>();
        List<Question.MAJOR> sortedList = new ArrayList<>();
        final SQLiteDatabase db = getReadableDatabase();
        db.beginTransaction();
        try {
            String querry = "SELECT " + KEY_QUES_MAJOR + " FROM " + TABLE_QUEST + " WHERE " + KEY_QUES_YEAR + " = " + year;
            Cursor cursor = db.rawQuery(querry, null);
            if (cursor.moveToFirst()) {
                do {
                    String major = cursor.getString(cursor.getColumnIndex(KEY_QUES_MAJOR));
                    majorList.add(Question.MAJOR.valueOf(major));
                } while (cursor.moveToNext());
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        sortedList.addAll(majorList);
        return sortedList;
    }

    public List<Question.TOPICS> getTopicListInYearAndMajor (int year, Question.MAJOR Major) {
        HashSet<Question.TOPICS> majorList = new HashSet<>();
        List<Question.TOPICS> sortedList = new ArrayList<>();
        final SQLiteDatabase db = getReadableDatabase();
        db.beginTransaction();
        try {
            String querry = "SELECT " + KEY_QUES_TOPIC + " FROM " + TABLE_QUEST + " WHERE "
                    + KEY_QUES_YEAR + " = " + year + " AND " + KEY_QUES_MAJOR + " = " + "'"+Major.toString()+"'";
            Cursor cursor = db.rawQuery(querry, null);
            if (cursor.moveToFirst()) {
                do {
                    String major = cursor.getString(cursor.getColumnIndex(KEY_QUES_TOPIC));
                    majorList.add(Question.TOPICS.valueOf(major));
                } while (cursor.moveToNext());
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        sortedList.addAll(majorList);
        return sortedList;
    }
}
