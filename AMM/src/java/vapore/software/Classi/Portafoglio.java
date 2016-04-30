/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vapore.software.Classi;

/**
 *
 * @author rober
 */
public class Portafoglio {
    private int saldo;

    /**
     * @return the saldo
     */
    public int getSaldo() {
        return saldo;
    }

    /**
     * @param saldo the saldo to set
     */
    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }
    
    public void increaseSaldo(int moneta) {
        this.saldo += moneta;
    }
    
    public void decreaseSaldo(int moneta) {
        this.saldo -= moneta;
    }
    
}
