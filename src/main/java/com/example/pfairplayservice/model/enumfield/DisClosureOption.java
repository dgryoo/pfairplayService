package com.example.pfairplayservice.model.enumfield;

public enum DisClosureOption {

    ALL(0),
    TEAM(1),
    NOBODY(2);


    private int disclosureOption;

    DisClosureOption(int position) {
        this.disclosureOption = position;
    }

    public int getDisclosureOption() {
        return disclosureOption;
    }

    public static DisClosureOption from(int disclosureOption) {

        for (DisClosureOption o : DisClosureOption.values()) {
            if (o.getDisclosureOption() == disclosureOption) {
                return o;
            }
        }
        return null;
    }

}
