package co.edu.javeriana.bittus.fitt.Modelo;
import android.os.Parcel;
import android.os.Parcelable;


public class Entrenador implements Parcelable {
    private int id;
    private String nombre;
    private int cantidadClientes;
    private boolean titulo;
    private int aniosEx;
    private String acercaDe;


    public Entrenador(String nombre){
        this.nombre = nombre;
    }

    public String getNombre(){
        return nombre;
    }

    protected Entrenador(Parcel in) {
        id = in.readInt();
        nombre = in.readString();
        cantidadClientes = in.readInt();
        titulo = in.readByte() != 0x00;
        aniosEx = in.readInt();
        acercaDe = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nombre);
        dest.writeInt(cantidadClientes);
        dest.writeByte((byte) (titulo ? 0x01 : 0x00));
        dest.writeInt(aniosEx);
        dest.writeString(acercaDe);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Entrenador> CREATOR = new Parcelable.Creator<Entrenador>() {
        @Override
        public Entrenador createFromParcel(Parcel in) {
            return new Entrenador(in);
        }

        @Override
        public Entrenador[] newArray(int size) {
            return new Entrenador[size];
        }
    };
}
