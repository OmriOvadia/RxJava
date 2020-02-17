package com.example.rxjava


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.annotation.MainThread
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer

import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private lateinit var  list: MutableList<String> ;
    private lateinit var listObservable: Observable<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var btn1 = btn_1_mainActivity
        var btn2 = btn_2_mainActivity
        btn1.setOnClickListener {
            var intent: Intent = Intent(applicationContext,ButtonActivity::class.java)
           startActivity(intent)



        }

         list = listOf("a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z").toMutableList()

         listObservable = Observable.fromIterable(list)
        listObservable.subscribe( object: Observer<String>{
            override fun onComplete() {

            }

            override fun onSubscribe(d: Disposable) {

            }

            override fun onNext(t: String) {
               Log.d("MainActivity",t)
            }

            override fun onError(e: Throwable) {

            }
        })

        var btnObservable = creatObservableFromOnClick(btn2)



        var flatObserver = btnObservable.flatMap { t-> listObservable}
        flatObserver?.subscribe({e->Log.d("Omri",e)})

        RxView.clicks(btn2)
            .subscribe(object: Observer<Any>{
                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: Any) {
                    Log.d("Omri","clicked")
                }

                override fun onError(e: Throwable) {

                }
            })

    }




    private fun creatObservableFromOnClick(btn: Button): Observable<Unit> {
        return Observable.create(object : ObservableOnSubscribe<Unit> {
            override fun subscribe(emitter: ObservableEmitter<Unit>) {
                btn.setOnClickListener { emitter.onNext(Unit) }
            }


        })
    }


}
