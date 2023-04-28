package com.sharma.ankitapp.utils

import APP_PREFERENCE
import android.Manifest
import android.app.Activity
import android.app.ProgressDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.text.SpannableString
import android.text.Spanned
import android.text.method.HideReturnsTransformationMethod
import android.text.method.LinkMovementMethod
import android.text.method.PasswordTransformationMethod
import android.text.style.*
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.CornerFamily
import com.sharma.ankitapp.R
import com.sharma.ankitapp.core.BaseApp
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.squareup.picasso.RequestCreator
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.*
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


fun CharSequence.xtnValidEmail(): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(this).matches()
}


/**
 * @author Satnam Singh
 * Showing Toast
 */
fun Context.xtnToast(message: String = "") {
    Toast.makeText(this, "$message", Toast.LENGTH_SHORT).show()
}

/**
 * @author Satnam Singh
 * Showing Toast
 * @param id of string resource
 * */
fun Context.xtnToast(@StringRes id: Int) {
    Toast.makeText(this, resources.getString(id), Toast.LENGTH_SHORT).show()
}

/**
 * @author Satnam Singh
 * Showing Alert with OK without any click action
 * @param id of string resource
 * */
fun Context.xtnAlertOk(@StringRes id: Int, listener: DialogInterface.OnClickListener? = null) {
    AlertDialog.Builder(this)
        .setTitle(R.string.app_name)
        .setMessage(id)
        .setPositiveButton("Ok", listener)
        .create().show()
}


/**
 * @author Satnam Singh
 * Method for saving a value to SharedPreference.
 * Note All Shared preference are in String type
 * */
fun Context.xtnPutKey(key: String, value: String = "") {
    getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE)
        .apply {
            this.edit()
                .putString(key, value)
                .apply()
        }
}

/**
 * @author Satnam Singh
 * Method for getting value from SharedPrefrences
 * @return value or null
 * */
fun Context.xtnGetKey(key: String): String? {
    return getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE).getString(key, null)
}

/**
 * @author Satnam Singh
 * Extension method for removing a particular key
 * */
fun Context.xtnRemoveKey(key: String) {
    getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE)
        .apply {
            this.edit().remove(key).apply()
        }
}

/**
 * @author Satnam Singh
 * Extension method for removing all SharedPreferences
 */
fun Context.xtnRemoveAllKey() {
    getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE)
        .apply {
            this.edit().clear().apply()
        }
}

/**
 * @author Satnam Singh
 * Extension method to run block of code after specific Delay.
 */
fun xtnRunDelayed(action: () -> Unit, delay: Long, timeUnit: TimeUnit = TimeUnit.MILLISECONDS) {
    Handler().postDelayed(action, timeUnit.toMillis(delay))
}

/**
 * @author Satnam Singh
 * Extension method for simplifying hide/showing text of Password EditText
 */
fun EditText.xtnTogglePwd() {
    (if (transformationMethod is PasswordTransformationMethod)
        HideReturnsTransformationMethod()
    else
        PasswordTransformationMethod()).also { transformationMethod = it }
}


//fun xtnCompressImage(realPath: String?, block: (outFilePath: String?) -> Unit) {
//    realPath?.let {
//        val option = Tiny.FileCompressOptions()
//        option.quality = 50
//        Tiny.getInstance().source(realPath).asFile().withOptions(option)
//            .compress { isSuccess, outfile, t ->
//                if (isSuccess) {
//                    block(outfile)
//                    Timber.d("Compressed File Path: $outfile")
//                } else {
//                    block(null)
//                    Timber.e(t)
//                }
//            }
//    }
//}


/**
 * @author Satnam Singh
 * Extension method to browse for Context.
 */
fun Context.xtnIntentBrowse(url: String, newTask: Boolean = false): Boolean {
    try {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        if (newTask) intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        return true
    } catch (e: Exception) {
        return false
    }
}

/**
 * @author Satnam Singh
 * Extension method to share for Context.
 */
