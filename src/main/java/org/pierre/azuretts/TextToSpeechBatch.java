package org.pierre.azuretts;

import com.microsoft.cognitiveservices.speech.*;
import com.microsoft.cognitiveservices.speech.audio.AudioConfig;

import java.util.Arrays;
import java.util.List;

public class TextToSpeechBatch {

    // Azure Speech Service credentials
    private static final String SUBSCRIPTION_KEY = "";
    private static final String REGION = "eastus"; // e.g., "westeurope"

    public static void main(String[] args) {
        // List of German text chunks to process
        List<String> textChunks = Arrays.asList(
                "wurde an eine französisch-belgische Holdinggesellschaft verkauft und entzog sich den Bedingungen des Young-Plans"
        );

        // Process each chunk and save as MP3
        for (int i = 0; i < textChunks.size(); i++) {
            String text = textChunks.get(i);
            String outputFile = "output_" + (i + 1) + ".mp3";
            synthesizeToFile(text, outputFile);
        }
    }

    private static void synthesizeToFile(String text, String outputFile) {
        try {
            // Configure speech synthesis
            SpeechConfig speechConfig = SpeechConfig.fromSubscription(SUBSCRIPTION_KEY, REGION);
            speechConfig.setSpeechSynthesisVoiceName("de-DE-KatjaNeural"); // German female neural voice

            // Configure audio output
            AudioConfig audioConfig = AudioConfig.fromWavFileOutput(outputFile);

            // Create a synthesizer and convert text to speech
            SpeechSynthesizer synthesizer = new SpeechSynthesizer(speechConfig, audioConfig);
            SpeechSynthesisResult result = synthesizer.SpeakText(text);

            if (result.getReason() == ResultReason.SynthesizingAudioCompleted) {
                System.out.println("Successfully created: " + outputFile);
            } else {
                System.err.println("Error synthesizing text: " + result.getReason());
            }

            result.close();
            synthesizer.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
