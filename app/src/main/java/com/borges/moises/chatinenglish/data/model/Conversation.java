package com.borges.moises.chatinenglish.data.model;

/**
 * Created by Moisés on 09/07/2016.
 */

public class Conversation {
    private long id;
    private User participant;

    public Conversation(User participant) {
        this.participant = participant;
    }
}
