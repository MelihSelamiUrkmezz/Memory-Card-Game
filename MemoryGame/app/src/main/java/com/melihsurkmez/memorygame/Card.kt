package com.melihsurkmez.memorygame

data class Card
    (var identifier: Int, var isFaceUp: Boolean=false, var isMatched: Boolean=false, var score:Int=10, var home:Int=1)