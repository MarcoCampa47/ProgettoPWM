package com.example.railmanager.modules.dbModule

import android.app.AlertDialog
import android.content.Context
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class UsefulStaticMethods {
    companion object {
        fun showSimpleAlertDialog(context: Context, message: String) {
            AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton("OK") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }

        fun formattaData(dataString: String): String {
            // Rimuovi eventuali "GMT" alla fine della stringa
            var cleanedDataString = dataString.replace(" GMT", "")

            // Crea un formatter personalizzato per gestire il giorno della settimana abbreviato
            val formatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss", Locale.US)

            // Prova a parsare la data usando il formatter
            val datetime = try {
                LocalDateTime.parse(cleanedDataString, formatter)
            } catch (e: Exception) {
                // Gestione dell'errore di parsing
                e.printStackTrace()
                return "Formato data non valido"
            }

            // Ottieni il fuso orario del dispositivo
            val zonaFusoOrarioLocale = ZonedDateTime.now().zone

            // Formatta la data nel formato desiderato
            val formatterOutput = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
            val dataFormattata = ZonedDateTime.of(datetime, zonaFusoOrarioLocale).format(formatterOutput)

            return dataFormattata
        }

        fun formattaDataLikeDb(dataString: String): String {
            // Rimuovi eventuali "GMT" alla fine della stringa
            var cleanedDataString = dataString.replace(" GMT", "")

            // Crea un formatter personalizzato per gestire il giorno della settimana abbreviato
            val formatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss", Locale.US)

            // Prova a parsare la data usando il formatter
            val datetime = try {
                LocalDateTime.parse(cleanedDataString, formatter)
            } catch (e: Exception) {
                // Gestione dell'errore di parsing
                e.printStackTrace()
                return "Formato data non valido"
            }

            // Ottieni il fuso orario del dispositivo
            val zonaFusoOrarioLocale = ZonedDateTime.now().zone

            // Formatta la data nel formato desiderato
            val formatterOutput = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")
            val dataFormattata = ZonedDateTime.of(datetime, zonaFusoOrarioLocale).format(formatterOutput)

            return dataFormattata
        }


        fun <K, V> getKeysForValues(hashMap: HashMap<K, V>, values: List<V>): HashMap<K, V> {
            val resultMap = HashMap<K, V>()

            for (entry in hashMap.entries) {
                if (entry.value in values) {
                    resultMap[entry.key] = entry.value
                }
            }

            return resultMap
        }

    }

}