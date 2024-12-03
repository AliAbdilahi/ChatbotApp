package com.example.dent_e;

import android.widget.ImageButton;

public class ChatbotResponse {
    String response;
    ImageButton sti;

    public ChatbotResponse(ImageButton sti) {
        this.sti = sti;
    }

    public ImageButton getSti() {
        return sti;
    }

    public void setSti(ImageButton sti) {
        this.sti = sti;
    }

    public String getResponse() {
        return response;
    }
}
