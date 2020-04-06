package com.mbwasi.a2docapstone;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.daprlabs.cardstack.SwipeDeck;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import pk.codebase.requests.HttpError;
import pk.codebase.requests.HttpHeaders;
import pk.codebase.requests.HttpRequest;
import pk.codebase.requests.HttpResponse;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";
    public static final String SELECTED_PLACE = "SELECTED_PLACE";
    private SwipeDeck cardStack;
    private Context context = this;

    private SwipeDeckAdapter adapter;
    List<Place> placesList=null;
    int selectedPlaceIndex=0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cardStack = (SwipeDeck) findViewById(R.id.swipe_deck);
        cardStack.setHardwareAccelerationEnabled(false);

        //HTTP POST request
        HttpRequest request = new HttpRequest();
        request.setOnResponseListener(new HttpRequest.OnResponseListener() {
            @Override
            public void onResponse(HttpResponse response) {
                if (response.code == HttpResponse.HTTP_OK) {

                    try {
                        Log.e(TAG, "JSONObject: "+ response.toJSONObject().toString());
                        JSONArray jsonArrayOfPlaces = response.toJSONObject().getJSONArray("success");


                        Log.e(TAG, "Places: "+jsonArrayOfPlaces.toString());
                        Type listType = new TypeToken<ArrayList<Place>>(){}.getType();
                        placesList = new Gson().fromJson(jsonArrayOfPlaces.toString(), listType);

                        adapter = new SwipeDeckAdapter(placesList, getApplicationContext());
                        cardStack.setAdapter(adapter);

                        //TODO: Take list categoriesList and use it in RecyclerAdapter to populate the RecyclerView

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e(TAG, e.toString());
                    }
                }

                else if (response.code == HttpResponse.HTTP_UNAUTHORIZED) {

                    Toasty.error(getApplicationContext(), "Invalid Token", Toast.LENGTH_LONG, true).show();
                }
                //Any other HTTP status
                else{
                    Log.e(TAG, "Response code:" + response.code);

                    Toasty.error(getApplicationContext(), "Network Error", Toast.LENGTH_LONG, true).show();
                }
            }
        });
        //IF request fails
        request.setOnErrorListener(new HttpRequest.OnErrorListener() {
            @Override
            public void onError(HttpError error) {

                Toasty.error(getApplicationContext(), "Login Failed", Toast.LENGTH_LONG, true).show();
                Log.e("LoginActivity", error.toString());
            }
        });


        JSONObject json;
        try {
            json = new JSONObject();
            json.put("lat", "53.540964");
            json.put("lng", "-113.502522");
            json.put("distance", "1");
        } catch (JSONException ignore) {
            return;
        }

        String token = TokenUtils.getLoginToken(getApplicationContext());

        if(token!=null){
            String bearerToken = "Bearer " + token;
            Log.d(TAG, bearerToken);
            HttpHeaders headers = new HttpHeaders("Authorization", bearerToken);
            request.post("http://2doo.ca/api/place/list", json,headers);
        }else{
            Toasty.error(getApplicationContext(), "Invalid Token!", Toast.LENGTH_LONG, true).show();
            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);
        }

        cardStack.setEventCallback(new SwipeDeck.SwipeEventCallback() {
            @Override
            public void cardSwipedLeft(int position) {
                Log.i("MainActivity", "card was swiped left, position in adapter: " + position);
                selectedPlaceIndex =position;
            }

            @Override
            public void cardSwipedRight(int position) {
                Log.i("MainActivity", "card was swiped right, position in adapter: " + position);
                selectedPlaceIndex =position;
            }

            @Override
            public void cardsDepleted() {
                Log.i("MainActivity", "no more cards");
            }

            @Override
            public void cardActionDown() {
                Log.i(TAG, "cardActionDown");
            }

            @Override
            public void cardActionUp() {
                Log.i(TAG, "cardActionUp");
            }

        });
        //cardStack.setLeftImage(R.drawable.left_arrow);
       // cardStack.setRightImage(R.drawable.right_arrow);

//        Button btn = (Button) findViewById(R.id.thumbDown);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                cardStack.swipeTopCardLeft(180);
//
//            }
//        });
//        Button btn2 = (Button) findViewById(R.id.button2);
//        btn2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                cardStack.swipeTopCardRight(180);
//            }
//        });

//        Button btn3 = (Button) findViewById(R.id.button3);
//        btn3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                testData.add("a sample string.");
////                ArrayList<String> newData = new ArrayList<>();
////                newData.add("some new data");
////                newData.add("some new data");
////                newData.add("some new data");
////                newData.add("some new data");
////
////                SwipeDeckAdapter adapter = new SwipeDeckAdapter(newData, context);
////                cardStack.setAdapter(adapter);
//                adapter.notifyDataSetChanged();
//            }
//        });
    }

    public void infoPressed(View view) {
        Intent intent = new Intent(getApplicationContext(), RestaurantInfoActivity.class);

        intent.putExtra(SELECTED_PLACE,placesList.get(selectedPlaceIndex));
        startActivity(intent);
    }

    public void thumbUpPressed(View view) {
        cardStack.swipeTopCardRight(180);
    }

    public void thumbDownPressed(View view) {
        cardStack.swipeTopCardLeft(180);
    }


    public class SwipeDeckAdapter extends BaseAdapter {

        private  List<Place> data;
        private Context context;

        public SwipeDeckAdapter( List<Place> data, Context context) {
            this.data = data;
            this.context = context;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            View v = convertView;
            if (v == null) {
                LayoutInflater inflater = getLayoutInflater();
                // normally use a viewholder
                v = inflater.inflate(R.layout.restaurant_card, parent, false);
            }
            //((TextView) v.findViewById(R.id.textView2)).setText(data.get(position));
            ImageView imageView = (ImageView) v.findViewById(R.id.restaurantImage);
          Picasso.get().load(placesList.get(position).getImage()).fit().centerCrop().into(imageView);

            imageView.setImageResource(R.drawable.rest1);
            TextView placeDescription = (TextView) v.findViewById(R.id.restDescription);
            TextView placeName = (TextView) v.findViewById(R.id.restName);
            TextView placeCost = (TextView) v.findViewById(R.id.textRestaurantCost);

            RatingBar rb  = (RatingBar) v.findViewById(R.id.ratingBar);
          rb.setIsIndicator(true);

            Place place = (Place)getItem(position);

            rb.setNumStars(5);
            rb.setRating(place.getStars());

            placeName.setText(place.getName());
            placeDescription.setText(place.getAddress() + " " + place.getPostal_code()+ " " +place.getCity());

            String price="";

            for(int i=0;i<place.getPrice();i++){
                price += "$";
            }
            placeCost.setText(price);


            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("Layer type: ", Integer.toString(v.getLayerType()));
                    Log.i("Hwardware Accel type:", Integer.toString(View.LAYER_TYPE_HARDWARE));
                  //  Intent i = new Intent(v.getContext(), RestaurantInfoActivity.class);
                  //  v.getContext().startActivity(i);
                }
            });
            return v;
        }
    }
}