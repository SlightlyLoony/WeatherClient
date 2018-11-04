package com.dilatush.weather.client;

import com.dilatush.mop.PostOffice;
import com.dilatush.util.Config;

import java.io.File;
import java.util.logging.Logger;

import static com.dilatush.util.General.isNotNull;
import static java.lang.Thread.sleep;

/**
 * @author Tom Dilatush  tom@dilatush.com
 */
public class Main {

    static private Logger LOGGER;
    static private PostOffice po;


    static public void main( final String[] _args ) throws InterruptedException {

        // set the configuration file location (must do before any logging actions occur)...
        System.getProperties().setProperty( "java.util.logging.config.file", "logging.properties" );
        LOGGER = Logger.getLogger( new Object(){}.getClass().getEnclosingClass().getSimpleName() );
        LOGGER.info( "Weather client is starting..." );

        // the configuration file...
        String config = "weatherclientconfig.json";   // the default...
        if( isNotNull( (Object) _args ) && (_args.length > 0) ) config = _args[0];
        if( !new File( config ).exists() ) {
            System.out.println( "Weather client configuration file " + config + " does not exist!" );
            return;
        }

        // get our config...
        Config weatherConfig = Config.fromJSONFile( config );

        // start up our post office...
        po = new PostOffice( config );

        // start up our client...
        ClientActor client = new ClientActor( po );
        client.start();

        // now just hang around...
        while( !Thread.currentThread().isInterrupted() )
            sleep( 1000 );
    }
}
