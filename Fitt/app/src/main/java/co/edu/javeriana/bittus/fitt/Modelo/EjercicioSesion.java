    package co.edu.javeriana.bittus.fitt.Modelo;

    import java.io.Serializable;

    public class EjercicioSesion implements Serializable {


        protected Ejercicio ejercicio;



        public EjercicioSesion(Ejercicio ejercicio) {
            this.ejercicio = ejercicio;
        }

        public EjercicioSesion() {
            super();
        }

        public Ejercicio getEjercicio() {
            return ejercicio;
        }

        public void setEjercicio(Ejercicio ejercicio) {
            this.ejercicio = ejercicio;
        }
    }