fun Context.xtnIntentShare(text: String, subject: String = ""): Boolean {
    val intent = Intent()
    intent.type = "text/plain"
    intent.putExtra(Intent.EXTRA_SUBJECT, subject)
    intent.putExtra(Intent.EXTRA_TEXT, text)
    try {
        startActivity(Intent.createChooser(intent, null))
        return true
    } catch (e: ActivityNotFoundException) {
        e.printStackTrace()
        return false
    }
}

/**
 * @author Satnam Singh
 * Extension method to send email for Context.
 */
fun Context.xtnIntentEmail(email: String, subject: String = "", text: String = ""): Boolean {

    val intent = Intent(Intent.ACTION_SENDTO)
    intent.data = Uri.parse("mailto:")
    intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
    if (subject.isNotBlank()) intent.putExtra(Intent.EXTRA_SUBJECT, subject)
    if (text.isNotBlank()) intent.putExtra(Intent.EXTRA_TEXT, text)
    if (intent.resolveActivity(packageManager) != null) {
        startActivity(intent)
        return true
    }
    return false
}

/**
 * @author Satnam Singh
 * Extension method to make call for Context.
 */
fun Context.xtnIntentMakeCall(number: String): Boolean {
    try {
        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$number"))
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.CALL_PHONE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return false
        }
        startActivity(intent)
        return true
    } catch (e: Exception) {
        return false
    }
}


/**
 * @author Satnam Singh
 * Extension method to rate app on PlayStore for Context.
 */
fun Context.xtnIntentRate(): Boolean =
    xtnIntentBrowse("market://details?id=$packageName") or xtnIntentBrowse("http://play.google.com/store/apps/details?id=$packageName")


/**
 * @author Satnam Singh
 * Extension method to send sms for Context.
 */
fun Context.xtnIntentSms(phone: String?, body: String = "") {
    val smsToUri = Uri.parse("smsto:" + phone)
    val intent = Intent(Intent.ACTION_SENDTO, smsToUri)
    intent.putExtra("sms_body", body)
    startActivity(intent)
}

fun Context.xtnIntentMapNavigate(lat: Double, lng: Double) {
    val intent = Intent(
        Intent.ACTION_VIEW,
        Uri.parse("http://maps.google.com/maps?saddr=30.724701,76.793892&daddr=$lat,$lng")
    )
    startActivity(intent)
}

/**
 * @author Satnam Singh
 * Extension method to dail telephone number for Context.
 */
fun Context.xtnIntentDial(tel: String?) =
    startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tel)))

/**
 * @author Satnam Singh
 * Make view GONE
 */
fun View.xtnGone() {
    if (visibility != View.GONE) {
        visibility = View.GONE
    }
}

/**
 * @author Satnam Singh
 * Extension method deleteLine for TextView.
 */
fun TextView.xtnStrikeThru() {
    paint.flags = paint.flags or Paint.STRIKE_THRU_TEXT_FLAG
    paint.isAntiAlias = true
}

/**
 * @author Satnam Singh
 * Toggle view's visibility to VISIBLE AND GONE on conditions
 **/
