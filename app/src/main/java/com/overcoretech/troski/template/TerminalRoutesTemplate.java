package com.overcoretech.troski.template;

/**
 * Created by pakabah on 27/05/16.
 */
public class TerminalRoutesTemplate {
   public String RouteName;
   public String RouteId;
   public String Distance;
   public String Price;
    public String RouteTo;


    public TerminalRoutesTemplate()
    {

    }

    public TerminalRoutesTemplate(String RouteName, String RouteId, String Distance, String Price)
    {
        this.RouteId = RouteId;
        this.RouteName = RouteName;
        this.Distance = Distance;
        this.Price = Price;
    }
}
