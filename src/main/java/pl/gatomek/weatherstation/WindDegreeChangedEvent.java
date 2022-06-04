package pl.gatomek.weatherstation;

import lombok.Getter;

import java.util.Date;

@Getter
public class WindDegreeChangedEvent {

    static public WindDegreeChangedEvent of( Double degree)
    {
        return new WindDegreeChangedEvent( degree);
    }

    protected WindDegreeChangedEvent( Double degree) {
        date = new Date();
        this.degree = degree;
    }

    private Date date;
    private Double degree;
}
