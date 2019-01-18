package Com.knoventive.nutri.model;

import android.os.Parcel;
import android.os.Parcelable;


public class DataList implements Parcelable {
    String Desc , nutri_value, value;
    public DataList(String Desc, String data, String value){
        this.Desc  = Desc;
        this.nutri_value=data;
        this.value= value;
    }

    protected DataList(Parcel in) {
        Desc = in.readString();
        nutri_value = in.readString();
        value = in.readString();
    }

    public static final Creator<DataList> CREATOR = new Creator<DataList>() {
        @Override
        public DataList createFromParcel(Parcel in) {
            return new DataList(in);
        }

        @Override
        public DataList[] newArray(int size) {
            return new DataList[size];
        }
    };

    public  void setDesc(){

    }
    public  String  getDesc(){

        return Desc;
    }
    public  void setNutri_value(){

    }
    public  String  getNutri_value(){

        return nutri_value;
    }

    public  void setValue(){

    }
    public  String  getValue(){

        return value;
    }
    public  String  getValues(){

        return Desc+" : "+nutri_value+"  "+value;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(Desc);
        parcel.writeString(nutri_value);
        parcel.writeString(value);
    }
    public  static  final  Parcelable.Creator creator = new Creator() {
        @Override
        public Object createFromParcel(Parcel parcel) {
           return new DataList(parcel);
        }

        @Override
        public Object[] newArray(int i) {
            return new Object[0];
        }

    };
}
