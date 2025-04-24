package com.cityxcape.cityxcape.utilities

import android.util.Log
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage

class QRCodeAnalyzer(
    private val onScanned: (String) -> Unit
) : ImageAnalysis.Analyzer {
    private val scanner = BarcodeScanning.getClient()
    @ExperimentalGetImage
    override fun analyze(image: ImageProxy) {
        val mediaImage = image.image

        if (mediaImage != null) {
            val inputImage = InputImage.fromMediaImage(mediaImage, image.imageInfo.rotationDegrees)

            scanner.process(inputImage)
                .addOnSuccessListener { barcodes ->

                    for (barcode in barcodes) {
                        barcode.rawValue?.let { value ->
                            onScanned(value)
                        }
                    }
                }
                .addOnFailureListener {
                    Log.e("QrCodeAnalyzer", "Qr scan failed", it)
                }
                .addOnCompleteListener {
                    image.close()
                }
        } else {
            image.close()
        }
    }
}