package f54148.moneybadger.Entities;

public enum Timeframe {

    MONTHLY("Monthly"),WEEKLY("Weekly"),BIWEEKLY("Biweekly"),DAILY("Daily"),UNREPEATABLE("Unrepeatable");

    private String iso4217Code = "";

    Timeframe(String code) {
        this.iso4217Code = code;
    }

    @Override
    public String toString() {
        return iso4217Code;
    }
}
