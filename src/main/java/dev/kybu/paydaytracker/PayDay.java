package dev.kybu.paydaytracker;

public class PayDay {

    private final long newMoney;
    private final long addedMoney;
    private long moneyOnHand;

    public PayDay(final long newMoney, final long addedMoney) {
        this.newMoney = newMoney;
        this.addedMoney = addedMoney;
    }

    public void setMoneyOnHand(long moneyOnHand) {
        this.moneyOnHand = moneyOnHand;
    }

    public long getMoneyOnHand() {
        return moneyOnHand;
    }

    public long getAddedMoney() {
        return addedMoney;
    }

    public long getNewMoney() {
        return newMoney;
    }
}

