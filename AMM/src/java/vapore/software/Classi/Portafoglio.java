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
    private Double saldo;
    
    public Portafoglio(){
        this.saldo = 0.0;
    }

    /**
     * @return the saldo
     */
    public Double getSaldo() {
        return saldo;
    }

    /**
     * @param saldo the saldo to set
     */
    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }
    
    public void increaseSaldo(Double moneta) {
        this.saldo += moneta;
    }
    
    public void decreaseSaldo(Double moneta) {
        this.saldo -= moneta;
    }
    
}
