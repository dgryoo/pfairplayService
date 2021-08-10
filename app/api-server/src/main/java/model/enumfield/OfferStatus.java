package model.enumfield;

public enum OfferStatus {

    Waiting(0),
    Agree(1),
    Refuse(2);

    private int status;

    OfferStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public static OfferStatus from(int status) {
        for (OfferStatus os : OfferStatus.values()) {
            if (os.status == status) {
                return os;
            }
        }
        return null;
    }

}
