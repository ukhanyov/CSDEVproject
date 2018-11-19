package com.example.admin_linux.csdevproject.data.models.feed_event.header;

import android.os.Parcel;
import android.os.Parcelable;

public class InvolvedPeople implements Parcelable {

    private int personId;
    private String personFirstName;
    private String personLastName;
    private String personFullName;
    private boolean isFavorite;
    private String organizationName;
    private String iconPath;

    public InvolvedPeople(int personId, String personFirstName, String personLastName, String personFullName, boolean isFavorite, String organizationName, String iconPath) {
        this.personId = personId;
        this.personFirstName = personFirstName;
        this.personLastName = personLastName;
        this.personFullName = personFullName;
        this.isFavorite = isFavorite;
        this.organizationName = organizationName;
        this.iconPath = iconPath;
    }

    public int getPersonId() {
        return personId;
    }

    public String getPersonFirstName() {
        return personFirstName;
    }

    public String getPersonLastName() {
        return personLastName;
    }

    public String getPersonFullName() {
        return personFullName;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public String getIconPath() {
        return iconPath;
    }

    public InvolvedPeople(Parcel in){
        boolean[] dataBoolean = new boolean[1];
        in.readBooleanArray(dataBoolean);

        this.personId = in.readInt();
        this.personFirstName = in.readString();
        this.personLastName = in.readString();
        this.personFullName = in.readString();
        this.isFavorite = dataBoolean[0];
        this.organizationName = in.readString();
        this.iconPath = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.personId);
        dest.writeString(this.personFirstName);
        dest.writeString(this.personLastName);
        dest.writeString(this.personFullName);
        dest.writeBooleanArray(new boolean[] {
                this.isFavorite
        });
        dest.writeString(this.organizationName);
        dest.writeString(this.iconPath);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public InvolvedPeople createFromParcel(Parcel in) {
            return new InvolvedPeople(in);
        }

        public InvolvedPeople[] newArray(int size) {
            return new InvolvedPeople[size];
        }
    };
}
