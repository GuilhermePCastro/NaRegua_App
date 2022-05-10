package br.senac.naregua.functions

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.View
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import com.google.android.material.snackbar.Snackbar
import java.text.Normalizer

fun alert (titulo: String, msg: String, ctx: Context){

    AlertDialog.Builder(ctx)
        .setTitle(titulo)
        .setMessage(msg)
        .setPositiveButton("OK",null)
        .create()
        .show()
}

fun montaShimmerPicaso(): Drawable {

    val s = Shimmer.ColorHighlightBuilder()
        .setAutoStart(true)
        .setDuration(1000)
        .setBaseColor(Color.GRAY)
        .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
        .setHighlightColor(Color.WHITE)
        .setBaseAlpha(0.8f)
        .build()

    val sDrawable = ShimmerDrawable()
    sDrawable.setShimmer(s)

    return sDrawable
}

fun formataNumero(n: Double, formato: String): String {

    var retorno: String
    retorno = n.toString()

    if (formato == "dinheiro"){
        var d = "%,3.2f".format(n)
        d = d.replace('.', 'p')
        d = d.replace(',', 'v')
        d = d.replace('p', ',')
        d = d.replace('v', '.')
        retorno = d
    }


    return retorno
}

fun formataData(d: String): String {

    //Simple dateformat
    var retorno: String
    var dia = d.substring(8,10)
    var mes = d.substring(5,7)
    var ano = d.substring(0,4)

    retorno = "${dia}/${mes}/${ano}"

    return retorno
}

fun retornaToken(ctx: Context): String? {

    val p = ctx.getSharedPreferences("auth", Context.MODE_PRIVATE)
    val token = p.getString("token", "")

    return token

}

fun msg(view: View, msg: String){

    Snackbar.make(view,msg, Snackbar.LENGTH_LONG).show()
}

fun alertFun (titulo: String, msg: String, ctx: Context): AlertDialog.Builder? {

    return AlertDialog.Builder(ctx)
        .setTitle(titulo)
        .setMessage(msg)
        .setPositiveButton("OK",null)

}

fun trataCel(numero: String): String {

    var string: String
    string = numero.replace(Regex("\\s+"), "")
    string = string.replace("-","")

    return string
}

fun trataEnd(end: String): String {

    var string: String
    string = end.replace(" ","+")

    return string
}

