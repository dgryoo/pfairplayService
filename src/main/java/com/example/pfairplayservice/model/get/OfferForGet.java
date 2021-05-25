package com.example.pfairplayservice.model.get;

import com.example.pfairplayservice.jpa.model.MatchEntity;
import com.example.pfairplayservice.jpa.model.TeamEntity;
import com.example.pfairplayservice.model.enumfield.OfferStatus;

public class OfferForGet {

    private int oid;

    private MatchForGet offedMatch;

    private TeamForGet sander;

    private TeamForGet receiver;

    private OfferStatus offerStatus;

}
