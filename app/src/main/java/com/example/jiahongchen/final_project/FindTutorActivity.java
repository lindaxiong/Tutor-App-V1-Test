package com.example.jiahongchen.final_project;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FindTutorActivity extends AppCompatActivity {

    //    private TextView txtJson;
    ProgressDialog pd;
    private Map<String, String> tutors;
    ListView listView;
    List<String> keys;
//    Button getDirBtn;
    View row;
//    Bundle saveInfo;
//    int childID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);




        setContentView(R.layout.activity_findtutor);

//        txtJson = findViewById(R.id.textView);
        new JsonTask().execute("https://engineering.virginia.edu/current-students/current-undergraduate-students/current-undergrads-tutoring#accordion106464");
//        new JsonTask().execute("https://uvabookstores.com/uvatext/textbook_express.asp?mode=2&step=2");

        tutors = new HashMap<>();

        listView = findViewById(R.id.listView);


//        if(savedInstanceState != null){
//            saveInfo = savedInstanceState;
//            int id = savedInstanceState.getInt("viewId");
//            listView.getChildAt(id).setBackgroundResource(R.color.colorMid);
//        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Find Your Tutor");
    }

//    private void setListView(){
//        List<String> keys = new ArrayList<String>(tutors.values());
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(TextbookActivity.this, android.R.layout.activity_list_item, keys);
//        listView.setAdapter(arrayAdapter);
//    }

    class listAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return tutors.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.item_layout, null);
            TextView name = view.findViewById(R.id.name);
//            TextView email = view.findViewById(R.id.email);
            name.setText(keys.get(i));
//            email.setText(tutors.get(keys.get(i)));
            return view;
        }
    }



    private class JsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(FindTutorActivity.this);
            pd.setMessage("Please wait");
            pd.setCancelable(false);
            pd.show();
        }

        protected String doInBackground(String... params) {
            Document doc = null;
            StringBuilder builder = new StringBuilder();
            try {
                doc = Jsoup.connect("https://engineering.virginia.edu/current-students/current-undergraduate-students/current-undergrads-tutoring#accordion106464").get();

//                Elements links = doc.select("span[class=\"text\"]");
//                Elements links = doc.select("a[href]");
                Elements links = doc.select("a[href$=\".me\"]");
                for (Element link : links){
//                    Log.d("FFFF: ", String.valueOf(link));
//                    Log.d("TEXT: ", String.valueOf(link.text()));

                    builder.append("\n").append(link.attr("href")).append("\n").append(link.text());
                    tutors.put(link.text(), link.attr("href"));

                }
            } catch (IOException e) {
//                e.printStackTrace();
                builder.append("Error: ").append(e.getMessage()).append("\n");
            }

            keys = new ArrayList<String>(tutors.keySet());


            Log.d("keys: ", String.valueOf(keys));
            Log.d("dict: ", String.valueOf(tutors));
            return builder.toString();

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (pd != null){
                pd.dismiss();
            }
//            txtJson.setText(result);

            listAdapter lA = new listAdapter();

            listView.setAdapter(lA);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    if (row != null) {
                        row.setBackgroundResource(R.color.white);
                    }
                    row = view;
                    view.setBackgroundResource(R.color.colorMid);
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(tutors.get(keys.get(i))));
                    startActivity(browserIntent);
                    finish();
                }
            });

        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


//    @Override
//    public void onSaveInstanceState(Bundle savedInstanceState) {
//
//        savedInstanceState.putInt("viewId", childID);
//        super.onSaveInstanceState(savedInstanceState);
//    }

}
