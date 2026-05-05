package com.example.amswerer

import android.graphics.Bitmap
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.example.amswerer.BuildConfig

class GeminiHelper {

    private val generativeModel = GenerativeModel(
        modelName = "gemini-2.5-flash",
        apiKey = BuildConfig.GEMINI_API_KEY
    )

    suspend fun analyzeImage(bitmap: Bitmap, prompt: String = "Extract the text/question from this image and provide an answer."): String? {
        return withContext(Dispatchers.IO) {
            try {
                val inputContent = content {
                    image(bitmap)
                    text(prompt)
                }
                val response = generativeModel.generateContent(inputContent)
                response.text
            } catch (e: Exception) {
                e.printStackTrace()
                "Error analyzing image: ${e.localizedMessage}"
            }
        }
    }
}