fun View.xtnToggleShowIf(predicate: () -> Boolean) {
    visibility = if (predicate()) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

/**
 * @author Satnam Singh
 * Extension method for navigating to other activity with/without Data
 * */
inline fun <reified T : Activity> Context.xtnNavigate(bundle: Bundle? = null) =
    startActivity(Intent(this, T::class.java).apply {
        bundle?.let { putExtras(bundle) }
    })


/**
 * @author Satnam Singh
 * Anything to json
 */
fun Any.xtnToJson() = Gson().toJson(this)


fun Context.xtnShowPD(msg: String = "Please wait"): ProgressDialog {
    return ProgressDialog.show(this, "", msg)
}

fun ProgressDialog.xtnDismiss() {
    if (isShowing) dismiss()
}

fun String.xtnPartFromString(): RequestBody {
    return RequestBody.create(MultipartBody.FORM, this)
}

fun String.xtnAsRequestBodyFromPath(
    mimeType: String = "image/*",
    paramName: String
): MultipartBody.Part {
    val file = File(this)
    return MultipartBody.Part.createFormData(
        paramName, file.name, file.asRequestBody(mimeType.toMediaTypeOrNull())
    )
}

fun SpannableString.xtnColor(
    color: String,
    @NonNull txt: String,
    @NonNull subString: String = txt
) = this.apply {
    setSpan(
        ForegroundColorSpan(Color.parseColor(color)),
        txt.indexOf(subString),
        (txt.indexOf(subString) + subString.length),
        0
    )
}

fun SpannableString.xtnBold(
    @NonNull txt: String,
    @NonNull subString: String = txt
) = this.apply {
    setSpan(
        StyleSpan(Typeface.BOLD),
        txt.indexOf(subString),
        (txt.indexOf(subString) + subString.length),
        0
    )
}


fun SpannableString.xtnUnderline(@NonNull txt: String, @NonNull subString: String = txt) =
    this.apply {
        setSpan(
            UnderlineSpan(),
            txt.indexOf(subString),
            (txt.indexOf(subString) + subString.length),
            0
        )
    }

fun SpannableString.xtnItalic(@NonNull txt: String, @NonNull subString: String) = this.apply {
    setSpan(
        StyleSpan(Typeface.ITALIC),
        txt.indexOf(subString),
        txt.indexOf(subString) + subString.length,
        0
    )
}


fun SpannableString.xtnStrikeThru(@NonNull txt: String, @NonNull subString: String) = this.apply {
    setSpan(
        StrikethroughSpan(),
        txt.indexOf(subString),
        (txt.indexOf(subString) + subString.length),
        0
    )
}


fun SpannableString.xtnClick(
    @NonNull txt: String,
    @NonNull subString: String,
    block: () -> Unit
) = this.apply {
    setSpan(
        object : ClickableSpan() {
            override fun onClick(widget: View) {
                block()
            }
        },
        txt.indexOf(subString),
        (txt.indexOf(subString) + subString.length),
        Spanned.SPAN_EXCLUSIVE_INCLUSIVE
    )
}


fun TextView.xtnSpannedTxt(spannableString: SpannableString) {
    text = spannableString
    movementMethod = LinkMovementMethod.getInstance()
}



fun ShapeableImageView.xtnRoundCorner(radius: Float = 40.0f) {
    shapeAppearanceModel = shapeAppearanceModel.toBuilder()
        .setTopLeftCorner(CornerFamily.ROUNDED, radius)
        .setTopRightCorner(CornerFamily.ROUNDED, radius)
        .setBottomLeftCorner(CornerFamily.ROUNDED, radius)
        .setBottomRightCorner(CornerFamily.ROUNDED, radius)
        .build()
}


/**
 * @author Satnam Singh
 * Extension method to provide simpler access to {@link ContextCompat#getColor(int)}.
 */
fun Context.xtnColor(color: Int) = ContextCompat.getColor(this, color)


/**
 * @author Satnam Singh
 * Single extension method for handling image loading accross the whole app
 * Method written to resolve loading of images from HTTP, Drawable,
 * Raw File, URI etc.
 **/
fun xtnLoadImage(
    any: Any?,
    placeHolder: Int = R.drawable.bg_placeholder,
    imageView: ImageView
) {
    any?.let {
        val picasso = BaseApp.INSTANCE.picasso
        var requestCreator: RequestCreator = when (any) {
            is Uri -> picasso.load(any)
            is Int -> picasso.load(any)
            is File -> picasso.load(any)
            else -> {
                val t = any as String
                if (t.xtnIsURL()) {
                    picasso.load(t)
                } else {
                    picasso.load(File(t))
                }
            }
        }

        requestCreator.placeholder(R.drawable.bg_placeholder)
            .error(R.drawable.bg_placeholder)
            .into(imageView)
    }
}

/**
 * @author Satnam Singh
 * Extension function to detect if a given string is
 * REMOTE URL
 */
fun String.xtnIsURL(): Boolean {
    return (this.isNullOrEmpty().not()) && (
            this.startsWith("HTTP", true) ||
                    this.startsWith("HTTPS", true) ||
                    this.startsWith("FTP", true))
}

/**
 * @author Satnam Singh
 *
 * Extension method for resolving backend's
 * Data Mapping mistakes
 */
inline fun <reified T> JsonElement.xtn2Concrete(clazz: Class<T>): T? {
    return Gson().fromJson(this.toString(), clazz)
}


/**
 * @author Satnam Singh
 * Extension method for returning 2 or > 2 digit string value
 */
fun Int.xtn2Digit(): String {
    return let {
        if (this < 10) "0$this" else "$this"
    }
}

/**
 * @author Satnam Singh
 * Extension method for returning 2 or > 2 digit string value
 */
fun Long.xtn2Digit(): String {
    return let {
        if (this < 10) "0$this" else "$this"
    }
}


/**
 * @author Satnam Singh
 * Extension method for returning decimal with upto 2 decimal places
 */
fun Double.xtn2Places(): Double {
    var bd = BigDecimal(this.toString())
    bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP)
    return bd.toDouble()
}

