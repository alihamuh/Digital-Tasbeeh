package com.alihamuh.ali.tasbeeh.customClasses;

import android.os.Parcel;
import android.os.Parcelable;

public class CompleteZikr implements Parcelable {
    String zikrType;
    String zikrText;
    int zikrCounter;
    int zikrSerialNo;

    protected CompleteZikr(Parcel in) {
        zikrType = in.readString();
        zikrText = in.readString();
        zikrCounter = in.readInt();
        zikrSerialNo = in.readInt();
    }

    public static final Creator<CompleteZikr> CREATOR = new Creator<CompleteZikr>() {
        @Override
        public CompleteZikr createFromParcel(Parcel in) {
            return new CompleteZikr(in);
        }

        @Override
        public CompleteZikr[] newArray(int size) {
            return new CompleteZikr[size];
        }
    };

    public CompleteZikr(){

    }

    public void setZikrCounter(int zikrCounter) {
        this.zikrCounter = zikrCounter;
    }

    public void setZikrText(String zikrText) {
        this.zikrText = zikrText;
    }

    public void setZikrType(String zikrType) {
        this.zikrType = zikrType;
    }

    public void setZikrSerialNo(int zikrSerialNo) {
        this.zikrSerialNo = zikrSerialNo;
    }

    public int getZikrSerialNo() {
        return zikrSerialNo;
    }

    public int getZikrCounter() {
        return zikrCounter;
    }

    public String getZikrText() {
        return zikrText;
    }

    public String getZikrType() {
        return zikrType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(zikrType);
        parcel.writeString(zikrText);
        parcel.writeInt(zikrCounter);
        parcel.writeInt(zikrSerialNo);
    }
}
