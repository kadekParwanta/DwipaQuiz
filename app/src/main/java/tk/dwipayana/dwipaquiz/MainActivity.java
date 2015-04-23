package tk.dwipayana.dwipaquiz;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import tk.dwipayana.dwipaquiz.helper.DbHelper;
import tk.dwipayana.dwipaquiz.model.Question;

/**
 * Created by Kadek Juliana Parwanta on 4/23/15.
 */
public class MainActivity extends Activity {
    private DbHelper dbHelper;
    private Context mContext;
    private TextView tvSoal;
    private TextView tvTahun;
    private TextView tvJurusan;
    private TextView tvMataPel;
    private Button btnStart;
    private Button btnVoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        dbHelper = new DbHelper(mContext);
        addSampleQuestions();
        setContentView(R.layout.activity_main);
        tvSoal = (TextView) findViewById(R.id.tvSoal);
        tvTahun = (TextView) findViewById(R.id.tvTahun);
        tvJurusan = (TextView) findViewById(R.id.tvJurusan);
        tvMataPel = (TextView) findViewById(R.id.tvMataPel);
        btnStart = (Button) findViewById(R.id.button_start);
        btnVoice = (Button) findViewById(R.id.btnVoiceRec);

        int soalCount = dbHelper.rowCount();
        tvSoal.append(" "+soalCount);

        List<Integer> yearList = dbHelper.getYearList();
        for (Integer i : yearList) {
            tvTahun.append(i + ",");
            List<Question.MAJOR> majorList = dbHelper.getMajorListInYear(i);
            for (Question.MAJOR major : majorList) {
                tvJurusan.append(major.name() + ", ");
                List<Question.TOPICS> topicsList = dbHelper.getTopicListInYearAndMajor(i,major);
                for (Question.TOPICS topic : topicsList) tvMataPel.append(topic.name() + ", ");
            }
        }

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setClass(mContext, QuizActivity.class);
                startActivity(i);
            }
        });

        btnVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setClass(mContext, VoiceRecognitionActivity.class);
                startActivity(i);
            }
        });
    }

    public void addSampleQuestions()
    {
        if (dbHelper.rowCount() > 0) return;
        String[] options1 = {"12", "179", "917", "791"};
        Question q1=new Question(2012, Question.TOPICS.MATH, Question.MAJOR.IPA, "Hitung 200 - 3 = ?", options1, "197", "iauhsfkhal");
        dbHelper.addQuestion(q1);

        String[] options2 = {"Monday", "Tuesday", "Wednesday", "Thursday"};
        Question q2=new Question(2013, Question.TOPICS.ENG, Question.MAJOR.IPS, "What day is 26 Juli 1987?", options2, "Sunday", "iauhsfkhal");
        dbHelper.addQuestion(q2);


        String[] options3 = {"Aku", "Kamu", "Mereka","Dia"};
        Question q3=new Question(2014, Question.TOPICS.INDO, Question.MAJOR.IPA, "Siapa presiden pertama Indonesia?", options3, "ir. Soekarno", "iauhsfkhal");
        dbHelper.addQuestion(q3);
    }
}
