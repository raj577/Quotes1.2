package com.probein.quotes;


public class Quote {
    public void setQuote(String quote) {
        this.quote = quote;
    }

    private String quote;

    public String getQuote() {
        return quote;
    }

    public Quote(String quote) {
        this.quote = quote;
    }

    @Override
    public String toString() {
        return  quote ;
    }
}
