package com.melihsurkmez.memorygame

import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import android.widget.ImageButton
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.melihsurkmez.memorygame.databinding.ActivityGameBinding
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_game.*
import java.io.IOException

class Game : AppCompatActivity() {
    lateinit var binding: ActivityGameBinding
    //Butonlarimizin listesi burada olucak
    var buttons = ArrayList<ImageButton>()
    var cards = ArrayList<Card>()
    var indexOfSingleSelectionCard : Int? =null
    var mediaPlayer : MediaPlayer?=null
    var mpForNope : MediaPlayer?=null
    var mpForEndFlag = true
    var mpForEnd : MediaPlayer?=null
    var runnable: Runnable = Runnable { }
    var handler = Handler()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityGameBinding.inflate(layoutInflater)

        setContentView(binding.root)
        println(cards.size)
        buttons.add(imageButton)
        buttons.add(imageButton2)
        buttons.add(imageButton3)
        buttons.add(imageButton4)
        buttons.add(imageButton5)
        buttons.add(imageButton6)
        buttons.add(imageButton7)
        buttons.add(imageButton8)
        buttons.add(imageButton9)
        buttons.add(imageButton10)
        buttons.add(imageButton11)
        buttons.add(imageButton12)
        buttons.add(imageButton13)
        buttons.add(imageButton14)
        buttons.add(imageButton15)
        buttons.add(imageButton16)


        var images = mutableListOf(R.drawable.bir,
            R.drawable.iki, R.drawable.uc,
            R.drawable.dort,R.drawable.bes,R.drawable.alti,R.drawable.yedi,R.drawable.sekiz
            ,R.drawable.dokuz,R.drawable.on,R.drawable.onbir,R.drawable.oniki,R.drawable.onuc
            ,R.drawable.ondort,R.drawable.onbes,R.drawable.onalti,R.drawable.onyedi,R.drawable.onsekiz
            ,R.drawable.ondokuz,R.drawable.yirmi,R.drawable.yirmibir,R.drawable.yirmiiki,R.drawable.yirmiuc
            ,R.drawable.yirmidort,R.drawable.yirmibes,R.drawable.yirmi6,R.drawable.yirmiyedi,R.drawable.yirmisekiz
            ,R.drawable.yirmidokuz,R.drawable.otuz,R.drawable.otuzbir,R.drawable.otuziki,R.drawable.otuzuc
            ,R.drawable.otuzdort,R.drawable.otuzbes,R.drawable.otuzalti,R.drawable.otuzyedi,R.drawable.otuzsekiz
            ,R.drawable.otuzdokuz,R.drawable.kirk,R.drawable.kirkbir,R.drawable.kirktiki,R.drawable.kirkuc
            ,R.drawable.kirkdort)



        // veriyi aldık


        // bu veri indexli sırali bir sekilde cardss a eklendi

        // biz eklenen cards lara sırasıyla image atıcaz

        // image isinlerini boyle sırasıyla atsak

        cards.forEachIndexed{index, card ->
            card.image = images[index]
        }
        // artik cards icersinde tum cardlar var

        // simdi sira karistirmakta
        cards.shuffle()

        // karistirdik da artik kartlar buttonlarla index ile iliskili davrnacak


        // sonra kartlari sufflela



        // eksi kod
        //images.addAll(images)
        //images.addAll(images)
        //images.shuffle()


        //cards = images.indices.map { index ->
        //    Card(images[index])
        //}


        buttons.forEachIndexed { index, button ->
            button.setOnClickListener {

               updateModel(index)

                updateViews()


            }
        }