/**
 * @author Satnam Singh
 * Extension method for calculating BMI for given values
 * Note: This method is designed to calculate BMI based on
 * Metric Value system.
 */
inline fun xtnBMI(weightInKg: Double, heightInMeter: Double) =
    (weightInKg / (heightInMeter * heightInMeter)).xtn2Places()


fun Date.xtnFormat(format: String = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"): String {
    var temp = SimpleDateFormat(format, Locale.getDefault())
    return temp.format(this)
}

/**
 * @author Ravindra Singh
 * Extension method for hide key board
 */
fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}


fun Date.xtnStripTime(): Date {
    val cal = Calendar.getInstance()
    cal.time = this
    cal.set(Calendar.HOUR_OF_DAY, 0)
    cal.set(Calendar.MINUTE, 0)
    cal.set(Calendar.SECOND, 0)
    cal.set(Calendar.MILLISECOND, 0)
    this.time = cal.time.time
    return this
}


fun Bitmap.xtnToFile(): File {

    val f = File.createTempFile("videoThumbnail", ".png", BaseApp.INSTANCE.cacheDir)


//Convert bitmap to byte array
    val bos = ByteArrayOutputStream()
    compress(Bitmap.CompressFormat.JPEG, 0 /*ignored for PNG*/, bos)
    val bitmapdata = bos.toByteArray()

//write the bytes in file
    var fos: FileOutputStream? = null
    try {
        fos = FileOutputStream(f)
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
    }
    try {
        fos?.write(bitmapdata)
        fos?.flush()
        fos?.close()
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return f
}

fun Long.xtnMilli2Format(): String {
    return getDurationBreakdown(this)
}


fun getDurationBreakdown(millis: Long): String {
    var millis = millis
    if (millis <= 0) return ""

    val hours = TimeUnit.MILLISECONDS.toHours(millis)
    millis -= TimeUnit.HOURS.toMillis(hours)
    val minutes = TimeUnit.MILLISECONDS.toMinutes(millis)
    millis -= TimeUnit.MINUTES.toMillis(minutes)
    val seconds = TimeUnit.MILLISECONDS.toSeconds(millis)
    val sb = StringBuilder()
    if (hours > 0) {
        sb.append(hours.xtn2Digit())
        sb.append(" Hr")
            .append(" ")
    }
    sb.append(minutes.xtn2Digit())
    sb.append(" Min").append(" ")
    sb.append(seconds.xtn2Digit())
    sb.append(" Sec")
    return sb.toString()
}


fun getMillis(hr: Long, min: Long, sec: Long): Long {
    val hrMillis = TimeUnit.HOURS.toMillis(hr)
    val minMillis = TimeUnit.MINUTES.toMillis(min)
    val secMillis = TimeUnit.SECONDS.toMillis(sec)
    val totalMillis = hrMillis + minMillis + secMillis
    return totalMillis
}
