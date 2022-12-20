package com.melihsurkmez.memorygame

import android.content.Intent
import android.graphics.Typeface
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.util.TypedValue
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.melihsurkmez.memorygame.databinding.ActivityGameBinding
import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.android.synthetic.main.activity_game.imageButton
import kotlinx.android.synthetic.main.activity_game.imageButton10
import kotlinx.android.synthetic.main.activity_game.imageButton11
import kotlinx.android.synthetic.main.activity_game.imageButton12
import kotlinx.android.synthetic.main.activity_game.imageButton13
import kotlinx.android.synthetic.main.activity_game.imageButton14
import kotlinx.android.synthetic.main.activity_game.imageButton15
import kotlinx.android.synthetic.main.activity_game.imageButton16
import kotlinx.android.synthetic.main.activity_game.imageButton2
import kotlinx.android.synthetic.main.activity_game.imageButton3
import kotlinx.android.synthetic.main.activity_game.imageButton4
import kotlinx.android.synthetic.main.activity_game.imageButton5
import kotlinx.android.synthetic.main.activity_game.imageButton6
import kotlinx.android.synthetic.main.activity_game.imageButton7
import kotlinx.android.synthetic.main.activity_game.imageButton8
import kotlinx.android.synthetic.main.activity_game.imageButton9
import kotlinx.android.synthetic.main.activity_game.puan
import kotlinx.android.synthetic.main.activity_game.sayac
import kotlinx.android.synthetic.main.activity_game_for_six.*

class Game : AppCompatActivity() {
    lateinit var binding: ActivityGameBinding
    //Butonlarimizin listesi burada olucak
    var buttons = ArrayList<ImageButton>()
    lateinit var timer : CountDownTimer
    var cards = ArrayList<Card>()
    var cards2 = ArrayList<Card>()
    var secilenkartindex : Int? =null
    var gamesound: MediaPlayer? = null
    var congratsound: MediaPlayer? = null
    var shockedsound: MediaPlayer? = null
    var victorysound: MediaPlayer? = null
    var nirvanasound: MediaPlayer? = null
    var playing: Int = 1



    var homeName1Counter =0
    var homeName2Counter =0
    var homeName3Counter =0
    var homeName4Counter=0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val arrayList = intent.getSerializableExtra("cards") as ArrayList<Card>
        cards2=arrayList
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


        binding.sesAc.setOnClickListener {
            playing=1
            playSound()
        }

        binding.sesKapa.setOnClickListener {
            playing=0
            pauseSound()
        }

        // biz eklenen cards lara sırasıyla image atıcaz

        // image isinlerini boyle sırasıyla atsak

        cards2.forEachIndexed{index, card2 ->
            // println("Eklenen index "+index)
            card2.image = images[index]
        }
        // artik cards icersinde tum cardlar var

        // simdi sira karistirmakta
        cards2.shuffle()



        var i = 0
        while(cards.size < buttons.size){

            var temp = Card(name = cards2[i].name, score=cards2[i].score, home = cards2[i].home, image = cards2[i].image, homeName = cards2[i].homeName)

            if(temp.homeName == "Gryffindor"){

                if(homeName1Counter < 2){
                    cards.add(temp)
                    var temp2 = Card(name = cards2[i].name, score=cards2[i].score, home = cards2[i].home, image = cards2[i].image, homeName = cards2[i].homeName)
                    cards.add(temp2)
                    homeName1Counter+=1

                }
            }else if(temp.homeName == "Slytherin"){

                if(homeName2Counter < 2){
                    cards.add(temp)
                    var temp2 = Card(name = cards2[i].name, score=cards2[i].score, home = cards2[i].home, image = cards2[i].image, homeName = cards2[i].homeName)
                    cards.add(temp2)
                    homeName2Counter+=1
                }
            }else if(temp.homeName == "Hufflepuff"){
                if(homeName3Counter <2){
                    cards.add(temp)
                    var temp2 = Card(name = cards2[i].name, score=cards2[i].score, home = cards2[i].home, image = cards2[i].image, homeName = cards2[i].homeName)
                    cards.add(temp2)
                    homeName3Counter+=1
                }
            }else if(temp.homeName == "Ravenclaw"){
                if(homeName4Counter <2){
                    cards.add(temp)
                    var temp2 = Card(name = cards2[i].name, score=cards2[i].score, home = cards2[i].home, image = cards2[i].image, homeName = cards2[i].homeName)
                    cards.add(temp2)
                    homeName4Counter+=1
                }
            }

            i+=1

        }

        // for(i in 0..(buttons.size/2)-1){
        // var temp = Card(name = cards2[i].name, score=cards2[i].score, home = cards2[i].home, image = cards2[i].image, homeName = cards2[i].homeName)
        // cards.add(temp)
        // }

        //for(i in 0..(buttons.size/2)-1){
        // var temp = Card(name = cards2[i].name, score=cards2[i].score, home = cards2[i].home, image = cards2[i].image, homeName = cards2[i].homeName)
        // cards.add(temp)
        // }


