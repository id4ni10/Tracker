package br.com.tracker;

import java.util.Date;

/**
 * Created by danilo.nascimento on 02/02/2016.
 */
public class Location {
    private float latitude;
    private float longitude;
    private float altitude;
    private float velocidade;
    private Date utc;

    public double getLatitude() {
        return decimalToDMS(latitude);
    }

    public double getLongitude() {
        return decimalToDMS(longitude);
    }

    public float getAltitude() {
        return altitude;
    }

    public Date getUtc() {
        return utc;
    }

    public float getVelocidade() {
        return velocidade;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public void setAltitude(float altitude) {
        this.altitude = altitude;
    }

    public void setVelocidade(float velocidade) {
        this.velocidade = velocidade;
    }

    public void setUtc(Date utc) {
        this.utc = utc;
    }

    private double decimalToDMS(float value) {
        double degValue = value / 100;

        int degrees = (int) degValue;

        double decMinutesSeconds = ((degValue - degrees)) / .60;

        return degrees + decMinutesSeconds;
    }
}