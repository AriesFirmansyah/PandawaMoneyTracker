package id.ac.umn.pandawa;

class TransactionData {
    String category, money, notes, tanggal;

    public TransactionData(String category, String money, String notes, String tanggal) {
        this.category = category;
        this.money = money;
        this.notes = notes;
        this.tanggal = tanggal;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }
}