        cards.shuffle()

        cards.forEachIndexed { index, card ->

            println((index+1).toString()+". kart = "+card.name)
        }
        println("---------------------------------------------")


        // karistirdik da artik kartlar buttonlarla index ile iliskili davrnacak


        // sonra kartlari sufflela


        myTimer()
        playSound()




        // eksi kod
        //images.addAll(images)
        //images.addAll(images)
        //images.shuffle()


        //cards = images.indices.map { index ->
        //    Card(images[index])
        //}

        buttons.forEachIndexed { index, button ->
            button.setOnClickListener {

                modeliguncelle(index)

                gorunumuguncelle()


            }
        }


    }

    private fun oyunBittiBasarili(){
        var builder: AlertDialog.Builder

        builder = AlertDialog.Builder(this)


        builder.setTitle("Bravo! Kazandın!")
            .setMessage("Puanın:"+puan.text)
            .setCancelable(true) // dialog box in cancellable
            // set positive button
            //take two parameters dialogInterface and an int
            .setPositiveButton("Ana Menüye Dön") { dialogInterface, it ->
                intent = Intent(applicationContext,Preferences::class.java)
                shockedstopSound()
                victorystopSound()
                CongratstopSound()
                stopSound()
                startActivity(intent)
                finish() // close the app when yes clicked
            }
            .setNegativeButton("Tekrar Oyna") { dialogInterface, it ->
                intent = Intent(applicationContext,SplashScreen::class.java)
                intent.putExtra("zorlukderece", "Orta");
                intent.putExtra("kisisayisi", "Tek");
                shockedstopSound()
                victorystopSound()
                CongratstopSound()
                stopSound()
                startActivity(intent)
                dialogInterface.cancel()
            }
            // show the builder
            .show()

    }

    fun NirvanaplaySound() {
        if (nirvanasound == null) {
            nirvanasound = MediaPlayer.create(this, R.raw.nirvana)
            nirvanasound!!.isLooping = true
            nirvanasound!!.start()
        } else nirvanasound!!.start()
    }

    fun NirvanastopSound() {
        if (nirvanasound != null) {
            nirvanasound!!.stop()
            nirvanasound!!.release()
            nirvanasound = null
        }
    }


    fun playSound() {
        if (gamesound == null) {
            gamesound = MediaPlayer.create(this, R.raw.prologue)
            gamesound!!.isLooping = true
            gamesound!!.start()
        } else gamesound!!.start()
    }
    fun pauseSound() {
        if (gamesound?.isPlaying == true) gamesound?.pause()
    }
    fun stopSound() {
        if (gamesound != null) {
            gamesound!!.stop()
            gamesound!!.release()
            gamesound = null
        }
    }
    // 4. Destroys the MediaPlayer instance when the app is closed
    override fun onStop() {
        super.onStop()
        if (gamesound != null) {
            gamesound!!.release()
            gamesound = null
        }
    }
    fun CongratsplaySound() {
        if (congratsound == null) {
            congratsound = MediaPlayer.create(this, R.raw.congrats)
            congratsound!!.isLooping = true
            congratsound!!.start()
        } else congratsound!!.start()
    }
    fun CongratspauseSound() {
        if (congratsound?.isPlaying == true) congratsound?.pause()
    }
    fun CongratstopSound() {
        if (congratsound != null) {
            congratsound!!.stop()
            congratsound!!.release()
            congratsound = null
        }
    }
    // 4. Destroys the MediaPlayer instance when the app is closed

    fun shockedplaySound() {
        if (shockedsound == null) {
            shockedsound = MediaPlayer.create(this, R.raw.shocked)
            shockedsound!!.isLooping = true
            shockedsound!!.start()
        } else shockedsound!!.start()
    }
    fun shockedpauseSound() {
        if (shockedsound?.isPlaying == true) shockedsound?.pause()
    }
    fun shockedstopSound() {
        if (shockedsound != null) {
            shockedsound!!.stop()
            shockedsound!!.release()
            shockedsound = null
        }
    }
    // 4. Destroys the MediaPlayer instance when the app is closed

    fun victoryplaySound() {
        if (victorysound == null) {
            victorysound = MediaPlayer.create(this, R.raw.victory)
            victorysound!!.isLooping = true
            victorysound!!.start()
        } else victorysound!!.start()
    }
    fun victorypauseSound() {
        if (victorysound?.isPlaying == true) victorysound?.pause()
    }
    fun victorystopSound() {
        if (victorysound != null) {
            victorysound!!.stop()
            victorysound!!.release()
            victorysound = null
        }
    }
    // 4. Destroys the MediaPlayer instance when the app is closed

    private fun oyunBittiBasarisiz() {
        var builder: AlertDialog.Builder

        builder = AlertDialog.Builder(this)


        builder.setTitle("Yetişemedin! Kaybettin :(")
            .setMessage("Puanın:"+puan.text)
            .setCancelable(true) // dialog box in cancellable
            // set positive button
            //take two parameters dialogInterface and an int
            .setPositiveButton("Ana Menüye Dön") { dialogInterface, it ->
                intent = Intent(applicationContext,Preferences::class.java)
                shockedstopSound()
                victorystopSound()
                CongratstopSound()
                stopSound()
                startActivity(intent)
                finish() // close the app when yes clicked
            }
            .setNegativeButton("Tekrar Oyna") { dialogInterface, it ->
                intent = Intent(applicationContext,SplashScreen::class.java)
                intent.putExtra("zorlukderece", "Orta");
                intent.putExtra("kisisayisi", "Tek");
                shockedstopSound()
                victorystopSound()
                CongratstopSound()
                stopSound()
                startActivity(intent)
                dialogInterface.cancel()
            }
            // show the builder
            .show()
    }





    private fun calculateTrueResult(index :Int){

        var card = cards[index]
        var totalScore = puan.text.toString().toInt()
        var timeScore = sayac.text.toString().toInt()

        var calculate = (card.score*2*card.home)*(timeScore/10)
        calculate = calculate + totalScore


        puan.text = calculate.toString()

        // val text: String = binding.succesLogs.text.toString()


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

    private fun gorunumuguncelle() {

        cards.forEachIndexed{index, card->
            var button = buttons[index]

            if(card.isFaceUp){

                button.setImageResource(card.image)
            }else{
                button.setImageResource(R.drawable.arka)
            }
        }
    }


    private fun modeliguncelle(index: Int) {
        NirvanastopSound()
        CongratstopSound()
        if(playing!=0){
            playSound()
        }
        var card = cards[index]
        //println("Cards[index] lengt "+cards.size)
        val new_text:String = card.name+"(Puan:"+card.score+""+",Ev:"+card.home+")"
        val textView = TextView(this)
        textView.setTypeface(textView.typeface,Typeface.BOLD)
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f)
        textView.text = new_text
        binding.scroll.addView(textView)
        //println("Tiklanan Kart'in adi "+card.name+ "Tiklanan kartin indexi "+index)


        if(card.isFaceUp){
            Toast.makeText(this, "Bu kart zaten seçili!", Toast.LENGTH_SHORT).show()
            return
        }

        if(secilenkartindex == null){
            kartlarirestoret()
            secilenkartindex = index
            card.isFaceUp = !card.isFaceUp
            //println("FaceUpladim")
            // println(card)
            //cards.forEachIndexed{index, card ->
            // if(card.isFaceUp){
            //   println("FaceUp olan kartin indexi-> "+index+ " Adi ->"+card.name)
            //  println(card)
            // }
            // }

        }else{
            eslesmeyikontrolet(secilenkartindex!!, index)
            secilenkartindex = null
        }


    }

    private fun kartlarirestoret(){

        for (card in cards){
            if(!card.isMatched){
                card.isFaceUp = false

            }
        }
    }

    private fun eslesmeyikontrolet(secilenkartindex: Int, index: Int) {

        if(cards[secilenkartindex].image == cards[index].image){

            Toast.makeText(this, "Eslesme Basarili", Toast.LENGTH_SHORT).show()


            cards[secilenkartindex].isMatched = true
            cards[index].isMatched = true
            cards[index].isFaceUp = !cards[index].isFaceUp
            gorunumuguncelle()
            calculateTrueResult(index)

            if(cards[index].name == "Cedric Diggory"){
                pauseSound()
                NirvanaplaySound()
            }else{
                pauseSound()
                CongratsplaySound()
            }
            var bittimi = 1
            for(card in cards){

                if(!card.isMatched){

                    bittimi = 0
                    break

                }
            }

            if(bittimi==1){
                victoryplaySound()
                timer.cancel()
                CongratstopSound()
                stopSound()
                oyunBittiBasarili()


            }

        }else{

                object : CountDownTimer(1000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    for (button in buttons) {    // Başta Kartlar Ters Gözüksün Diye
                        button.isEnabled = false
                    }
                    calculateFalseResult(secilenkartindex, index)
                    buttons[index].setImageResource(cards[index].image)
                    buttons[secilenkartindex].setImageResource(cards[secilenkartindex].image)

                }

                override fun onFinish() {
                    for (button in buttons) {    // Başta Kartlar Ters Gözüksün Diye
                        button.isEnabled = true
                    }
                    kartlarirestoret()
                    gorunumuguncelle()

                }
            }.start()



        }
    }

    //Timer
    private fun myTimer(){
        timer = object : CountDownTimer(46000,1000){
            override fun onTick(p0: Long) {
                binding.sayac.text = "${p0/1000}"
            }

            override fun onFinish() {
                binding.sayac.text = "0"
                victorystopSound()
                CongratstopSound()
                stopSound()
                shockedplaySound()
                oyunBittiBasarisiz()
            }

        }

        timer.start()

    }


}