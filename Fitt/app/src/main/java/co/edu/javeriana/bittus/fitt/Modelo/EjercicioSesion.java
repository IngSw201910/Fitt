    package co.edu.javeriana.bittus.fitt.Modelo;

    public abstract class EjercicioSesion {



        protected Ejercicio ejercicio;


        public EjercicioSesion(Ejercicio ejercicio) {
            this.ejercicio = ejercicio;
        }

        public Ejercicio getEjercicio() {
            return ejercicio;
        }

        public void setEjercicio(Ejercicio ejercicio) {
            this.ejercicio = ejercicio;
        }
    }