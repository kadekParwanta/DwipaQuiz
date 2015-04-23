package tk.dwipayana.dwipaquiz;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
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
    TextView txtQuestion, txtTahun, txtMataPel;
    RadioButton rda, rdb, rdc, rdd, rde;
    RadioGroup radioGroup1;
    Button butNext;
    DbHelper db;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        db=new DbHelper(this);
        quesList=db.getAllQuestions();
        currentQ=quesList.get(qid);
        totalQuestions = db.rowCount();

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
        txtTahun = (TextView) findViewById(R.id.tv_tahun);
        txtMataPel = (TextView) findViewById(R.id.tv_mata_pel);
        progressBar = (ProgressBar) findViewById(R.id.progressBarQuest);
        progressBar.setMax(totalQuestions);

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
        txtTahun.setText("Tahun: " + currentQ.getYear());
        txtMataPel.setText("Mata Pelajaran " + currentQ.getTopic().toString().toLowerCase());
        txtQuestion.setText(qid + 1 + " . " + currentQ.getQuestion());
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
        progressBar.setProgress(qid);
    }
}
