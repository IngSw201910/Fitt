    package co.edu.javeriana.bittus.fitt.Modelo;

    import java.io.Serializable;

    public class EjercicioEntrenamiento implements Serializable {


        protected Ejercicio ejercicio;



        public EjercicioEntrenamiento(Ejercicio ejercicio) {
            this.ejercicio = ejercicio;
        }

        public EjercicioEntrenamiento() {
            super();
        }

        public Ejercicio getEjercicio() {
            return ejercicio;
        }

        public void setEjercicio(Ejercicio ejercicio) {
            this.ejercicio = ejercicio;
        }
    }
