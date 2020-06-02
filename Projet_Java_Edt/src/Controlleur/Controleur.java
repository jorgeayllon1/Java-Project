/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlleur;

import Vue.*;
import Modele.*;

import java.awt.event.ActionListener;
import java.util.*;

import static javafx.scene.input.KeyCode.T;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

/**
 * @author Wang David
 */
public abstract class Controleur implements ActionListener {

    protected DAO dao;


    public Controleur() {
    }

    public Controleur(DAO dao) {
        this.dao = dao;
    }


    public static void main(String args[]) throws UnsupportedLookAndFeelException {

    }

}
