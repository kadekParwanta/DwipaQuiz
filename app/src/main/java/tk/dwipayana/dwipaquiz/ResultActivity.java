package tk.dwipayana.dwipaquiz;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.RatingBar;
import android.widget.TextView;

import tk.dwipayana.dwipaquiz.helper.DbHelper;

/**
 * Created by mitrais on 4/22/15.
 */
public class ResultActivity extends Activity {

    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        dbHelper = new DbHelper(this);
        int totalSoal = dbHelper.rowCount();
        //get rating bar object
        RatingBar bar = (RatingBar) findViewById(R.id.ratingBar1);
        bar.setNumStars(totalSoal);
        bar.setStepSize(1f);
        //get text view
        TextView t = (TextView) findViewById(R.id.textResult);
        //get score
        Bundle b = getIntent().getExtras();
        int score = b.getInt("score");
        //display score
        bar.setRating(score);
        switch (score) {
            case 0:
            case 1:
                t.setText("Opps, try again bro, keep learning");
                break;
            case 2:
                t.setText("Hmmmm.. maybe you have been reading a lot of JasaProgrammer quiz");
                break;
            case 3:
                t.setText("Who are you? A student in JP???");
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_result, menu);
        return true;
    }
}
