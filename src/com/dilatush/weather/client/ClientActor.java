package com.dilatush.weather.client;

import com.dilatush.mop.Actor;
import com.dilatush.mop.Message;
import com.dilatush.mop.PostOffice;

import java.util.logging.Logger;

/**
 * Implements a simple actor that subscribes to the once-per-minute weather report.
 *
 * @author Tom Dilatush  tom@dilatush.com
 */
public class ClientActor extends Actor {

    final static private Logger LOGGER = Logger.getLogger( new Object(){}.getClass().getEnclosingClass().getCanonicalName() );

    protected ClientActor( final PostOffice _po ) {
        super( _po, "client" );
    }


    public void start() {

        // register to receive weather reports...
        registerFQPublishMessageHandler( this::onWeatherReport,"weather.weather", "minute", "report" );
        mailbox.subscribe( "weather.weather", "minute.report" );
    }


    private void onWeatherReport( final Message _message ) {

        LOGGER.info( "Received weather report" );
        LOGGER.info( "" );
        LOGGER.info( "      Temperature: " + (32f + _message.getFloatDotted( "temperatureAvg" ) * 9f / 5f) + "°F");
        LOGGER.info( "Relative Humidity: " + _message.getFloatDotted( "relativeHumidity" ) + "%");
        LOGGER.info( "       Rain today: " + _message.getFloatDotted( "rainIntDy" ) + " inches" );
        LOGGER.info( "        Wind from: " + _message.getFloatDotted( "windVectorDirection" ) + "°"
                + " at " + (2.23694f * _message.getFloatDotted( "windVectorDistance" ) / 60f) + " mph" );
    }
}
