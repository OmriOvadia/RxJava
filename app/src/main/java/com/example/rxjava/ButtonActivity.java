package com.example.rxjava;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import kotlin.jvm.functions.Function1;

public class ButtonActivity extends AppCompatActivity {
    private Button btn1, btn2, btn3;
    ArrayList<String> list = new ArrayList<>();


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button);
        btn1 = findViewById(R.id.btn_1_buttonActivity);
        btn2 = findViewById(R.id.btn_2_buttonActivity);
        btn3 = findViewById(R.id.btn_3_buttonActivity);
        list.add("omri");
        list.add("yossi");
        list.add("offer");
        list.add("tamara");
        list.add("roei");
        Observer observer = new Observer() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Object o) {
                Observable<String> observable = Observable.fromIterable(list);
                observable.flatMap(new Function<String, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(String s) throws Exception {
                        return Observable.just(s);
                    }
                }).subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d("omri","Start");
                    }

                    @Override
                    public void onNext(String s) {
                        Log.d("omri",s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("omri", "onError: ");                    }

                    @Override
                    public void onComplete() {
                        Log.d("omri", "onComplete: ");
                    }
                });
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

              Observable.merge(RxView.clicks(btn1),RxView.clicks(btn2),RxView.clicks(btn3)).subscribe(new Consumer<Object>() {
                  @Override
                  public void accept(Object o) throws Exception {
                      Log.d("Omri","Clicked");
                  }
              });


        final Observable<String> observable3 = Observable.fromIterable(list);
        observable3.subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

//        Observable flatObservable = observable.flatMap(new Function1<String,Observable<String>>() {
//
//        };






    }

    public Observable creatObservableFromOnClick(final Button btn) {
        return Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(final ObservableEmitter emitter) throws Exception {
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        emitter.onNext("Hello");
                    }
                });
            }
        });
    }


}
