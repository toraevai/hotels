package com.example.hotels.ui.parts

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class PhoneNumberTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed =
            if (text.text.length >= 11) text.text.substring(0..10) else text.text
        val out = "+7(***)***-**-**"
        for (i in trimmed.indices) {
            out.replaceFirst("*", trimmed[i].toString())
        }

        val phoneNumberOffsetTranslator = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                if (offset <= 2) return 0
                if (offset <= 5) return offset
                if (offset <= 9) return offset + 1
                if (offset <= 12) return offset + 2
                return 16
            }

            override fun transformedToOriginal(offset: Int): Int {
                if (offset <= 6) return offset
                if (offset <= 10) return offset
                if (offset <= 13) return offset - 1
                if (offset <= 16) return offset - 2
                return 10
            }
        }

        return TransformedText(AnnotatedString(out), phoneNumberOffsetTranslator)
    }
}