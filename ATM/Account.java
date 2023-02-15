package ATM;

public class Account {
    String cardID;
    String username;
    String passWord;
    double money;
    double quotoMoney;

    public Account() {
    }

    public Account(String cardID, String username, String passWord, double money, double quotoMoney) {
        this.cardID = cardID;
        this.username = username;
        this.passWord = passWord;
        this.money = money;
        this.quotoMoney = quotoMoney;
    }

    public String getCardID() {
        return cardID;
    }

    public void setCardID(String cardID) {
        this.cardID = cardID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public double getQuotoMoney() {
        return quotoMoney;
    }

    public void setQuotoMoney(double quotoMoney) {
        this.quotoMoney = quotoMoney;
    }

}
