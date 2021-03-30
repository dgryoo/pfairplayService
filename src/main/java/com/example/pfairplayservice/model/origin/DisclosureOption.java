package com.example.pfairplayservice.model.origin;

public enum DisclosureOption {

    ALL(1),
    TEAM(2),
    NOBODY(3);

    private Integer disclosureOption;

    DisclosureOption(Integer disclosureOption) {
        this.disclosureOption = disclosureOption;
    }

    public Integer getDisclosureOption() {
        return disclosureOption;
    }

    public static DisclosureOption from(Integer disclosureOption) {
        if(disclosureOption == null) {
            return null;
        }
        for (DisclosureOption p : DisclosureOption.values()) {
            if (p.getDisclosureOption() == disclosureOption) {
                return p;
            }
        }
        return null;
    }


}
