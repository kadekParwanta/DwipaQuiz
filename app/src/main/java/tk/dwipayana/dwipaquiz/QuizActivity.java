package tk.dwipayana.dwipaquiz;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

import tk.dwipayana.dwipaquiz.helper.DbHelper;
import tk.dwipayana.dwipaquiz.model.Question;


public class QuizActivity extends ActionBarActivity {

    List<Question> quesList;
    int score=0;
    int qid=0;
    int totalQuestions;
    Question currentQ;
    TextView txtQuestion;
    RadioButton rda, rdb, rdc, rdd, rde;
    RadioGroup radioGroup1;
    Button butNext;
    DbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        db=new DbHelper(this);
        addSampleQuestions();
        quesList=db.getAllQuestions();
        currentQ=quesList.get(qid);

        txtQuestion=(TextView)findViewById(R.id.textView1);
        radioGroup1 = (RadioGroup)findViewById(R.id.radioGroup1);
        rda=(RadioButton)findViewById(R.id.radio0);
        rda.setTag(0);
        rdb=(RadioButton)findViewById(R.id.radio1);
        rdb.setTag(1);
        rdc=(RadioButton)findViewById(R.id.radio2);
        rdc.setTag(2);
        rdd = (RadioButton)findViewById(R.id.radio3);
        rdd.setTag(3);
        rde = (RadioButton)findViewById(R.id.radio4);
        rde.setTag(4);
        butNext=(Button)findViewById(R.id.button1);

        setQuestionView();

        butNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton answer=(RadioButton)findViewById(radioGroup1.getCheckedRadioButtonId());
                if(currentQ.getAnswer().equals(answer.getText())) score++;
                if(qid<totalQuestions){
                    currentQ=quesList.get(qid);
                    setQuestionView();
                }else{
                    Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
                    Bundle b = new Bundle();
                    b.putInt("score", score);
                    intent.putExtras(b);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quiz, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setQuestionView()
    {
        totalQuestions = db.rowCount();
        txtQuestion.setText(currentQ.getQuestion());
        String[] options = currentQ.getOptions();
        Random rand = new Random();
        int i = rand.nextInt(5);
        int k=0;
        Log.d("rand", "Random answer pos "+i);
        for (int j=0; j<radioGroup1.getChildCount(); j++) {
            RadioButton button = (RadioButton) radioGroup1.getChildAt(j);
            if (button.getTag() == i) {
                button.setText(currentQ.getAnswer());
                k--;
            } else {
                button.setText(options[k]);
            }
            k++;
        }
        qid++;
    }

    public void addSampleQuestions()
    {
        if (db.rowCount() > 0) return;

        String[] options1 = {"Abundance", "Anxiety", "Bruxism", "Discipline"};
        Question q1=new Question(2012, Question.TOPICS.INDO, Question.MAJOR.IPA, "What is JP?", options1, "Jasa Programmer1", "iauhsfkhal");
        db.addQuestion(q1);

        String[] options2 = {"Monas, Jakarta", "Gelondong, Bangun Tapan, bantul", "Gelondong, Bangun Tapan, bandul", "Gelondong, Bangun Tapan, bantul"};
        Question q2=new Question(2012, Question.TOPICS.INDO, Question.MAJOR.IPA, "where the JP place?", options2, "Gelondong, Bangun Tapan, bantul2", "iauhsfkhal");
        db.addQuestion(q2);


        String[] options3 = {"JP is programmer home", "JP also realigy home", "all answer is true","all answer is true"};
        Question q3=new Question(2012, Question.TOPICS.INDO, Question.MAJOR.IPA, "what do you know about JP?", options3, "all answer is true3", "iauhsfkhal");
        db.addQuestion(q3);
    }
}
