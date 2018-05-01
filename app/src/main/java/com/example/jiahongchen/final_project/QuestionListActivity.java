package com.example.jiahongchen.final_project;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class QuestionListActivity extends AppCompatActivity {

    SQLiteDatabase db;
    RecyclerView mRecyclerView;
    final DatabaseHelper mDbHelper = new DatabaseHelper(this);

    public DatabaseHelper getDbHelper() {
        return mDbHelper;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        db = mDbHelper.getReadableDatabase();
        DatabaseHelper helper = new DatabaseHelper(this);
        helper.onCreate(db);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("My Questions");
        }
        mRecyclerView = (RecyclerView) findViewById(R.id.myQuestions);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Add code here to load from the database

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                "question",
        };

        // How you want the results sorted in the resulting Cursor

        Cursor cursor = db.query(
                "questions",  // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );

        List<String> questions = new ArrayList<>();
        //cursor.moveToFirst();
        while(cursor.moveToNext()) {
            String ques = cursor.getString(
                    cursor.getColumnIndexOrThrow("question"));
            questions.add(ques);
        }

        RecyclerView.Adapter adapter = mRecyclerView.getAdapter();
        if (adapter instanceof QuestionListActivity.Adapter) {
            ((Adapter) adapter).setQuestions(questions);
        } else {
            adapter = new QuestionListActivity.Adapter(questions);
            mRecyclerView.setAdapter(adapter);
        }
    }

    public static class QuestionViewHolder extends RecyclerView.ViewHolder {

        View root;

        public QuestionViewHolder(View itemView) {
            super(itemView);
            root = itemView;
        }

        public void bind(String question) {
            TextView textView = (TextView) root.findViewById(R.id.theQuestion);
            textView.setText(question);
        }

    }

    public static class ClearQuestionsViewHolder extends RecyclerView.ViewHolder {

        View root;

        public ClearQuestionsViewHolder(View itemView) {
            super(itemView);
            root = itemView;
        }

        public void bind(final QuestionListActivity.Adapter adapter) {
            Button clearQuestions = itemView.findViewById(R.id.clearQuestions);
            clearQuestions.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatabaseHelper databaseHelper = new DatabaseHelper(view.getContext());
                    SQLiteDatabase db = databaseHelper.getWritableDatabase();
                    databaseHelper.onDelete(db);
                    adapter.clearQuestions();
                }
            });
        }
    }

    public static class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        List<String> questions = new ArrayList<>();

        final int view_type_question = 0;
        final int view_type_clear = 1;

        public Adapter(List<String> questions) {
            setQuestions(questions);
        }

        public void clearQuestions() {
            questions.clear();
            notifyItemRangeRemoved(0, questions.size());
        }

        public void setQuestions(List<String> questions) {
            this.questions = questions;
            notifyDataSetChanged();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            switch (viewType) {
                case view_type_question:
                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_question, parent, false);
                    return new QuestionViewHolder(view);
                case view_type_clear:
                    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_clear_questions_button, parent, false);
                    return new ClearQuestionsViewHolder(v);
            }
            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            switch (holder.getItemViewType()) {
                case view_type_question:
                    String question = null;
                    if (questions.size() > 0) {
                        question = questions.get(position);
                    } else {
                        question = "No questions have been asked";
                    }
                    ((QuestionViewHolder) holder).bind(question);
                    break;
                case view_type_clear:
                    ((ClearQuestionsViewHolder) holder).bind(this);
                    break;
            }

        }

        @Override
        public int getItemViewType(int position) {
            if (questions.size() > 0 && position == questions.size()) {
                return view_type_clear;
            } else {
                return view_type_question;
            }
        }

        @Override
        public int getItemCount() {
            return questions.size() + 1;
        }
    }
}
