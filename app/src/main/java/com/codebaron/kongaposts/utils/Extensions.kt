package com.codebaron.kongaposts.utils

import android.content.Context
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

/**
 * @author Anyanwu Nicholas(codeBaron)
 * @since 16-11-2023
 */


/**
 * Reads JSON data from an asset file in the given context.
 * @param context The context to access the application's assets.
 * @param fileName The name of the JSON file in the assets directory.
 * @return A String containing the JSON data, or null if an error occurs.
 */
fun getJsonDataFromAsset(context: Context, fileName: String): String? {
    // String to hold the JSON data.
    val jsonString: String
    try {
        // Open the specified asset file and read its content into the jsonString.
        jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
    } catch (ioException: IOException) {
        // Handle IO exceptions, print the stack trace, and return null.
        ioException.printStackTrace()
        return null
    }
    // Return the JSON data.
    return jsonString
}

/**
 * Checks the validity of an image URL by sending a HEAD request to the specified URL.
 * @param imageUrl The URL of the image to be validated.
 * @return true if the URL is valid and returns a successful HTTP response code, false otherwise.
 */
fun isImageUrlValid(imageUrl: String): Boolean {
    return try {
        // Create a URL object from the provided image URL.
        val url = URL(imageUrl)
        // Open a connection to the URL and cast it to HttpURLConnection.
        val connection = url.openConnection() as HttpURLConnection
        // Set the request method to HEAD to retrieve only the headers without the actual content.
        connection.requestMethod = "HEAD"
        // Get the HTTP response code.
        val responseCode = connection.responseCode
        // Check if the response code is HTTP_OK (200).
        responseCode == HttpURLConnection.HTTP_OK
    } catch (e: Exception) {
        // Handle exceptions, e.g., MalformedURLException or IOException
        false
    }
}