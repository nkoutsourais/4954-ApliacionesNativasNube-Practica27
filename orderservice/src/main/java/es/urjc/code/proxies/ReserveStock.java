package es.urjc.code.proxies;

public class ReserveStock {

    private int quanty;

    public int getQuanty() {
        return quanty;
    }

    public void setQuanty(int quanty) {
        this.quanty = quanty;
    }

    public ReserveStock(int quanty) {
        this.quanty = quanty;
    }
}