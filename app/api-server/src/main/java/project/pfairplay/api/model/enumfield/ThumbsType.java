package project.pfairplay.api.model.enumfield;

public enum ThumbsType {

    IncreaseThumbsUp(0),
    DecreaseThumbsUp(1),
    IncreaseThumbsDown(2),
    DecreaseThumbsDown(3);

    private Integer typeNumber;

    ThumbsType(Integer typeNumber) {
        this.typeNumber = typeNumber;
    }

    public int getTypeNumber() {
        return typeNumber;
    }

    public static ThumbsType from(Integer typeNumber) {
        for (ThumbsType t : ThumbsType.values()) {
            if (t.typeNumber == typeNumber) {
                return t;
            }
        }
        return null;
    }





}