        myTimer()
        playAudio()


    }

    private fun playAudioForNope(){

        mpForNope = MediaPlayer()
        mpForNope!!.setAudioStreamType(AudioManager.STREAM_MUSIC)

        try {
            mpForNope!!.setDataSource(this, Uri.parse("android.resource://"+this.packageName+"/"+R.raw.nope))
            mpForNope!!.prepare()
            mpForNope!!.start()

        }catch (e: IOException){
            println("Hata")
        }

    }

    private fun stopAudioForNope(){
        if(mpForNope!!.isPlaying){
            mpForNope!!.stop()
            mpForNope!!.reset()
            mpForNope!!.release()
        }
    }

    private fun playAudioForEnd(){

        mpForEnd = MediaPlayer()
        mpForEnd!!.setAudioStreamType(AudioManager.STREAM_MUSIC)

        try {
            mpForEnd!!.setDataSource(this, Uri.parse("android.resource://"+this.packageName+"/"+R.raw.yeter))
            mpForEnd!!.prepare()
            mpForEnd!!.start()

        }catch (e: IOException){
            println("Hata")
        }
    }

    private fun stopAudioForEnd(){
        if(mpForEnd!!.isPlaying){
            mpForEnd!!.stop()
            mpForEnd!!.reset()
            mpForEnd!!.release()
        }
    }


    private fun playAudio(){

        mediaPlayer = MediaPlayer()
        mediaPlayer!!.setAudioStreamType(AudioManager.STREAM_MUSIC)

        try {
            mediaPlayer!!.setDataSource(this, Uri.parse("android.resource://"+this.packageName+"/"+R.raw.vodka))
            mediaPlayer!!.prepare()
            mediaPlayer!!.start()

        }catch (e: IOException){
            println("Hata")
        }
    }

    private fun stopAudio(){
        if(mediaPlayer!!.isPlaying){
            mediaPlayer!!.stop()
            mediaPlayer!!.reset()
            mediaPlayer!!.release()
        }
    }

    private fun calculateTrueResult(index :Int){

        var card = cards[index]
        var totalScore = puan.text.toString().toInt()
        var timeScore = sayac.text.toString().toInt()

        var calculate = (card.score*2*card.home)*(timeScore/10)
        calculate = calculate + totalScore


        puan.text = calculate.toString()

        val text: String = binding.succesLogs.text.toString()

        val new_text: String ="Karakter"+":"+card.name+" Kazandırdığı Puan:"+calculate+" Evi:"+card.home



    }

    private fun calculateFalseResult(index1 :Int, index2 :Int){
        var card1 = cards[index1]
        var card2 = cards[index2]
        var totalScore = puan.text.toString().toInt()
        var timeScore = sayac.text.toString().toInt()

        var calculate = 0

        if(card1.home == card2.home){

            calculate = ((card1.score+card2.score)/card1.home)*(timeScore/10)
            calculate = totalScore-calculate

        }else{
            calculate = ((((card1.score+card2.score)/2)*card1.home*card2.home))*(timeScore/10)
            calculate = totalScore-calculate

        }

        if(calculate<0){
            calculate = 0
        }

        puan.text = calculate.toString()

    }

    private fun updateViews() {
        println("----------------------------------")
        cards.forEachIndexed{index, card->
            var button = buttons[index]

            if(card.isFaceUp){

                println("Gozukmeli")
                button.setImageResource(card.image)
            }else{
                button.setImageResource(R.drawable.arka)
            }
        }
    }

    private fun updateModel(index: Int) {
        var card = cards[index]


        if(card.isFaceUp){
            Toast.makeText(this, "Bu kart zaten secili", Toast.LENGTH_SHORT).show()
            return
        }

        if(indexOfSingleSelectionCard == null){
            restoreCards()
            indexOfSingleSelectionCard = index
            card.isFaceUp = !card.isFaceUp
        }else{
            checkForMatch(indexOfSingleSelectionCard!!, index)
            indexOfSingleSelectionCard = null
        }


    }

    private fun restoreCards(){

        for (card in cards){
            if(!card.isMatched){
                card.isFaceUp = false

            }
        }
    }

    private fun checkForMatch(indexOfSingleSelectionCard: Int, index: Int) {

        if(cards[indexOfSingleSelectionCard].image == cards[index].image){

            Toast.makeText(this, "Eslesme Basarili", Toast.LENGTH_SHORT).show()


            cards[indexOfSingleSelectionCard].isMatched = true
            cards[index].isMatched = true
            cards[index].isFaceUp = !cards[index].isFaceUp

            calculateTrueResult(index)

        }else{

            object : CountDownTimer(1000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    for (button in buttons) {    // Başta Kartlar Ters Gözüksün Diye
                        button.isEnabled = false
                    }
                    calculateFalseResult(indexOfSingleSelectionCard, index)
                    buttons[index].setImageResource(cards[index].identifier)
                    buttons[indexOfSingleSelectionCard].setImageResource(cards[indexOfSingleSelectionCard].identifier)
                    playAudioForNope()
                }

                override fun onFinish() {
                    for (button in buttons) {    // Başta Kartlar Ters Gözüksün Diye
                        button.isEnabled = true
                    }
                    restoreCards()
                    updateViews()
                    stopAudioForNope()
                }
            }.start()



        }
    }


    //Timer
    private fun myTimer(){
        object : CountDownTimer(45000,1000){
            override fun onTick(p0: Long) {
                binding.sayac.text = "${p0/1000}"

                if((p0/1000)<10 && mpForEndFlag){
                    playAudioForEnd()
                    mpForEndFlag = false
                }

            }

            override fun onFinish() {
                binding.sayac.text = "0"
                stopAudioForEnd()
                stopAudio()
            }

        }.start()
    }

    async fun get_data(){

        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://192.168.1.101:5001/")
            .build()
            .create(ApiInterface::class.java)


        val retrofitData = retrofitBuilder.getData()

        retrofitData.enqueue(object : Callback<List<CardsItem>?> {
            override fun onResponse(
                call: Call<List<CardsItem>?>,
                response: retrofit2.Response<List<CardsItem>?>
            ) {
                val responseBody = response.body()!!
                val myStringBuilder = StringBuilder()
                for(Cards in responseBody){
                    val card_id: Int = Cards.cardId
                    val card_name: String = Cards.cardName
                    val card_home: String = Cards.homeName
                    val home_score: Int = Cards.homeScore
                    val card_score: Int = Cards.cardScore

                    var addedCard = Card(identifier=card_id, name=card_name, score=card_score, home = home_score, home_name = card_home)

                    println(addedCard.home)

                    println(card_id.toString()+" "+ card_name+" "+card_home+" "+card_score.toString()+" "+home_score.toString())
                    cards.add(addedCard)

                }

            }

            override fun onFailure(call: Call<List<CardsItem>?>, t: Throwable) {
                Log.e("Error",t.toString())
            }
        }



        )




    }
}