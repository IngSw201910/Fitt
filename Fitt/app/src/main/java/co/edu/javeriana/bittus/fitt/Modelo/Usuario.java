package co.edu.javeriana.bittus.fitt.Modelo;

import java.util.List;

public class Usuario {

    private List<Rutina> rutinaList;


    public Usuario() {

    }

    public List<Rutina> getRutinaList() {
        return rutinaList;
    }

    public void setRutinaList(List<Rutina> rutinaList) {
        this.rutinaList = rutinaList;
    }
}
