/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author rostaklein
 */
public interface Subject {
    
    void registerObservers(Observer observer);
    
    void removeObservers(Observer observer);
    
    void notifyObservers();
    
}
