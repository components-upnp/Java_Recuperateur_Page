package com.irit.main;

import com.irit.upnp.RecuperateurServer;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        new Thread(new RecuperateurServer()).run();
    }
}
